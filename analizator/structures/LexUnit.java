package analizator.structures;

import analizator.generated.LexClass;

public class LexUnit{
   

   // TODO: extractati lexClass generatoru
   // TODO: dodati line number mozda

   private LexClass className;
   private String content;
   
   public LexUnit(LexClass className, String content){
      this.className = className;
      this.content = content;
   }

   public LexClass getClasssName(){
      return className;
   }

   public String getContent(){
      return content;
   }

   public String toString(){
      return String.format("%s: %s", className.toString(), content);
   }

}
