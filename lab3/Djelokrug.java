package lab3;

import java.util.Map;

import lab3.tip.Tip;
import lab3.znakovi.Deklaracija;

/// TODO: mozda tu ne trebaju ic identifikatori neg neka slicna nova klasa (Deklaracija?)
/// Deklaracija
/// Varijabla extends Deklaracija
/// 
public class Djelokrug {
   private Map<String, Identifikator> identifikatori;
   public Djelokrug ugnjezdujuciDjelokrug;

   public boolean sadrziIdentifikator(String ime){
      return identifikator(ime) != null;
   }
   public boolean sadrziLokalniIdentifikator(String ime){
      return identifikatori.get(ime) != null;
   }

   public Identifikator identifikator(String ime) {
      Identifikator id = identifikatori.get(ime);
      if(id == null) {
         if(ugnjezdujuciDjelokrug == null) {
            return null;
         }
         else {
            return ugnjezdujuciDjelokrug.identifikator(ime);
         }
      }
      return id;
   }

   public void zabiljeziIdentifikator(String ime, Tip tip){
      throw new UnsupportedOperationException();
   }
   
   public Tip tipIdentifikatora(String ime) {
      throw new UnsupportedOperationException();
   }
}
