package analizator;
public class LexUnit{
   
   public enum lexClass {
      OPERAND,
      OP_MINUS,
      UMINUS,
      LIJEVA_ZAGRADA,
      DESNA_ZAGRADA
   }

   // TODO: extractati lexClass generatoru
   // TODO: dodati line number mozda

   private lexClass className;
   private String content;
   
   public LexUnit(lexClass className, String content){
      this.className = className;
      this.content = content;
   }

   public lexClass getClasssName(){
      return className;
   }

   public String getContent(){
      return content;
   }

   public String toString(){
      return String.format("%s: %s", className.toString(), content);
   }

}
