package analizator;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Map;

import analizator.Rule;

public class Lekser {

  public static enum State {
    // TODO: get states from generator
    S_pocetno,
    S_komentar,
    S_unarni
  }

  private char activeChar;
  private ArrayList<State> activeStates, nextStates;
  private Reader reader;
  private String readCache;       // string from *start to *end
  private int lastValidLen;       // distance from *start to *last

  private Map<State, Rule[]> rules;

  private ArrayList<LexUnit> lexUntis;

  public Lekser(Reader _reader){
    reader = _reader;
    activeStates = new ArrayList<State>();
    activeStates.add(State.S_pocetno);
    nextStates = new ArrayList<State>();
    readCache = "";
    lastValidLen = 0;

    lexUntis = new ArrayList<LexUnit>();
  }

  // TODO: change return type or return through getters of private properties
  public void analyse() throws IOException{
    for(;;){
      nextChar();
      
      boolean anyAccepted = false;
      for(State state : activeStates){
        for(Rule rule : rules.get(state)){
          anyAccepted = anyAccepted || rule.automat.doTransition(activeChar);
          if(rule.automat.isAccepted()){

          }
        }
      }

      /*(
       * for each regex (rule) tied to one of the active states:
       *  check if regex accepts
       *  if all reject:
       *    if len(lastAccepted) == 0:
       *      error recovery 
       *    if len(lastAccepted) > 1:
       *      only consider the regex tied to the uppermost rule
       *    deal with the transition tied to the last regex that accepted => accept()
       *    reset relevant data structures
       *  note what regexes accept in array lastAccepted
      ) */


    }
  }

  private int nextChar() throws IOException{
    activeChar = (char) reader.read();
    if(activeChar == -1){
      return 1;
    }
    return 0;
  }

  private void accept(LexUnit.lexClass klasa){
    // TODO: accept only start to last, not entire readCache
    // https://docs.oracle.com/javase/8/docs/api/java/io/BufferedReader.html#mark-int-
    // https://docs.oracle.com/javase/8/docs/api/java/io/BufferedReader.html#reset--
    // leksJeds.add(new LeksJed(klasa, readCache));
  }

  void reject(){
    /*
     * TODO:
     * return readCache to reader
     * reset relevant data structures
     */
  }

}
