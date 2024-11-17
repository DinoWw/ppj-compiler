
public class SyntaxTreeNode {
   public boolean isLeaf;

   public SyntaxTreeNode[] children;

   public LexUnit lexUnit;
   public String symbol;

   public SyntaxTreeNode(String symbol, SyntaxTreeNode[] children){
      this.symbol = symbol;
      this.children = children;
      if(this.children.length == 0){
         System.out.println("MORA IMAT DJECU");
      }
      this.isLeaf = false;
   }
   public SyntaxTreeNode(LexUnit lexUnit){
      if(lexUnit == null){
         System.out.println("NE NULL ZA LEX UNIT pls");
      }
      this.lexUnit = lexUnit;
      this.isLeaf = true;
   }

   @Override
   public String toString(){
      StringBuilder s = new StringBuilder();
      if(this.isLeaf){
         s.append("Symbol: " + this.lexUnit.lexUnit);
         return s.toString();
      }
      // else
      s.append("Symbol: " + this.symbol);
      s.append("\n{\n");         
      for (SyntaxTreeNode node : this.children){
         s.append(node.toString()).append("\n");
      }
      return (s.append("}").toString());
   }
}
