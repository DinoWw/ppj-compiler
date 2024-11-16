import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.plaf.nimbus.State;

public class Transformer{

   private ArrayList<Stavka> stavkas;
   private String[] ulZnakovi;

   public Transformer(ArrayList<Stavka> stavkas, String[] ulZnakovi){
      this.stavkas = stavkas;
      this.ulZnakovi = ulZnakovi;
   }

   static boolean zavrsni(String s){
      return s.startsWith("<");
   }

   public Map<Stavka, Map<String, Stavka[]>> generateENKA(){
      Map<Stavka, Map<String, Stavka[]>> enka = new HashMap<Stavka, Map<String, Stavka[]>>();


      
   
      for(Stavka s1 : stavkas){
         // initialize map
         Map<String, Stavka[]> tmpMap = new HashMap<String, Stavka[]>();

         String nextSymbol = s1.right.get(s1.dotIndex);

         if(nextSymbol == null){
            continue;
         }

         // dodajemo epsilon prijelaze
         if(zavrsni(nextSymbol))
         {
            ArrayList<Stavka> tmpList = new ArrayList<Stavka>();
            for(Stavka s2 : stavkas)
            {
               if(nextSymbol.equals(s2.left) && s2.dotIndex == 0){
                  tmpList.add(s2);
               }
            }
            tmpMap.put("", tmpList.toArray(new Stavka[0]));
         }
         else 
         {
            ArrayList<Stavka> tmpList = new ArrayList<Stavka>();
            for(Stavka s2 : stavkas)
            {
               if(s2.left.equals(s1.left) && nextSymbol.equals(s2.right.get(s2.dotIndex-1))){
                  tmpList.add(s2);
                  break;   // should only be one such transition
               }
            }
            tmpMap.put(nextSymbol, tmpList.toArray(new Stavka[0]));
         }


         enka.put(s1, tmpMap);
      }

      return enka;

   }


   public Map<Stavka, Map<String, Set<Stavka>>> NKAfromENKA (Map<Stavka, Map<String, Stavka[]>> enka){
      // izracuanj epsilon okoline svih stavki
      // NEBITNO: prihvatljiva stanja su ista kao u enka uz pocetno stanje ako je u njegovom eps okruzenju ijedno prihvatljivo
      // za svako stanje s
      //    eps okolina od s nazovimo ju e
      //    za svaki ulazni znak ulzn
      //       ArrayList nextStanja
      //       za svako stanje es iz njegove e okoline
      //          u nextStanja dodaj eps okolinu svih stanja iz prijelaza iz e -ulzn>
      //       u nka dodaj s -ulzn> nextStanja

      Map<Stavka, Map<String, Set<Stavka>>> nka = new HashMap<Stavka, Map<String, Set<Stavka>>>();
      
      Map<Stavka, Set<Stavka>> epsEnv = new HashMap<Stavka, Set<Stavka>>();

      for(Stavka s1 : stavkas){
         Set<Stavka> epsNexts = new HashSet<Stavka>(Arrays.asList(enka.get(s1).get("")));
         epsNexts.add(s1);
         epsEnv.put(s1, epsNexts);
      }

      for(Stavka s1 : stavkas){
         for(Stavka s2 : epsEnv.get(s1)){
            Map<String, Set<Stavka>> tmpMap = new HashMap<String, Set<Stavka>>();
            for(String ul : ulZnakovi){
               Set<Stavka> nextStavkas = new HashSet<Stavka>();
               for(Stavka s3 : enka.get(s2).get(ul)){
                  nextStavkas.add(s3);
               }
               tmpMap.put(ul, nextStavkas);
            }
            nka.put(s1, tmpMap);
         }   
      }




      return nka;
   }

   public static class TransitionSet {
      public Set<Stavka> stateFrom;
      public Map <String, Set<Stavka>> transitions;
      public TransitionSet(Set<Stavka> stateFrom, Map <String, Set<Stavka>> transitions){
         this.stateFrom = stateFrom;
         this.transitions = transitions;
      }
   }

   public ArrayList<TransitionSet> DKAfromNKA (Map<Stavka, Map<String, Set<Stavka>>> nka) {


      ArrayList<TransitionSet> dkaList = new ArrayList<TransitionSet>();

      for(Stavka s : stavkas){
         Set<Stavka> ss = new HashSet<Stavka>();
         ss.add(s);
         dkaList.add(new TransitionSet(ss, new HashMap <String, Set<Stavka>>()));
      }
      for(int stateIndex = 0; stateIndex < dkaList.size(); stateIndex++){
         TransitionSet ts = dkaList.get(stateIndex);

         // empty state
         if(ts.stateFrom.size() == 0){
            for(String a : ulZnakovi){
               ts.transitions.put(a, ts.stateFrom);
            }
            break;
         }
         // else

         for(String a : ulZnakovi){
            Set<Stavka> stateTo = new HashSet<Stavka>();
            for(Stavka s : ts.stateFrom){
               stateTo.addAll(nka.get(s).get(a));  // TODO: check if you can addAll(set)
            }
            // check if stateTo already exists
            boolean exists = false;
            for(TransitionSet searchSet : dkaList){
               if(searchSet.stateFrom.equals(stateTo)){
                  ts.transitions.put(a, searchSet.stateFrom);
                  exists = true;
                  break;
               }
            }
            if(!exists){
               dkaList.add(new TransitionSet(stateTo, new HashMap <String, Set<Stavka>>()));
               ts.transitions.put(a, stateTo);
            }

         }
      }

      return dkaList;

   }

   // za dka iz nka
   // Stanje {
   //    Stavka[]

   // }



   // """""""""""incopmplete"""""""""""
   // dodaj sva stanja na stack kao Stanje (1 element array)

   // za svaki array sa stacka
   //    za svaki znak
   //       nadji skup stanja u koje zajedno prelaze ta stanja
   //       ako takav skup nije na stacku
   //          pretvori skup stanja u Stanje
   //          dodaj ga na vrh
   //       inace
   //          zapisi prijelaz u dka uz konstrukte sa stacka ( ne stvarati nova stanja )
}



