package structures;

public class GeneratorRule
{
   public String stateFrom;
   public String regex;
   public String lexClass = "-";
   public boolean newLine = false;
   public String stateTo;
   public int goBack = -1;

   public String toNewRule(){

      String lexClassString = "null";
      if(!this.lexClass.equals("-")){
         lexClassString = String.format("LexClass.%s", this.lexClass);
      }

      String stateToString = "null";
      if(!(this.stateTo == null || this.stateTo.equals(""))){
         stateToString = String.format("State.%s", this.stateTo);
      }

      return String.format("new Rule( State.%s, new Automat(\"%s\"), %s, %b, %s, %d )",
         this.stateFrom, escape(this.regex), lexClassString,
         this.newLine, stateToString, this.goBack);
   }

   public String toString(){
      return String.format("%s -> %s\n %s\n prihvati: %s\n novi red: %b\n vrati se: %d", stateFrom, stateTo, regex, lexClass, newLine, goBack);
   }

   // src: https://stackoverflow.com/questions/2406121/how-do-i-escape-a-string-in-java
   static String escape(String s){
      return s.replace("\\", "\\\\")
              .replace("\t", "\\t")
              .replace("\b", "\\b")
              .replace("\n", "\\n")
              .replace("\r", "\\r")
              .replace("\f", "\\f")
              .replace("\'", "\\'")      // <== not necessary
              .replace("\"", "\\\"");
    }
};

