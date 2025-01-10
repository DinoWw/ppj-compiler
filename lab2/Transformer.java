import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.plaf.nimbus.State;

public class Transformer{

   private ArrayList<Stavka> stavkas;
   private String[] ulZnakovi;
   private Stavka startStavka;

   public Transformer(ArrayList<Stavka> stavkas, String[] ulZnakovi){
      this.stavkas = stavkas;
      this.ulZnakovi = ulZnakovi;
   }

   static boolean zavrsni(String s){
      return s.startsWith("<");
   }


   public Map<Stavka, Map<String, Stavka[]>> ENKAtoENKAMap(eNKA enka){

      Map<Stavka, Map<String, ArrayList<Stavka>>> enkaMap = new HashMap<Stavka, Map<String, ArrayList<Stavka>>>();


      for(eNKA.Transition t : enka.allTransitions){
         if(!enkaMap.containsKey(t.currState)){
            enkaMap.put(t.currState, new HashMap<String, ArrayList<Stavka>>());
         }
         if(!enkaMap.containsKey(t.nextState)){
            enkaMap.put(t.nextState, new HashMap<String, ArrayList<Stavka>>());
         }
         if(!enkaMap.get(t.currState).containsKey(t.inp)){
            enkaMap.get(t.currState).put(t.inp, new ArrayList<Stavka>());
         }
         enkaMap.get(t.currState).get(t.inp).add(t.nextState);
      }

      for(eNKA.StatePair s : enka.epsTransitions){
         if(!enkaMap.containsKey(s.leftState)){
            enkaMap.put(s.leftState, new HashMap<String, ArrayList<Stavka>>());
         }
         if(!enkaMap.containsKey(s.rightState)){
            enkaMap.put(s.rightState, new HashMap<String, ArrayList<Stavka>>());
         }
         if(!enkaMap.get(s.leftState).containsKey("")){
            enkaMap.get(s.leftState).put("", new ArrayList<Stavka>());
         }
         enkaMap.get(s.leftState).get("").add(s.rightState);
      }

      Map<Stavka, Map<String, Stavka[]>> output = new HashMap<Stavka, Map<String, Stavka[]>>();

      for(Entry<Stavka, Map<String, ArrayList<Stavka>>> m1 : enkaMap.entrySet()){
         output.put(m1.getKey(), new HashMap<String, Stavka[]>());
         for(Entry<String, ArrayList<Stavka>> m2 : m1.getValue().entrySet()){
            output.get(m1.getKey()).put(m2.getKey(), m2.getValue().toArray(new Stavka[0]));
         }
      }

      return output;

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
         ArrayList<Stavka> epsNexts = new ArrayList<Stavka>();
         epsNexts.add(s1);

         for(int i = 0; i < epsNexts.size(); i++){
            Stavka s2 = epsNexts.get(i);
            if(!enka.get(s2).containsKey(""))
               continue;
            // else
            for(Stavka potential : enka.get(s2).get("")){
               if(!epsNexts.contains(potential)){
                  epsNexts.add(potential);
               }
            }
         }
         epsEnv.put(s1, new HashSet<Stavka>(epsNexts));
      }
      System.out.println("EPSENV START");
      System.out.println(epsEnv);
      System.out.println("EPSENV END");

      for(Stavka s1 : stavkas){
         // initialize map and fill with entries for each InSymbol
         nka.put(s1, new HashMap<String, Set<Stavka>>());

         for(String ul : ulZnakovi){
            nka.get(s1).put(ul, new HashSet<Stavka>());
         }
         

         for(Stavka s2 : epsEnv.get(s1)){
            for(String ul : ulZnakovi){
               if(!enka.get(s2).containsKey(ul)){
                  // nka.get(s1).get(ul).addAll(nextStavkas);
                  continue;
               }
               for(Stavka s3 : enka.get(s2).get(ul)){
                  nka.get(s1).get(ul).addAll(epsEnv.get(s3));
               }
            }
            // nka.get(s1).putAll(tmpSet);
         }   
      }




      return nka;
   }

   public static class TransitionSet {
      public Set<Stavka> stateFrom;
      public Map <String, Set<Stavka>> transitions;
      public int stateIndex;
      public TransitionSet(Set<Stavka> stateFrom, Map <String, Set<Stavka>> transitions){
         this.stateFrom = stateFrom;
         this.transitions = transitions;
      }

      @Override
      public String toString(){
         StringBuilder s = new StringBuilder();
         s.append(stateFrom + " ->\n");
         for(Entry<String, Set<Stavka>> e1 : transitions.entrySet()){
            s.append("\t -> " + e1.getKey() + " -> ");
            s.append(e1.getValue() + "\n");
        }
        return s.toString();
      }
   }

   public ArrayList<TransitionSet> DKAfromNKA (Map<Stavka, Map<String, Set<Stavka>>> nka) {


      ArrayList<TransitionSet> dkaList = new ArrayList<TransitionSet>();

      // for(Stavka s : stavkas){
      //    Set<Stavka> ss = new HashSet<Stavka>();
      //    ss.add(s);
      //    dkaList.add(new TransitionSet(ss, new HashMap <String, Set<Stavka>>()));
      // }

      // add initial state
      Set<Stavka> ss = new HashSet<Stavka>();
      ss.add(startStavka);
      dkaList.add(new TransitionSet(ss, new HashMap <String, Set<Stavka>>()));

      for(int stateIndex = 0; stateIndex < dkaList.size(); stateIndex++){
         TransitionSet ts = dkaList.get(stateIndex);
         // empty state
         if(ts.stateFrom.size() == 0){
            for(String a : ulZnakovi){
               ts.transitions.put(a, ts.stateFrom);
            }
            continue;
         }
         // else

         for(String a : ulZnakovi){
            Set<Stavka> stateTo = new HashSet<Stavka>();
            for(Stavka s : ts.stateFrom){
               stateTo.addAll(nka.get(s).get(a));
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


      for(int i = 0; i < dkaList.size(); i++){
         dkaList.get(i).stateIndex = i;
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


   /* THIS DOES NOT WORK
   public Map<Stavka, Map<String, Stavka[]>> generateENKA(){
      Map<Stavka, Map<String, Stavka[]>> enka = new HashMap<Stavka, Map<String, Stavka[]>>();


      
   
      for(Stavka s1 : stavkas){
         // initialize map
         Map<String, Stavka[]> tmpMap = new HashMap<String, Stavka[]>();
         
         if(s1.complete){
            enka.put(s1, new HashMap<String, Stavka[]>());
            continue;
         }

         String nextSymbol = s1.right.get(s1.dotIndex);

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
               try{
                  if(s2.left.equals(s1.left) && nextSymbol.equals(s2.right.get(s2.dotIndex-1))){
                     tmpList.add(s2);
                     break;   // should only be one such transition
                  }
               }
               catch(IndexOutOfBoundsException e){
                  continue;
               }
            }
            tmpMap.put(nextSymbol, tmpList.toArray(new Stavka[0]));
         }


         enka.put(s1, tmpMap);
      }
      
      return enka;
      
   }
      */
}



