
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
        for(LexUnit lu: lexUnits){
            System.out.print(lu.contents);
            System.out.print(" - ");
            System.out.print(lu.lexUnit);
            System.out.print(" - ");
            System.out.print(lu.lineNumber);
            System.out.println();
        }

        System.out.println(new ActionTable().table);
        // TODO: run Syntaxer with lexUnits;
        // TODO: fetch table and suyncSymbols
        System.out.println("SYNTAXER BELOW: ");
        Syntaxer syntaxer = new Syntaxer(new ActionTable().table, new String[0]);
        syntaxer.setInputArray(lexUnits);

        syntaxer.analyse();

        System.out.println(syntaxer.syntaxTree);

    }
}
