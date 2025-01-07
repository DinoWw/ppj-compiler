package lab3.znakovi;

import lab3.tip.Tip;

public class Identifikator extends Konstanta {
    public Tip tip;
    public boolean l_izraz;

    public Identifikator(int lineN, String vrijednost) {
        this.lineN = lineN;
        this.vrijednost = vrijednost;

    }

}
