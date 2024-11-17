
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

public class Syntaxer {
   private Stack<StackElem> stack;

   private String[] syncSymbols;
   private ArrayList<LexUnit> inputArray;

   private LexUnit currentLexUnit;
   private int currentIndex = 0;
   public SyntaxTreeNode syntaxTree;


   private Map<Integer, Map<String, Action>> table;

   public Syntaxer(Map<Integer, Map<String, Action>> table, String[] syncSymbols){
      this.syncSymbols = syncSymbols;
      this.table = table;

      stack = new Stack<StackElem>();

   }
   public void setInputArray(ArrayList<LexUnit> inputArray){
      this.inputArray = inputArray;
   }


   public void analyse(){
      stack.push(new StackElem(0, "", syntaxTree));    //state 0 and end of stack symbol; ovdje fakat nije bitno ja msilim da je zandnji argumenti syntaxtree

      nextSymbol();

      for(;;){
         Action nextAction;
         try{
            System.out.println("Calculating next action with:");
            System.out.println(stack.peek().stateIndex);
            System.out.println(currentLexUnit.lexUnit);
            nextAction = table.get(stack.peek().stateIndex).get(currentLexUnit.lexUnit);
            System.out.println(nextAction);
         }
         catch(NullPointerException e){
            nextAction = null;
            odbij();
         }
         if(nextAction == null){
            odbij();
         }
         else{
            switch (nextAction.whatToDo) {
               case POMAKNI:
                  pomakni(nextAction.nextState);
                  break;
               case PRIHVATI:
                  prihvati();
                  break;
               case STAVI:
                  stavi(nextAction.nextState);
                  break;
               case REDUCIRAJ:
                  reduciraj(nextAction.left, nextAction.right.toArray(new String[0]));  //TODO: check
                  break;

            
               default:
                  System.out.println("OVO SE BAS I NE BI TREBALO IKAD ISPISAT");
            }
         }

      }


   }

   private void nextSymbol(){
      currentLexUnit = inputArray.get(currentIndex);
      currentIndex ++;
   }

   private void prihvati(){
      this.syntaxTree = stack.pop().node;
   }

   private void reduciraj(String left, String[] right){
      
      ArrayList<SyntaxTreeNode> children = new ArrayList<SyntaxTreeNode>();
      System.out.println("REDUKCIJA:");
      System.out.println("stack prije redukcije:");
      System.out.println(stack);
      for(int i = right.length -1; i >= 0; i--){
         String acceptSymbol = right[i];
         StackElem stackElem = stack.pop();
         System.out.println(acceptSymbol);
         System.out.println(stackElem.stateString);
         if(!stackElem.stateString.equals(acceptSymbol)){
            System.out.println("NEKA FAKING GRESKA pri redukciji");
            return;
         }
         // else
         children.add(stackElem.node);
         
      }
      System.out.println("stack poslje redukcije:");
      System.out.println(stack);

      SyntaxTreeNode newNode = new SyntaxTreeNode(left, children.toArray(new SyntaxTreeNode[0]));
      System.out.println("REDUKCIJA USPJESNA DO POLA:");
      System.out.println(stack.peek().stateIndex);
      System.out.println(left);
      Action nextAction = table.get(stack.peek().stateIndex).get(left);
      if(nextAction == null){
         System.out.println("nextAction is NULL");
         odbij();
      }
      else if(nextAction.whatToDo != ActionEnum.STAVI){
         System.out.println("WTF(frick), mislio sam da je uvijek stavi. jel moguce da je greska u ulaznom nizu ili ne?");
      }
      System.out.println(newNode);
      Integer nextStateIndex = nextAction.nextState;
      stack.push(new StackElem(nextStateIndex, left, newNode));

   }

   private void odbij(){
      // TODO: odbij, oporavak od pogreske
      System.out.println("TODO: ODBIJ");
   }

   private void pomakni(int stateIndex){
      stack.add(new StackElem(stateIndex, currentLexUnit.lexUnit, new SyntaxTreeNode(currentLexUnit)));
      nextSymbol();
   }

   private void stavi(int stateIndex){
      System.out.println("POZVAN STAVI! zasto??");
      
      //stack.add(new StackElem(stateIndex, currentSymbol));
   }

}
