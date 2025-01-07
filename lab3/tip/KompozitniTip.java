package lab3.tip;

// const i niz
public class KompozitniTip extends Tip {
    public Tip subTip;

    public KompozitniTip(TipEnum tip, Tip subTip){
        super(tip);
        if( !( tip == TipEnum.NIZ || tip == TipEnum.CONST )){
            throw new Error("Unsupported composite type");
        }
        this.tip = tip;
        this.subTip = subTip;
    }

    @Override
    public boolean equals(Tip tip){
        if( ! (tip instanceof KompozitniTip))
            return false;
        throw new UnsupportedOperationException();
    }
}
