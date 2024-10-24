package analizator.generated;
import analizator.generated.State;
import analizator.generated.LexClass;
import analizator.structures.Rule;
import analizator.structures.Automat;
import java.util.EnumMap;
import java.util.Map;
public class Rules{
public static Map<State, Rule[]> getRules(){
Map<State, Rule[]> tmp = new EnumMap<>(State.class);tmp.put( State.S_unarni, new Rule[] {new Rule( State.S_unarni, new Automat("\\t|\\_"), null, false, null, -1 ),
new Rule( State.S_unarni, new Automat("\\n"), null, true, null, -1 ),
new Rule( State.S_unarni, new Automat("-"), LexClass.UMINUS, false, State.S_pocetno, -1 ),
new Rule( State.S_unarni, new Automat("-(\\t|\\n|\\_)*-"), LexClass.UMINUS, false, null, 1 ),
});
tmp.put( State.S_komentar, new Rule[] {new Rule( State.S_komentar, new Automat("\\|#"), null, false, State.S_pocetno, -1 ),
new Rule( State.S_komentar, new Automat("\\n"), null, true, null, -1 ),
new Rule( State.S_komentar, new Automat("(\\(|\\)|\\{|\\}|\\||\\*|\\\\|\\$|\\t|\\n|\\_|!|\"|#|%|&|\'|+|,|-|.|/|0|1|2|3|4|5|6|7|8|9|:|;|<|=|>|?|@|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|[|]|^|_|`|a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|~)"), null, false, null, -1 ),
});
tmp.put( State.S_pocetno, new Rule[] {new Rule( State.S_pocetno, new Automat("\\t|\\_"), null, false, null, -1 ),
new Rule( State.S_pocetno, new Automat("\\n"), null, true, null, -1 ),
new Rule( State.S_pocetno, new Automat("#\\|"), null, false, State.S_komentar, -1 ),
new Rule( State.S_pocetno, new Automat("((0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)*|0x((0|1|2|3|4|5|6|7|8|9)|a|b|c|d|e|f|A|B|C|D|E|F)((0|1|2|3|4|5|6|7|8|9)|a|b|c|d|e|f|A|B|C|D|E|F)*)"), LexClass.OPERAND, false, null, -1 ),
new Rule( State.S_pocetno, new Automat("\\("), LexClass.LIJEVA_ZAGRADA, false, null, -1 ),
new Rule( State.S_pocetno, new Automat("\\)"), LexClass.DESNA_ZAGRADA, false, null, -1 ),
new Rule( State.S_pocetno, new Automat("-"), LexClass.OP_MINUS, false, null, -1 ),
new Rule( State.S_pocetno, new Automat("-(\\t|\\n|\\_)*-"), LexClass.OP_MINUS, false, State.S_unarni, 1 ),
new Rule( State.S_pocetno, new Automat("\\((\\t|\\n|\\_)*-"), LexClass.LIJEVA_ZAGRADA, false, State.S_unarni, 1 ),
});
return tmp;
};
}