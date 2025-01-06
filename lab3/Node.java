package lab3;

import lab3.znakovi.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

//import static java.util.Map.entry;


public abstract class Node {

    public ArrayList<Node> children = new ArrayList<>();

    /// prone to change, it would be useful if this method was just polymorphised

    /// on the main class so it has access to the symbol table

    // public void provjeri();

    // returns appropriate node type
    // mogu pretvorit u mapu ak ti se ne svidja ali nisam sigurna kak ce radit ako
    // je neki map value new Object()
    public static Node createNode(String string) {

        switch (string) {
            case "<primarni_izraz>":
                return new PrimarniIzraz();
            case "<postfix_izraz>":
                return new PostfiksIzraz();
            case "<lista_argumenata>":
                return new ListaArgumenata();
            case "<unarni_izraz>":
                return new UnarniIzraz();
            case "<unarni_operator>":
                return new UnarniOperator();
            case "<cast_izraz>":
                return new CastIzraz();
            case "<ime_tipa>":
                return new ImeTipa();
            case "<specifikator_tipa>":
                return new SpecifikatorTipa();
            case "<multiplikativni_izraz>":
                return new MultiplikativniIzraz();
            case "<aditivni_izraz>":
                return new AditivniIzraz();
            case "<odnosni_izraz>":
                return new OdnosniIzraz();
            case "<jednakosni_izraz>":
                return new JednakosniIzraz();
            case "<bin_i_izraz>":
                return new BinIIzraz();
            case "<bin_xili_izraz>":
                return new BinXiliIzraz();
            case "<bin_ili_izraz>":
                return new BinIliIzraz();
            case "<log_i_izraz>":
                return new LogIIzraz();
            case "<log_ili_izraz>":
                return new LogIliIzraz();
            case "<izraz_pridruzivanja>":
                return new IzrazPridruzivanja();
            case "<izraz>":
                return new Izraz();
            case "<slozena_naredba>":
                return new SlozenaNaredba();
            case "<lista_naredbi>":
                return new ListaNaredbi();
            case "<naredba>":
                return new Naredba();
            case "<izraz_naredba>":
                return new IzrazNaredba();
            case "<naredba_grananja>":
                return new NaredbaGrananja();
            case "<naredba_petlje>":
                return new NaredbaPetlje();
            case "<naredba_skoka>":
                return new NaredbaSkoka();
            case "<prijevodna_jedinica>":
                return new PrijevodnaJedinica();
            case "<vanjska_deklaracija>":
                return new VanjskaDeklaracija();
            case "<definicija_funkcije>":
                return new DefinicjaFunkcije();
            case "<lista_parametara>":
                return new ListaParametara();
            case "<deklaracija_parametra>":
                return new DeklaracijaParametra();
            case "<lista_deklaracija>":
                return new ListaDeklaracija();
            case "<deklaracija>":
                return new Deklaracija();
            case "<lista_init_deklaratora>":
                return new ListaInitDeklaratora();
            case "<init_deklarator>":
                return new InitDeklarator();
            case "<izravni_deklarator>":
                return new IzravniDeklarator();
            case "<inicijalizator>":
                return new Inicijalizator();
            case "<lista_izraza_pridruzivanja>":
                return new ListaIzrazaPridruzivanja();

            default:
                System.err.println("Nevažeći element gramatike: " + string);
                return null;
        }

        // public static Map<String,Node> map = Map.ofEntries(

        // entry("<primarni_izraz>", new PrimarniIzraz())

        // );

    }

}
