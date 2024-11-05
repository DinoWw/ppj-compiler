import java.util.ArrayList;
import java.util.Set;

public class Stavka {
    public String left;
    public ArrayList<String> right;
    public int dotIndex; // if 0 means its before the element at index 0
    public boolean complete; // discuss if necessary
    public Set<String> beginsSet; // all possible most-left final-chars

    public Stavka(String left, ArrayList<String> right, int dotIndex, Set<String> begins, boolean complete) {
        this.left = left;
        this.right = right;
        this.dotIndex = dotIndex;
        this.beginsSet = begins;
        this.complete = complete;
    }

    // could be more efficient but we never really need to print these out
    @Override
    public String toString(){ 
        ArrayList<String> right_tmp = (ArrayList<String>)this.right.clone();
        if (dotIndex <= right_tmp.size()) {
            right_tmp.add(this.dotIndex,".");
        }
        else{
            right_tmp.add(".");
        }
        String tmp = this.left + " -> " + String.join("",right_tmp) + "   " + this.beginsSet.toString().replace('[', '{').replace(']', '}') + "   " + (complete ? "complete" : "-");
        return tmp;
    }
}
