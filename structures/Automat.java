package structures;
import java.util.ArrayList;

/**
 * checks regEx
 */
public class Automat {

    private int numStates = 0;
    private ArrayList<StatePair> epsTransitions;
    private ArrayList<Transition> allTransitions;
    private ArrayList<Integer> currStates;
    private int startState, endState;

    public Automat(String regEx){
        this.epsTransitions = new ArrayList<Automat.StatePair>();
        this.allTransitions = new ArrayList<Automat.Transition>();
        this.currStates = new ArrayList<Integer>();

        StatePair pair = this.transform(regEx);
        this.startState = pair.leftState;
        this.endState = pair.rightState;

        this.currStates.add(this.startState);

        doEpsTransitions();
    
    }
    
    public void reset(){
        this.currStates.clear();
        this.currStates.add(startState);

        doEpsTransitions();

    }

    public int newState(){
        this.numStates+=1;
        return this.numStates;
    }

    /**
     *  checks if char at index i is operator 
     */
    public boolean isOperator(String regEx, int i){
        int numOfSlashes = 0;
        while(i-1>0 &&  regEx.charAt(i)=='\\'){
            numOfSlashes++;
            i--; 
        }
        return numOfSlashes%2 == 0;
    }


    public StatePair transform(String regEx){
        ArrayList<String> choices = new ArrayList<String>();
        int numOpenBrackets = 0;
        int end = 0; // pointer to last char thats been grouped 
        for (int i = 0; i<regEx.length(); i++){
            if (regEx.charAt(i) == '(' && isOperator(regEx, i)){
                numOpenBrackets++;
            }
            else if (regEx.charAt(i) == ')' && isOperator(regEx, i)){
                numOpenBrackets--;
            }
            else if(numOpenBrackets == 0 && regEx.charAt(i) == '|' && isOperator(regEx, i)){
                choices.add(regEx.substring(end, i));
                end = i+1;
            }
        }
        if (end!=0){  // there has been at least one | 
            choices.add(regEx.substring(end, regEx.length()));
        }



        int leftState = newState(); 
        int rightState = newState();

        if (end!=0){ // recursively invokes pretvori for each sub-expression
            for(int i = 0; i<choices.size(); i++){
                StatePair temp = transform(choices.get(i));
                addEpsTransition(leftState,temp.leftState);
                addEpsTransition(temp.rightState,rightState);

            }
        }
        else{
            boolean prefixed = false;
            int lastState = leftState;
            int i = 0;
            while(i<regEx.length()){
                int a, b;
                if (prefixed){
                    // slucaj 1
                    prefixed = false;
                    char transChar;
                    if (regEx.charAt(i) == 't'){
                        transChar = '\t';
                    }
                    else if (regEx.charAt(i) == 'n'){
                        transChar = '\n';
                    }
                    else if (regEx.charAt(i) == '_'){
                        transChar = ' ';
                    }
                    else{
                        transChar = regEx.charAt(i);
                    }
                    a = newState();
                    b = newState();
                    //System.out.println("dodajem "+a+"->"+b+" "+prijelazniZnak);
                    addTransition(a, b, transChar);
                }
                else{
                    // slucaj 2
                    if(regEx.charAt(i) == '\\'){
                        prefixed = true;
                        i++;
                        continue;
                    }
                    if (regEx.charAt(i) != '('){
                        a = newState();
                        b = newState();
                        if (regEx.charAt(i) == '$'){
                            addEpsTransition(a, b);
                        }
                        else{
                            // System.out.println("dodajem "+a+"->"+b+" "+regEx.charAt(i));
                            addTransition(a, b, regEx.charAt(i));
                        }
                    }
                    else{
                        int j = indexOfClosedBracket(i,regEx);
                        StatePair temp = transform(regEx.substring(i+1, j));
                        a = temp.leftState;
                        b = temp.rightState;
                        i = j;
                    }
                }

                // checks if repeating
                if (i+1<regEx.length() && regEx.charAt(i+1) == '*'){
                    int x = a;
                    int y = b;
                    a = newState();
                    b = newState();
                    addEpsTransition(a, x);
                    addEpsTransition(y, b);
                    addEpsTransition(a, b);
                    addEpsTransition(y, x);
                    i++; 
                }

                addEpsTransition(lastState, a);
                lastState = b;

                i++;
            }
            addEpsTransition(lastState,rightState);
        }

        return new StatePair(leftState,rightState);
    }

    /*
     * returns true if automat u prihavljivom stanju
     */
    public boolean isAccepted(){
        return this.currStates.contains(this.endState);
    }

    /* dovoljno je provjerit postoji li prijelaz ili ne, 
    ako da onda nastavit feedat automat charovima.
    ako ne onda je niz odbijen */
    public boolean doTransition(char c){
        boolean any = false; // checks if any is possible
        ArrayList<Integer> nova_trenutnaStanja = new ArrayList<Integer>();
        for (Transition p : this.allTransitions){
            if (this.currStates.contains(p.currState) && p.c==c){
                nova_trenutnaStanja.add(p.nextState);
                any = true;
            }
        }
        this.currStates = nova_trenutnaStanja; 
        doEpsTransitions();

        return any;
    }



    private void addTransition(int curr, int next, char c){
        this.allTransitions.add(new Transition(curr, next, c));
    }


    /**
     * adds epsilon transition between states a and b to epsPrijelazi ArrayList 
     */
    private void addEpsTransition(int a, int b){
        this.epsTransitions.add(new StatePair(a, b));
    }

    private void doEpsTransitions(){
        boolean any = true;
        while (any){
            any = false;
            for (StatePair p : this.epsTransitions){
                if (this.currStates.contains(p.leftState) && !this.currStates.contains(p.rightState)){
                    this.currStates.add(p.rightState);
                    any = true;
                }
            }
        }
    }

    private int indexOfClosedBracket(int i, String regEx){
        int opened = 0;
        for (int a = i+1; a<regEx.length(); a++){
            if (regEx.charAt(a) == '('){ 
                opened++;
            }
            else if (regEx.charAt(a) == ')'){
                if (opened==0) {
                    return a;
                }
                else opened--;
            }
        }
        return -1; // should always return a
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