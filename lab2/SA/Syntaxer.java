package SA;

import java.util.Map;
import java.util.Stack;

public class Syntaxer {
   private Stack<StackElem> stack;

   private String[] syncSymbols;

   private String currentSymbol;

   public Syntaxer(Map<String, Integer>[] table, String[] syncSymbols){
      this.syncSymbols = syncSymbols;
   }


   private void prihvati(){
      // TODO: prihvati
      System.out.println("TODO: PRIHVATI");
   }

   private void reduciraj(String left, String[] right){
      // TODO: reducijra
   }

   private void odbij(){
      // TODO: odbij, oporavak od pogreske
      System.out.println("TODO: ODBIJ");
   }

   private void pomakni(int stateIndex){
      stack.add(new StackElem(stateIndex, currentSymbol));
   }

   private void stavi(int stateIndex){
      stack.add(new StackElem(stateIndex, currentSymbol));
   }

}
