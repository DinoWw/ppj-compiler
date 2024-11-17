package SA;

public class LexUnit {
   public int lineNumber;
   public String lexUnit;
   public String contents;

   public LexUnit(int lineNumber, String lexUnit, String contents){
      this.lineNumber = lineNumber;
      this.lexUnit = lexUnit;
      this.contents = contents;
   }
}
