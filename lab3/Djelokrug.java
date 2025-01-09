package lab3;

import java.util.HashMap;
import java.util.Map;

import lab3.tip.FunkcijaTip;
import lab3.tip.Tip;
import lab3.tip.TipEnum;

/// TODO: mozda tu ne trebaju ic identifikatori neg neka slicna nova klasa (Deklaracija?)
/// Deklaracija
/// Varijabla extends Deklaracija
/// 
public class Djelokrug {
   private Map<String, Identifikator> identifikatori;
   public Djelokrug ugnjezdujuciDjelokrug;

   public TipDjelokruga tipDjelokruga;

   public Tip povratniTip; // samo za tipDjelokruga == FUNKCIJA

   public Djelokrug(Djelokrug ugnjezdujuciDjelokrug) {
      identifikatori = new HashMap<String, Identifikator>();
      this.ugnjezdujuciDjelokrug = ugnjezdujuciDjelokrug;
      this.povratniTip = new Tip(TipEnum.VOID);
      this.tipDjelokruga = TipDjelokruga.OBICNI_BLOK;
   }

   public Djelokrug() {
      identifikatori = new HashMap<String, Identifikator>();
      this.ugnjezdujuciDjelokrug = null;
      this.povratniTip = new Tip(TipEnum.VOID);
      this.tipDjelokruga = TipDjelokruga.OBICNI_BLOK;
   }

   public void setFunkcija(FunkcijaTip fTip){
      tipDjelokruga = TipDjelokruga.FUNKCIJA;
      povratniTip = fTip.rval;
   }

   public boolean sadrziLokalniIdentifikator(String ime){
      return lokalIdentifikator(ime) != null;
   }

   public boolean sadrziIdentifikator(String ime){
      return identifikator(ime) != null;
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

   public boolean jeUnutarFunkcijePovratneVrijednosti(Tip rval) {
      if(tipDjelokruga == TipDjelokruga.FUNKCIJA && povratniTip.equals(rval)) {
         return true;
      }
      else if(ugnjezdujuciDjelokrug != null){
         return ugnjezdujuciDjelokrug.jeUnutarFunkcijePovratneVrijednosti(rval);
      }
      else {
         return false;
      }
   }

   public Tip povratniTipUgnjezdujuceFunkcije() {
      if(tipDjelokruga == TipDjelokruga.FUNKCIJA) {
         return povratniTip;
      }
      else if(ugnjezdujuciDjelokrug != null){
         return ugnjezdujuciDjelokrug.povratniTipUgnjezdujuceFunkcije();
      }
      else {
         return null;
      }
   }

   public boolean jeUnutarPetlje(){
      if(tipDjelokruga == TipDjelokruga.PETLJA){
         return true;
      }
      else if(ugnjezdujuciDjelokrug != null){
         return ugnjezdujuciDjelokrug.jeUnutarPetlje();
      }
      else {
         return false;
      }
   }

   public Identifikator lokalIdentifikator(String ime) {
      return identifikatori.get(ime);
   }

   public void zabiljeziIdentifikator(String ime, Tip tip){
      throw new UnsupportedOperationException();
   }
   
   public Tip tipIdentifikatora(String ime) {
      throw new UnsupportedOperationException();
   }
}
