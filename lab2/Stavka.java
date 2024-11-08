import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class Stavka {
    public String left;
    public ArrayList<String> right;
    public int dotIndex; // if 0 = its before the element at index 0
    public boolean complete; // discuss if necessary
    public Set<String> beginsSet; // all possible most-left final-chars

    public Stavka(String left, ArrayList<String> right, int dotIndex, Set<String> begins, boolean complete) {
        this.left = left;
        this.right = right;
        this.dotIndex = dotIndex;
        this.beginsSet = begins;
        this.complete = complete;
    }

    public Stavka(){
        this.left = "q0";
        this.right = new ArrayList<>();
        this.dotIndex = 0;
        this.beginsSet = new HashSet<>();
        this.complete = false;
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
        String tmp = this.left + " -> " + String.join("",right_tmp) + " " + this.beginsSet.toString().replace('[', '{').replace(']', '}') ; //+ "   " + (complete ? "complete" : "-") ;
        return tmp;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }
 
        if (!(o instanceof Stavka)) {
            return false;
        }
         
        Stavka s = (Stavka) o;
         
        return (this.left.equals(s.left) && this.right.equals(s.right) && this.dotIndex==s.dotIndex);
    }
}
