package analizator.structures;

import analizator.generated.State;
import analizator.generated.LexClass;

public class Rule
{
   public State stateFrom;          // not null
   public Automat automat;          // not null
   public LexClass lexClass;        // default to null
   public boolean newLine = false;  // default to false
   public State stateTo;            // default to stateFrom
   public int goBack;               // has to be default -1

   public Rule(State stateFrom, Automat automat, LexClass lexClass, boolean newLine,
         State stateTo, int goBack) {
      this.stateFrom = stateFrom;
      this.automat = automat;
      this.lexClass = lexClass;
      this.newLine = newLine;
      if(stateTo == null || stateTo.equals("")){
         this.stateTo = null;
      }
      else{
         this.stateTo = stateTo;
      }
      this.goBack = goBack;
   }
   
   public String toString(){
      return String.format("%s -> %s\n\tprihvati: %s\n\tnovi red: %b\n\tvrati se: %d", 
         stateFrom.name(), stateTo == null ? "null" : stateTo.name(), lexClass == null ? "null" : lexClass.name(), newLine, goBack);
   }


   public void reset(){
      this.automat.reset();
   }

};
