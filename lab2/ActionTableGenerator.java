import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// generates both action table and state table in one table
public class ActionTableGenerator {
    private static Map<Set<Stavka>, Map<String, Action>> table ;
    private static StringBuilder code; 

    public ActionTableGenerator(ArrayList<Transformer.TransitionSet> DKA, String firstSign, ArrayList<String> terminalSigns, ArrayList<String> nonTerminalSigns){
        table = new HashMap<>();

        // write to file   
        code = new StringBuilder()
        .append("public class ActionTable{\n")
        .append("private static Map<Set<Stavka>, Map<String, Action>> table = new HashMap<>();\n")
        .append("public ActionTable(){\n");

        // TODO change type
        for (Transformer.TransitionSet ts : DKA){
            int stateIndex = getStateIndex (ts.stateFrom);
            // Action table
            for (Stavka s : ts.stateFrom){
                // case c) from PPJ 15 
                if (s.complete && s.left.equals(firstSign)){
                    checkForKey(ts.stateFrom);
                     // STAVI
                    code.append("table.get(" + stateIndex + ").put('" + s.right.get(s.dotIndex) + "'," + "new Action()" + " );\n");
                    
                    //addAction(table, ts.stateFrom, s.right.get(s.dotIndex), new Action());
                }
                // case b)
                else if (s.complete){
                    for (String endSign : s.beginsSet){
                        
                        checkForKey(ts.stateFrom);

                        // TODO PROVJERIT JEL OVJA TO STRING RADI
                        // REDUCIRAJ
                        code.append("table.get(" + stateIndex + ").put('" + endSign + "'," + "new Action('" + s.left + "' , " + s.right.toString() + " );\n");

                        //addAction(table, ts.stateFrom, endSign, new Action(s));
                    } 
                }
                // case a)
                else if (terminalSigns.contains(s.right.get(s.dotIndex)) && ts.transitions.keySet().contains(s.right.get(s.dotIndex))){
                    
                    checkForKey(ts.stateFrom);
                    
                    // TODO is this correct?
                    // POMAKNI
                    code.append("table.get(" + stateIndex + ").put('" + s.right.get(s.dotIndex) + "'," + "new Action('" + s.right.get(s.dotIndex) + "', false );\n");

                    //addAction(table, ts.stateFrom, s.right.get(s.dotIndex), new Action(ts.transitions.get(s.right.get(s.dotIndex))));
                    
                }
            }

            // State table
            for (String inpString : ts.transitions.keySet()){
                
                if (nonTerminalSigns.contains(inpString)){
                   
                    code.append("table.get(" + stateIndex + ").put('" + inpString + "'," + "new Action('" + ts.transitions.get(inpString) + "', true );\n");
                   
                   // addAction(table, ts.stateFrom, inpString, new Action(ts.transitions.get(inpString)));
                    
                }
            }
        }

        code.append("}\n");
        // todo write GETETR function
        //code.append("public akcija ? getAction(state, inpSign){\n");


        try {
            FileWriter file = new FileWriter("SA/ActionTable.java", false); // false = overwrite
            BufferedWriter output = new BufferedWriter(file);
            output.write(code.toString());
            output.close();
        }
        catch (IOException e) {
            System.err.println("Generator error: couldn't make ActionTable.java");;
        } 

        //----------------------------------------------

        // write ActionEnum.java file
        
        code = new StringBuilder();
        code.append("public enum ActionEnum {\n" + 
                        "    POMAKNI,\n" + 
                        "    REDUCIRAJ,\n" + 
                        "    PRIHVATI,\n" + 
                        "    STAVI\n" + 
                        "\n" + 
                        "}\n" + 
                        "");
        try {
            FileWriter file = new FileWriter("SA/ActionEnum.java", false); // false = overwrite
            BufferedWriter output = new BufferedWriter(file);
            output.write(code.toString());
            output.close();
        }
        catch (IOException e) {
            System.err.println("Generator error: couldn't make ActionEnum.java");;
        } 

        //----------------------------------------------

        // write Action.java file
          
        code = new StringBuilder();
        code.append(  "public class Action {\n" + 
                        "\n" + 
                        "    ActionEnum whatToDo = null;\n" + 
                        "    String nextState = null;\n" + 
                        "    Stavka reduction = null;\n" + 
                        "    public Action(String nextState, boolean isNewState){\n" + 
                        "        if (isNewState){\n" + 
                        "            this.whatToDo = ActionEnum.STAVI;\n" + 
                        "            this.nextState = nextState;\n" + 
                        "        }\n" + 
                        "        else{\n" + 
                        "            this.whatToDo = ActionEnum.POMAKNI;\n" + 
                        "            this.nextState = nextState;\n" + 
                        "        }\n" + 
                        "    }\n" + 
                        "    public Action(Stavka reduction){\n" + 
                        "        this.whatToDo = ActionEnum.REDUCIRAJ;\n" + 
                        "        this.reduction = reduction; \n" + 
                        "        // extract left and right from stavka\n" + 
                        "    }\n" + 
                        "    public Action(){\n" + 
                        "        this.whatToDo = ActionEnum.PRIHVATI;\n" + 
                        "    }\n" + 
                        "}\n" );
        try {
            FileWriter file = new FileWriter("SA/Action.java", false); // false = overwrite
            BufferedWriter output = new BufferedWriter(file);
            output.write(code.toString());
            output.close();
        }
        catch (IOException e) {
            System.err.println("Generator error: couldn't make Action.java");;
        } 


    }

    // // tablica, trenutno stanje (skup stavki), inp znak, akcija
    // private static void addAction(Map<Set<Stavka>, Map<String, Action>> ActionTable, Set<Stavka> s, String inpSign, Action action){

        
    //     // unneccessary : table.get(s).put(inpSign, action);
    //     // write s as Set<> and action as new action
    //     code.append("table.get(" + stateIndex + ").put('" + inpSign + "'," + "new SAction()" + " );\n");
    // }

    // keeps track of keys so they can be added if necessary in the real table
    private static void checkForKey(Set<Stavka> s){
        if (!table.keySet().contains(s)){
            
            // TODO dino ce
            int stateIndex = 0; //Transformer.getStateIndex(s); // s = set stavki

            // redundant but could be used for testing, then we can reconsider
            table.put(s, new HashMap<String, Action>());
            code.append("table.put(" + stateIndex + ", new HashMap<String, Action>());\n");
        }

    }


}
