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
    private static Map<Set<Stavka>, Integer> mapa;
    
    public ActionTableGenerator(ArrayList<Transformer.TransitionSet> DKA, String firstSign, ArrayList<String> terminalSigns, ArrayList<String> nonTerminalSigns){
        table = new HashMap<>();

        // Set<Stavka> -> integer
        mapa = new HashMap<>();

        int i = 0;
        for (Transformer.TransitionSet ts : DKA){
            mapa.put(ts.stateFrom, i);
            i++;
        }


        // write to file   
        code = new StringBuilder()
        .append("package SA;\nimport java.util.HashMap;\nimport java.util.Map;\nimport java.util.ArrayList;\n import java.util.Arrays;\n")
        .append("public class ActionTable{\n")
        .append("public Map<Integer, Map<String, Action>> table = new HashMap<>();\n")
        .append("public ActionTable(){\n");

        // TODO change type
        for (Transformer.TransitionSet ts : DKA){
            int stateIndex = mapa.get(ts.stateFrom);
            // Action table
            for (Stavka s : ts.stateFrom){
                // case c) from PPJ 15 
                if (s.complete && s.left.equals(firstSign)){
                    checkForKey(ts.stateFrom);
                     // PRIHVATI
                    code.append("table.get(" + stateIndex + ").put(\"$\"," + "new Action()" + " );\n");
                    
                    //addAction(table, ts.stateFrom, s.right.get(s.dotIndex), new Action());
                }
                // case b)
                else if (s.complete){
                    for (String endSign : s.beginsSet){
                        
                        checkForKey(ts.stateFrom);

                        // TODO PROVJERIT JEL OVJA TO STRING RADI
                        // REDUCIRAJ
                        code.append("table.get(" + stateIndex + ").put(\"" + endSign + "\"," + "new Action(\"" + s.left + "\" , new ArrayList<>(\n" + //
                                                        "    Arrays.asList" + arrayToString(s.right) + " )));\n");

                        //addAction(table, ts.stateFrom, endSign, new Action(s));
                    } 
                }
                // case a)
                else if (terminalSigns.contains(s.right.get(s.dotIndex)) && ts.transitions.keySet().contains(s.right.get(s.dotIndex))){
                    
                    checkForKey(ts.stateFrom);
                    
                    // TODO is this correct?
                    // POMAKNI
                    code.append("table.get(" + stateIndex + ").put(\"" + s.right.get(s.dotIndex) + "\"," + "new Action(" + mapa.get(ts.transitions.get(s.right.get(s.dotIndex))) + ", false));\n");

                    //addAction(table, ts.stateFrom, s.right.get(s.dotIndex), new Action(ts.transitions.get(s.right.get(s.dotIndex))));
                    
                }
            }

            // State table
            for (String inpString : ts.transitions.keySet()){
                
                if (nonTerminalSigns.contains(inpString)){
                    checkForKey(ts.stateFrom);
                   // STAVI
                    code.append("table.get(" + stateIndex + ").put(\"" + inpString + "\"," + "new Action(" + mapa.get(ts.transitions.get(inpString)) + ", true ));\n");
                   
                   // addAction(table, ts.stateFrom, inpString, new Action(ts.transitions.get(inpString)));
                    
                }
            }
        }

        code.append("}\n");
        // todo write GETETR function
        code.append("public Action getAction(int state, String input){\n return table.get(state).get(input);}");

       code.append("\n}");


        try {
            FileWriter file = new FileWriter("SA/ActionTable.java", false); // false = overwrite
            BufferedWriter output = new BufferedWriter(file);
            output.write(code.toString());
            output.close();
        }
        catch (IOException e) {
            System.err.println("Generator error: couldn't make ActionTable.java");
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
            
            int stateIndex = mapa.get(s); 

            table.put(s, new HashMap<String, Action>());
            code.append("table.put(" + stateIndex + ", new HashMap<String, Action>());\n");
        }

    }

    private static String arrayToString(ArrayList<String> ar){
        StringBuilder s = new StringBuilder();
        s.append("(");
        boolean first = true;
        for (String elem : ar){
            if (!first){
                s.append(",");

            }
            s.append("\"" + elem + "\"");
            first = false;
        }

        s.append(")");
        return s.toString();
    }

}
