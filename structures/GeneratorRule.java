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
   public int goBack = -1;

   public String toNewRule(){
      return String.format("new Rule( %s, new Automat(%s), %s, %b, %s, %d))",
      this.stateFrom.toString(), this.regex, this.lexUnit.toString(),
      this.newLine, this.stateTo.toString(), this.goBack);
   }

   public String toString(){
      return String.format("%s -> %s\n %s\n prihvati: %s\n novi red: %b\n vrati se: %d", stateFrom, stateTo, regex, lexUnit, newLine, goBack);
   }

};
