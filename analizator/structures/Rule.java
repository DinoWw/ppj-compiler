package analizator.structures;

import analizator.generated.State;
import analizator.generated.LexClass;
import structures.Automat;

public class Rule
{
   public State stateFrom;          // not null
   public Automat automat;          // not null
   public LexClass lexClass;           // default to null
   public boolean newLine = false;  // default to false
   public State stateTo;            // default to stateFrom
   public int goBack;               // has to be default -1

   public void reset(){
      //TODO: reset automaton
   }

};
