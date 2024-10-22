package structures;

import java.lang.Thread.State;
import structures.Automat;

public class GeneratorRule
{
   public String stateFrom;
   public String regex;
   public Automat automat;
   public String lexUnit = "-";
   public boolean newLine = false;
   public String stateTo;
   public int goBack;


   public String toString(){
      return String.format("%s -> %s\n %s\n prihvati: %s\n novi red: %b\n vrati se: %d", stateFrom, stateTo, regex, lexUnit, newLine, goBack);
   }

};
