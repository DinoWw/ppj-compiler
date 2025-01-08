package lab3.tip;

// const i niz
public class KompozitniTip extends Tip {
    public Tip subTip;

    public KompozitniTip(TipEnum tip, Tip subTip) {
        super(tip);
        if (!(tip == TipEnum.NIZ || tip == TipEnum.CONST)) {
            throw new Error("Unsupported composite type");
        }
        this.tip = tip;
        this.subTip = subTip;
    }

    public KompozitniTip(String tip, String subTip) {
        super(tip);
        if (!(this.tip == TipEnum.NIZ || this.tip == TipEnum.CONST)) {
            throw new Error("Unsupported composite type");
        }
        this.subTip = new Tip(subTip);
        System.err.println(this.tip.toString() + " " + this.subTip.tip.toString());
    }

    public KompozitniTip(String nadTip, String tip, String subTip) {
        super(nadTip);
        if ((this.tip != TipEnum.NIZ)) {
            throw new Error("Unsupported composite type");
        }
        this.subTip = new KompozitniTip(tip, subTip);
        System.err.println(
                this.tip.toString() + " " + this.subTip.tip.toString() + " "
                        + ((KompozitniTip) this.subTip).subTip.tip.toString());
    }

    @Override
    public boolean equals(Tip tip) {
        if (!(tip instanceof KompozitniTip))
            return false;

        else {
            // const int
            if (tip.tip != this.tip || ((KompozitniTip) tip).subTip != this.subTip)
                return false;

            // niz const int
            if (((KompozitniTip) tip).subTip instanceof KompozitniTip && this.subTip instanceof KompozitniTip) {
                if (((KompozitniTip) ((KompozitniTip) tip).subTip).subTip != ((KompozitniTip) this.subTip).subTip)
                    return false;
            }

            return true;
        }
    }
}
