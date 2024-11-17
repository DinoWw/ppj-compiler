import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.HashSet;


public class GSA{
    private static final String PREFIX_NONTERMINAL_SIGN = "%V";
    private static final String PREFIX_TERMINAL_SIGN = "%T";
    private static final String PREFIX_SYN_SIGN = "%Syn";

    private static ArrayList<String> nonTerminalSigns;
    private static ArrayList<String> terminalSigns;
    private static ArrayList<String> synSigns;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // NONFINAL SIGNS
        nonTerminalSigns = new ArrayList<String>();
        String line = reader.readLine();
        if (!line.startsWith(PREFIX_NONTERMINAL_SIGN)){
            throw new IOException();
        }
        nonTerminalSigns.addAll(Arrays.asList(line.substring(3).split(" ")));

        // FINAL SIGNS
        terminalSigns = new ArrayList<String>();
        line = reader.readLine();
        if (!line.startsWith(PREFIX_TERMINAL_SIGN)){
            throw new IOException();
        }
        terminalSigns.addAll(Arrays.asList(line.substring(3).split(" ")));

        // SYN SIGNS
        synSigns = new ArrayList<String>();
        line = reader.readLine();
        if (!line.startsWith(PREFIX_SYN_SIGN)){
            throw new IOException();
        }
        synSigns.addAll(Arrays.asList(line.substring(5).split(" ")));

        // GRAMMAR PRODUCTIONS
        Map<String, ArrayList<ArrayList<String>>> productions = new HashMap<String, ArrayList<ArrayList<String>>>();
        String key = null;
        while(null != (line = reader.readLine()) && line.length() != 0){
            if (line.charAt(0)!=' '){
                key = line;
            }
            else{
                if (productions.containsKey(key)){
                    productions.get(key).add( new ArrayList<String>(Arrays.asList(line.substring(1).split(" "))) );
                    // depends how we want to write $
                    //productions.get(key).add( line.substring(1).equals("$") ? "" : line.substring(1) ); 
                }
                else{
                    productions.put(key, new ArrayList<ArrayList<String>>(Arrays.asList(new ArrayList<String>(Arrays.asList( line.substring(1).split(" ") )))));
                    //productions.put(key, new ArrayList<String>(Arrays.asList( line.substring(1).equals("$") ? "" : line.substring(1) )));
                }
            }

        }

        beginsTable(productions);

        ArrayList<Stavka> stavke = generateStavke(productions);

        //Automat epsNKA = Automat(stavke);


        /// PARSING ENDS HERE.

        ArrayList<String> allSigns = new ArrayList<String>(nonTerminalSigns);
        allSigns.addAll(terminalSigns);
        Transformer transformer = new Transformer(stavke, allSigns.toArray(new String[0]));

        System.out.println(allSigns);
        Map<Stavka, Map<String, Stavka[]>> enka = transformer.generateENKA();
        System.out.println("ENKA ENKA ENKA ENKA ENKA ENKA ENKA ENKA ENKA ENKA ENKA ENKA ENKA ENKA ENKA ENKA ENKA ENKA ENKA ENKA ENKA :");
        for(Entry<Stavka, Map<String, Stavka[]>> e1 : enka.entrySet()){
            System.out.println(e1.getKey());
            for(Entry<String, Stavka[]> e2 : e1.getValue().entrySet()){
                System.out.println("\t-> " + e2.getKey());
                for( Stavka s : e2.getValue()){
                    System.out.println("\t\t-> " + s);
                }
            }
        }
        Map<Stavka, Map<String, Set<Stavka>>> nka = transformer.NKAfromENKA(enka);
        System.out.println("NKA  NKA  NKA  NKA  NKA  NKA  NKA  NKA  NKA  NKA  NKA  NKA  NKA  NKA  NKA  NKA  NKA  NKA  NKA  NKA  NKA  :");
        for(Entry<Stavka, Map<String, Set<Stavka>>> e1 : nka.entrySet()){
            System.out.println(e1.getKey());
            for(Entry<String, Set<Stavka>> e2 : e1.getValue().entrySet()){
                System.out.println("\t-> " + e2.getKey());
                for( Stavka s : e2.getValue()){
                    System.out.println("\t\t-> " + s);
                }
            }
        }
        ArrayList<Transformer.TransitionSet> dka = transformer.DKAfromNKA(nka);
        System.out.println("DKA  DKA  DKA  DKA  DKA  DKA  DKA  DKA  DKA  DKA  DKA  DKA  DKA  DKA  DKA  DKA  DKA  DKA  DKA  DKA  DKA  :");
        System.out.println(dka);

    }


    private static ArrayList<Stavka> generateStavke(Map<String, ArrayList<ArrayList<String>>> productions){
        ArrayList<Stavka> stavke = new ArrayList<Stavka>();
        Map<String,Set<String>> beginsTable = beginsTable(productions);

        for (String left : productions.keySet()){
            
            for (ArrayList<String> right : productions.get(left)){
                
                // if complete stavka
                if (right.get(0).equals("$")){
                    stavke.add(new Stavka(left, new ArrayList<>(Arrays.asList("")), 0, new HashSet<String>(), true));
                    continue;
                }
                
                int maxNum = right.size();
               
                for (int i = 0; i<maxNum; i++){
                    Stavka tmp = new Stavka(left, right, i, new HashSet<String>(), false);
                    stavke.add(tmp);
                }

                stavke.add(new Stavka(left, right, maxNum, new HashSet<String>(), true));
            }
        }

        for (Stavka s : stavke){
            System.out.println(s.toString());
        }

        return stavke;
    }
// TODO trhere should be more elems i think idk
    private static Map<String,Set<String>> beginsTable (Map<String, ArrayList<ArrayList<String>>> productions){
        Map<String,Set<String>> table = new HashMap<String,Set<String>>();
        for (String left : nonTerminalSigns){
            table.put(left, new HashSet<String>(Arrays.asList(left)));
        }
 
        // adding most-left elements
        for(String key : table.keySet()){

            for (ArrayList<String> production : productions.get(key)){

                String leftestElem = production.get(0);
                if (!table.get(key).contains(leftestElem)){// better if set but ok :(
                    table.get(key).add(leftestElem);
                }

            }
        }

        // adding all transitive
        boolean changed = true;
        while(changed){
            changed = false;

            for(String key : table.keySet()){
                Set<String> tmp = new HashSet<>(table.get(key));
                for (String sign : table.get(key)){ 
                    if (nonTerminalSigns.contains(sign)){
                        for (ArrayList<String> production : productions.get(sign)){
                            if ( !table.get(key).contains(production.get(0)) ){
                                changed = true;
                                tmp.add(production.get(0));
                            }
                        }
                    }   
                }
                table.put(key, tmp);
            }
        }

        // remove all nonTerminalSigns 
        // maybe better if signs are removed in the previous loops
        for(String key : table.keySet()){
            Set<String> tmp = new HashSet<String>();
            for(String elem : table.get(key)){
                if (terminalSigns.contains(elem) || elem.equals("$")){
                tmp.add(elem);
                }
            }
            table.put(key, tmp);
        }

        //print
        // for(String key : table.keySet()){
        //     System.err.println(key+"  "+table.get(key).toString());
        // }

        return table;
    }
}