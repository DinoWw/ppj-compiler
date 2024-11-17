import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class eNKA {
 
    public ArrayList<StatePair> epsTransitions;
    public ArrayList<Transition> allTransitions;
    private Stavka startState;
    private ArrayList<Stavka> stavke;
    Map<String, Set<String>> beginsMap;

    private String oznakaKrajaNiza = "$";

    public eNKA(ArrayList<Stavka> stavke, Map<String, Set<String>> beginsMap, String startSign){

        this.stavke = stavke;
        this.beginsMap = beginsMap;
        epsTransitions = new ArrayList<>();
        allTransitions = new ArrayList<>();

        // q0 state 
        startState = new Stavka();

        for (Stavka s : stavke){
            if ( s.left.equals(startSign) && s.dotIndex==0 ){
                s.beginsSet.add(oznakaKrajaNiza);
                epsTransitions.add(new StatePair(startState, s));
                // begin tree build
                step(s);
            }
        }

        // print
        //printAllTransitions();
    }


    public void step(Stavka s){
        if (s.complete){
            return;
        }
        // transition with inp char (S -> .A  --- S -> A.)
        for (Stavka nextStavka : stavke ){ // i think this could be better with a Map
            
            if (nextStavka.left.equals(s.left) && nextStavka.right.equals(s.right) && nextStavka.dotIndex == s.dotIndex+1){
                nextStavka.beginsSet = s.beginsSet; // copy beginsSet from parent
                Transition t = new Transition(s, nextStavka, s.right.get(s.dotIndex));

                if (allTransitions.contains(t)){
                    return;
                }
                allTransitions.add(t);
                step(nextStavka);
                
            }           
        }

        // epsilon transitions (S -> .A --- A -> .BA)
        for (Stavka nextStavka : stavke ){ 

            if ( nextStavka.dotIndex==0 && nextStavka.left.equals(s.right.get(s.dotIndex)) ){
                // set beginsSet depending on parent production
                if (s.right.size() > s.dotIndex+1){
                    nextStavka.beginsSet = beginsMap.get(s.right.get(s.dotIndex+1)); // set beginsSet as begins(A)
                }
                else{
                    nextStavka.beginsSet = s.beginsSet; // set beginsSet the same as parent
                }
                StatePair pair = new StatePair(s, nextStavka);

                if (epsTransitions.contains(pair)){
                    return;
                }
                epsTransitions.add(pair);
                
                step(nextStavka);
            }           
        }

    }


    // print 
    public void printAllTransitions(){
        for (StatePair sp : epsTransitions ){
            System.out.println(sp.toString());
        }
        for (Transition t : allTransitions){
            System.out.println(t.toString());

        }
    }

    private class StatePair{
        private Stavka leftState;
        private Stavka rightState;

        public StatePair(Stavka left, Stavka right) {
			this.leftState = left;
			this.rightState = right;
		}

        
        @Override
        public String toString(){
            return this.leftState.toString()+" -----> "+this.rightState.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
     
            if (!(o instanceof StatePair)) {
                return false;
            }
             
            StatePair sp = (StatePair) o;

            return (this.leftState.equals(sp.leftState) && this.rightState.equals(sp.rightState));
        }
    }


    private class Transition{
        private Stavka currState;
        private Stavka nextState;
        private String inp; 

        public Transition(Stavka curr, Stavka next, String inp){
            this.currState = curr;
            this.nextState = next;
            this.inp = inp;
        }

        @Override
        public String toString(){
            return this.currState.toString()+" ---" +this.inp+ "---> " +this.nextState.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
     
            if (!(o instanceof Transition)) {
                return false;
            }
             
            Transition t = (Transition) o;

            return (this.currState.equals(t.currState) && this.nextState.equals(t.nextState) && this.inp.equals(t.inp));
        }

    }
}
