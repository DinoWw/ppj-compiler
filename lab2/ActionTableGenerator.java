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

    public ActionTable(ArrayList<TransitionSet> DKA, String firstSign, ArrayList<String> terminalSigns, ArrayList<String> nonTerminalSigns){
        table = new HashMap<>();

        // write to file   
        code = new StringBuilder()
        .append("public class ActionTable{\n")
        .append("private static Map<Set<Stavka>, Map<String, Action>> table = new HashMap<>();\n")
        .append("public ActionTable(){\n");

        // TODO change type
        for (TransitionSet ts : DKA){

            // Action table
            for (Stavka s : ts.ss){
                // case c) from PPJ 15 
                if (s.complete && s.left.equals(firstSign)){
                    addAction(table, ts, s.right.get(s.dotIndex), new Action());
                }
                // case b)
                else if (s.complete){
                    for (String endSign : s.beginsSet){
                        addAction(table, ts, endSign, new Action(s));
                    }
                }
                // case a)
                else if (terminalSigns.contains(s.right.get(s.dotIndex)) && ts.dinoMap.keySet().contains(s.right.get(s.dotIndex))){
                    addAction(table, ts, s.right.get(s.dotIndex), new Action(ts.dinoMap.get(s.right.get(s.dotIndex))));
                    
                }
            }

            // State table
            for (String inpString : ts.Mapa.keySet()){
                
                if (nonTerminalSigns.contains(inpString)){
                    addAction(table, ts, inpString, new Action(ts.mapa.get(inpString)));
                    
                }
            }
        }

        code.append("}\n");
        // todo write function
        //code.append("public akcija ? getAction(state, inpSign){\n");


        try {
            FileWriter file = new FileWriter("SA/ActionTable.java", false); // false - overwrite
            BufferedWriter output = new BufferedWriter(file);
            output.write(code.toString());
            output.close();
        }
        catch (IOException e) {
            System.err.println("Generator error: couldn't make ActionTable.java");;
        } 

    }

    // static
    private static void addAction(Map<Set<Stavka>, Map<String, Action>> ActionTable, Set<Stavka> s, String a, Action action){
        if (!table.keySet().contains(s)){
            // redundant but could be used for testing, then we can reconsider
            table.put(s, new HashMap<String, Action>());
            code.append("table.put("+ s +", new HashMap<String, Action>());\n");
        }
        table.get(s).put(a, action);
        code.append("table.get(" + s + ").put(" + a + "," + action + " );\n");


    }


}
