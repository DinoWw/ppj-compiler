
public class StackElem {
   public int stateIndex;
   public String stateString;
   public SyntaxTreeNode node;
   public StackElem(int stateIndex, String stateString, SyntaxTreeNode node) {
      this.stateIndex = stateIndex;
      this.stateString = stateString;
      this.node = node; 
   }
   
   @Override
   public String toString(){
      return stateIndex + " " + stateString;
   }
}
