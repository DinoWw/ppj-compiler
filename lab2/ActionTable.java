import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ActionTable {
    private static Map<Set<Stavka>, Map<String, Action>> table;

    public ActionTable(ArrayList<TransitionSet> DKA, String firstSign, ArrayList<String> terminalSigns){
        table = new HashMap<>();

        for (TransitionSet ts : DKA){
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
        }
    }

    // static?
    private static void addAction(Map<Set<Stavka>, Map<String, Action>> ActionTable, Set<Stavka> s, String a, Action action){
        if (!table.keySet().contains(s)){
            table.put(s, new HashMap<String, Action>());
        }
        table.get(s).put(a, action);
    }


    private ? getAction (stanje, ulazniznak){
        // access action table 
        return akcija
    }
}
