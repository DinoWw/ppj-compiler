package lab3.tip;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import static java.util.Map.entry;

public class Tip {
    TipEnum tip;

    public Tip(TipEnum tip) {
        this.tip = tip;
    }

    public Tip(String tipString) {
        switch (tipString) {
            case "KR_VOID":
                this.tip = TipEnum.VOID;
                break;
            case "KR_CHAR":
                this.tip = TipEnum.CHAR;
                break;
            case "KR_INT":
                this.tip = TipEnum.INT;
                break;
            case "KR_CONST":
                this.tip = TipEnum.CONST;
                break;
            default:
                this.tip = null;
        }
    }

    public boolean equals(Tip tip) {
        return this.tip == tip.tip;
    }

    // there must be a better way to do this...
    public static boolean isX(Tip tip) {
        throw new UnsupportedOperationException();
    }

    public static boolean isT(Tip tip) {
        throw new UnsupportedOperationException();
    }

    public static boolean isNizX(Tip tip) {
        throw new UnsupportedOperationException();
    }

    public static boolean isNizT(Tip tip) {
        throw new UnsupportedOperationException();
    }

    public static boolean isConstX(Tip tip) {
        throw new UnsupportedOperationException();
    }

    public static boolean isConstT(Tip tip) {
        throw new UnsupportedOperationException();
    }

    public static boolean isNizConstX(Tip tip) {
        throw new UnsupportedOperationException();
    }

    public static boolean isNizConstT(Tip tip) {
        throw new UnsupportedOperationException();
    }

    // TODO: promjenit ime, al ne znam opce u sta
    // str 41

    /// int -> int, const(int)
    /// char -> char, const(char), int, const(int)
    /// const int -> int, const(int)
    /// const char -> char, int, const(char), const(int)
    /// niz char -> niz(const(char))
    /// niz int -> niz(const(int))

    public static boolean seMozeImplicitnoPretvoritiIzU(Tip t1, Tip t2) {
        // refleksivnost
        if (t1.equals(t2) && t2.equals(t1)) {
            return true;
        }

        if (!(t1 instanceof KompozitniTip) && t1.tip == TipEnum.INT) {
            // int -> const(int)
            if (t2 instanceof KompozitniTip && t2.tip == TipEnum.CONST
                    && ((KompozitniTip) t2).subTip.tip == TipEnum.INT)
                return true;
        }
        if (!(t1 instanceof KompozitniTip) && t1.tip == TipEnum.CHAR) {
            // char -> const(char)
            if (t2 instanceof KompozitniTip && t2.tip == TipEnum.CONST
                    && ((KompozitniTip) t2).subTip.tip == TipEnum.CHAR)
                return true;

            // char -> const(int)
            if (t2 instanceof KompozitniTip && t2.tip == TipEnum.CONST
                    && ((KompozitniTip) t2).subTip.tip == TipEnum.INT)
                return true;

            // char -> int
            if (!(t2 instanceof KompozitniTip) && t2.tip == TipEnum.INT)
                return true;
        }
        if ((t1 instanceof KompozitniTip) && t1.tip == TipEnum.CONST
                && ((KompozitniTip) t1).subTip.tip == TipEnum.INT) {
            // const(int) -> int
            if (!(t2 instanceof KompozitniTip) && t2.tip == TipEnum.INT)
                return true;

        }
        if ((t1 instanceof KompozitniTip) && t1.tip == TipEnum.CONST
                && ((KompozitniTip) t1).subTip.tip == TipEnum.CHAR) {
            // const(char) -> char
            if (!(t2 instanceof KompozitniTip) && t2.tip == TipEnum.CHAR)
                return true;

            // const(char) -> int
            if (!(t2 instanceof KompozitniTip) && t2.tip == TipEnum.INT)
                return true;

            // const(char) -> const(int)
            if (t2 instanceof KompozitniTip && t2.tip == TipEnum.CONST
                    && ((KompozitniTip) t2).subTip.tip == TipEnum.INT)
                return true;
        }

        if ((t1 instanceof KompozitniTip) && t1.tip == TipEnum.NIZ
                && ((KompozitniTip) t1).subTip.tip == TipEnum.CHAR) {
            // niz((char)) -> niz(const(char))
            if ((t2 instanceof KompozitniTip) && ((KompozitniTip) t2 instanceof KompozitniTip) &&
                    t2.tip == TipEnum.NIZ &&
                    ((KompozitniTip) t2).subTip.tip == TipEnum.CONST &&
                    ((KompozitniTip) ((KompozitniTip) t2).subTip).subTip.tip == TipEnum.CHAR)
                return true;
        }

        if ((t1 instanceof KompozitniTip) && t1.tip == TipEnum.NIZ
                && ((KompozitniTip) t1).subTip.tip == TipEnum.INT) {
            // niz((int)) -> niz(const(int))
            if ((t2 instanceof KompozitniTip) && ((KompozitniTip) t2 instanceof KompozitniTip) &&
                    t2.tip == TipEnum.NIZ &&
                    ((KompozitniTip) t2).subTip.tip == TipEnum.CONST &&
                    ((KompozitniTip) ((KompozitniTip) t2).subTip).subTip.tip == TipEnum.INT)
                return true;
        }

        return false;

    }

    // u T
    public static boolean seMozeImplicitnoPretvoritiUT(Tip t) {

        throw new UnsupportedOperationException();
    }
}