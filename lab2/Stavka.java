import java.util.ArrayList;

public class Stavka {
    public String left;
    public ArrayList<String> right;
    public int dotIndex; // if 0 means its before the element at index 0
    public boolean complete; // discuss if necessary
    public ArrayList<String> beginsSet; // all possible most-left final-chars

    public Stavka(String left, ArrayList<String> right, int dotIndex, boolean complete) {
        this.left = left;
        this.right = right;
        this.dotIndex = dotIndex;
        this.complete = complete;
        this.beginsSet = new ArrayList<>();
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
        String tmp = this.left + " -> " + String.join("",right_tmp) + " --- " + (complete ? "complete" : "-");
        return tmp;
    }
}
