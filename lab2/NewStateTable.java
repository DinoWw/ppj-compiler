import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NewStateTable {
    private static Map<Set<Stavka>, Map<String, Action>> table;

    public NewStateTable(ArrayList<TransitionSet> DKA, String firstSign, ArrayList<String> nonTerminalSigns){
        table = new HashMap<>();

        // TransitionSet (Set<Stavka> , HashMap < String , Set <Stavka> > )

        for (TransitionSet ts : DKA){
            for (String inpString : ts.Mapa.keySet()){
                
                if (nonTerminalSigns.contains(inpString)){
                    addAction(table, ts, inpString, new Action(ts.mapa.get(inpString)));
                    
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

}
