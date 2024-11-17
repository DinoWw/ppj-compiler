package SA; 
import java.util.ArrayList;
public class Action {

    ActionEnum whatToDo = null;
    Integer nextState = null;
    String left = null;
    ArrayList<String> right = null;

    public Action(int nextState, boolean isNewState){
        if (isNewState){
            this.whatToDo = ActionEnum.STAVI;
            this.nextState = nextState;
        }
        else{
            this.whatToDo = ActionEnum.POMAKNI;
            this.nextState = nextState;
        }
    }
    public Action( String left, ArrayList<String> right){
        this.whatToDo = ActionEnum.REDUCIRAJ;
        this.left = left;
        this.right = right;
    }
    public Action(){
        this.whatToDo = ActionEnum.PRIHVATI;
    }
}
