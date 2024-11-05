package analizator.structures;

import analizator.generated.LexClass;

public class LexUnit{
   
   private LexClass className;
   private String content;
   private int lineNumber;
   
   public LexUnit(LexClass className, int lineNumber, String content){
      this.className = className;
      this.content = content;
      this.lineNumber = lineNumber;
   }

   public LexClass getClasssName(){
      return className;
   }

   public String getContent(){
      return content;
   }

   public int getLineNumber(){
      return lineNumber;
   }

   public String toString(){
      return String.format("%s %d %s", className.toString(), lineNumber, content);
   }

}
