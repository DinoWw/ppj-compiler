import java.util.ArrayList;;

public class Automat {

    // copied 
    private int numStates = 0;
    private ArrayList<StatePair> epsTransitions;
    private ArrayList<Transition> allTransitions;
    private ArrayList<Integer> currStates;
    private int startState, endState;



    public Automat(ArrayList<Stavka> stavke){
        // TODO: generate states

        // TODO add eps transitioon from q0 state



    }



    
    private class State {

        public int id;
        public Stavka stavka;

        public State(int id, Stavka stavka) {
            this.id = id;
            this.stavka = stavka;
        }

        
    }

    private class StatePair{
        private int leftState;
        private int rightState;

        public StatePair(int left, int right) {
			this.leftState = left;
			this.rightState = right;
		}

        @Override
        public String toString(){
            return this.leftState+" -> "+this.rightState;
        }
    }


    private class Transition{
        private int currState;
        private int nextState;
        private char c; 

        public Transition(int curr, int next, char c){
            this.currState = curr;
            this.nextState = next;
            this.c = c;
        }

        @Override
        public String toString(){
            return this.currState+" -> "+this.nextState+" ("+this.c+")";
        }
    }
}
