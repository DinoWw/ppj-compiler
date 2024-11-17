
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
      this.lexUnit = lexUnit;
      this.isLeaf = true;
   }

   @Override
   public String toString(){
      StringBuilder s = new StringBuilder();
      s.append("Symbol: " + this.symbol + "{");
      for (SyntaxTreeNode node : this.children){
         s.append(node.toString());
      }
      return (s.append("}").toString());
   }
}
