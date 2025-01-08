package lab3.znakovi;

import lab3.Node;

public abstract class Znak extends Node {
    
   // TODO check :)
   // gernerira isklucivo
   public Konstanta generira(KonstantaEnum k) {
      if(children.size() > 1)
         return null;
      if(children.size() == 0 ){
         if(this instanceof GenerickaKonstanta){
            if( ((GenerickaKonstanta) this ).konstantaTip == k ) {
               return (GenerickaKonstanta) this;
            }
            else {
               return null;
            }
         }
         // vjv unreachable
         return null;
      }
      
      return ( (Znak) this.children.get(0) ).generira(k);
   }
}