import java.util.Set;

public class Action {

    ActionEnum whatToDo = null;
    String nextState = null;
    Stavka reduction = null;


    public Action(String nextState, boolean isNewState){
        if (isNewState){
            this.whatToDo = ActionEnum.STAVI;
            this.nextState = nextState;
        }
        else{
            this.whatToDo = ActionEnum.POMAKNI;
            this.nextState = nextState;
        }
    }
    public Action(Stavka reduction){
        this.whatToDo = ActionEnum.REDUCIRAJ;
        this.reduction = reduction; 
        // extract left and right from stavka
    }
    public Action(){
        this.whatToDo = ActionEnum.PRIHVATI;
    }
}
