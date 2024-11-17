package SA;

public class LexUnit {
   private int lineNumber;
   private String lexUnit;
   private String contents;

   public LexUnit(int lineNumber, String lexUnit, String contents){
      this.lineNumber = lineNumber;
      this.lexUnit = lexUnit;
      this.contents = contents;
   }
}
