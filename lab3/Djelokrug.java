package lab3;

import java.util.List;

import lab3.tip.Tip;
import lab3.znakovi.Identifikaror;

/// TODO: mozda tu ne trebaju ic identifikatori neg neka slicna nova klasa (Deklaracija?)
/// Deklaracija
/// Varijabla extends Deklaracija
/// 
public class Djelokrug {
   public List<Identifikaror> identifikatori;
   public Djelokrug ugnjezdujuciDjelokrug;

   public boolean sadrziDeklaraciju(String ime){
      throw new UnsupportedOperationException();
   }

   public void zabiljeziDeklaraciju(String ime, Tip tip){
      throw new UnsupportedOperationException();
   }
   
   public Tip tipDeklaracije(String ime) {
      throw new UnsupportedOperationException();
   }
}
