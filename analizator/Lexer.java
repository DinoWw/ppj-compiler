package analizator;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Map;

import analizator.generated.State;
import analizator.structures.LexUnit;
import analizator.structures.Rule;
import analizator.generated.Rules;

public class Lexer {

  private static final int LEX_UNIT_MAX_LENGTH = 255;   // Lexical units longer than 256 chars will cause undefined behavior

  private boolean lastChar;

  private char activeChar;
  private State activeState;
  private Reader reader;
  private int readLen;            // number of chars read since last accept()
  private int lastValidLen;       // distance from *start to *last

  private int lineNumber;
  
  private Rule[] lastAccepted; 

  private Map<State, Rule[]> rules = Rules.getRules();

  private ArrayList<LexUnit> lexUnits;

  public Lexer(Reader _reader){
    reader = _reader;

    readLen = 0;
    lastValidLen = 0;
    lineNumber = 1;

    lastChar = false;

    activeState = State.S_pocetno;

    lexUnits = new ArrayList<LexUnit>();
    try{
    reader.mark(LEX_UNIT_MAX_LENGTH);

    }
    catch(IOException e){
      System.err.println("couldnt mark");
    }
   
  }

  // TODO: change return type or return through getters of private properties
  public ArrayList<LexUnit> analyse() throws IOException{
    for(nextChar(); !lastChar; nextChar()){           
      boolean anyActive = false;
      ArrayList<Rule> accepts = new ArrayList<Rule>();
      
      for(Rule rule : rules.get(activeState)){
        anyActive = (rule.automat.doTransition(activeChar) || anyActive);
        if(rule.automat.isAccepted()){
          accepts.add(rule);
        }
      }
      if(!accepts.isEmpty()){
        lastAccepted = accepts.toArray(new Rule[0]);
        lastValidLen = readLen;
      }
      if(anyActive){
        continue;
      }
      if(lastAccepted.length == 0){
        handleError();
      }
      else{
        // TODO: test, potential source of errors
        final Rule priorityRule = lastAccepted[0]; // because of ordering, first rule has highest priority

        // accept resets automatons
        accept(priorityRule);
      }
    }

    return this.lexUnits;

    /*(
      *  for each regex (rule) tied to one of the active states:
      *   check if regex accepts
      *   note what regexes accept in array lastAccepted
      *  if all reject:
      *    if len(lastAccepted) == 0:
      *      error recovery 
      *    if len(lastAccepted) > 1:
      *      only consider the regex tied to the uppermost rule
      *    deal with the transition tied to the last regex that accepted => accept()
      *    reset relevant data structures
    ) */

  }

  private void nextChar() throws IOException{
    
    if(lastChar){
      System.err.println("Lekser error: read past end of input");
      return;
    }
    // windows represents a newline character with \r\n, \r is redundant
    if('\r' == (activeChar = readChar())){
      nextChar();
    }
    
    readLen ++;

  }

  private char readChar() throws IOException{
    int nextCharInt = reader.read();
    if(nextCharInt == -1){
      lastChar = true;
    }
    return (char) nextCharInt;
  }

  private void accept(Rule rule){
    try{
    reader.reset();
    // reset automatons before change of state so only relevant automatons are touched
    resetAutomatons();

    int acceptLen;
    if(rule.goBack != -1){
      acceptLen = rule.goBack;
    }
    else{
      acceptLen = lastValidLen;
    }

    // reading acceptLen characters is absolutely required regardles
    //  of weatther or not thely will be written to a lexUnit as
    //  we can not skip reader.reset() due to potential goBack
    char[] lexUnitCharArr = new char[acceptLen];
    reader.read(lexUnitCharArr, 0, acceptLen);
    String lexUnitString = new String(lexUnitCharArr);

    if(rule.lexClass != null){
      lexUnits.add(new LexUnit(rule.lexClass, this.lineNumber, lexUnitString));
    }

    if(rule.newLine){
      lineNumber++;
    }

    if(rule.stateTo != null){
      this.activeState = rule.stateTo;
    }

    //nextChar();
    resetPointers();
    reader.mark(LEX_UNIT_MAX_LENGTH);
    }
    catch(IOException e){
      System.err.println("reader error");
    }
  }

  // TODO: rename method to better reflect function
  private void resetPointers(){
    lastAccepted = new Rule[0];
    readLen = 0;
    lastValidLen = 0;
  } 


  private void resetAutomatons(){
    for(Rule rule : rules.get(this.activeState)){
      rule.automat.reset();
    }
  }

  private void handleError() throws IOException{
    System.err.println(String.format("Greska na liniji %d", lineNumber));
    
    reader.reset();
    nextChar();
    resetAutomatons();
    resetPointers();
    reader.mark(LEX_UNIT_MAX_LENGTH);
  }

}
