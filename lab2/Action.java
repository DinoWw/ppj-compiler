import java.util.Set;

public class Action {

    ActionEnum whatToDo = null;
    String nextState = null;
    Stavka reduction = null;
    Set<Stavka> novoStanje = null;


    public Action(String nextState){
        this.whatToDo = ActionEnum.POMAKNI;
        this.nextState = nextState;
    }
    public Action(Stavka reduction){
        this.whatToDo = ActionEnum.REDUCIRAJ;
        this.reduction = reduction; 
        // extract left and right from stavka
    }
    public Action(){
        this.whatToDo = ActionEnum.PRIHVATI;
    }
    public Action(Set<Stavka> stanje){
        this.whatToDo = ActionEnum.STAVI;
        this.novoStanje = stanje;
    }

}
