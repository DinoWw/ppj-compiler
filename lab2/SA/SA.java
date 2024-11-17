package SA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.Stream;


public class SA {

    
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        Stream<String> lexUnitsStream = reader.lines();
        ArrayList<LexUnit> lexUnits = new ArrayList<LexUnit>();

        lexUnitsStream.forEachOrdered((String line) -> {
            String[] elems = line.split(" ", 3);
            lexUnits.add(new LexUnit(Integer.parseInt(elems[1]), elems[0], elems[2]));
        });
        lexUnits.add(new LexUnit(0, "", ""));

        System.out.println("LEX UNTIS: ");
        System.out.println(lexUnits);


        // TODO: run Syntaxer with lexUnits;
        // TODO: fetch table and suyncSymbols
        Syntaxer syntaxer = new Syntaxer(new ActionTable().table, new String[0]);
        syntaxer.setInputArray(lexUnits);
    }
}
