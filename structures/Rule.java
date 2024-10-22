package structures;

public class Rule
{
   public String stateFrom;   // TODO: change into State
   public String regex;   // TODO: create constructor, and in it, transform regex into automaton
   public Automat automat;
   public String lexUnit = "-";
   public boolean newLine = false;
   public String stateTo = "";
   public int goBack;
   public int priority;

   public void reset(){
      //TODO: reset automaton
   }

   public String toString(){
      return String.format("%s -> %s\n %s\n prihvati: %s\n novi red: %b\n vrati se: %d", stateFrom, stateTo, regex, lexUnit, newLine, goBack);
   }

};
