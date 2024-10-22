package analizator.structures;

import java.lang.Thread.State;
import structures.Automat;

public class Rule
{
   public State stateFrom;          // not null
   public Automat automat;          // not null
   public String lexUnit;           // default to null
   public boolean newLine = false;  // default to false
   public State stateTo;            // default to stateFrom
   public int goBack;               // has to be default 0

   public void reset(){
      //TODO: reset automaton
   }

};
