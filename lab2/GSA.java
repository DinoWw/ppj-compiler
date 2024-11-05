import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

//import Stavka;

import javax.print.DocFlavor.STRING;

public class GSA{
    private static final String PREFIX_NONFINAL_SIGN = "%V";
    private static final String PREFIX_FINAL_SIGN = "%T";
    private static final String PREFIX_SYN_SIGN = "%Syn";

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // NONFINAL SIGNS
        String line = reader.readLine();
        if (!line.startsWith(PREFIX_NONFINAL_SIGN)){
            throw new IOException();
        }
        String [] nfSigns = line.substring(3).split(" ");

        // FINAL SIGNS
        line = reader.readLine();
        if (!line.startsWith(PREFIX_FINAL_SIGN)){
            throw new IOException();
        }
        String [] fSigns = line.substring(3).split(" ");


        // SYN SIGNS
        line = reader.readLine();
        if (!line.startsWith(PREFIX_SYN_SIGN)){
            throw new IOException();
        }
        String [] synSigns = line.substring(5).split(" ");

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

        generateStavke(productions);
    }


    // maybe return list or smtn
    private static void generateStavke(Map<String, ArrayList<ArrayList<String>>> productions){
        ArrayList<Stavka> stavke = new ArrayList<Stavka>();

        for (String left : productions.keySet()){
            for (ArrayList<String> right : productions.get(left)){
                int maxNum = right.size()+1;
                for (int i = 0; i<maxNum-1; i++){
                    Stavka tmp = new Stavka(left, right, i, false);
                    stavke.add(tmp);
                }
                stavke.add(new Stavka(left, right, maxNum, true));

            }
        }

        for (Stavka s : stavke){
            System.out.println(s.toString());
        }
    }

}