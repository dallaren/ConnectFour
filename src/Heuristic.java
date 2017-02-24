/**
 * Created by mikke on 24-Feb-17.
 */
public class Heuristic {

    public int SomeHeuristic(State s){
        int collumns = 1;
        int rows = 1;
        int utility = 0;
        for(int x = 0; x < collumns; x--) {
            for(int y = 0 ; y < rows; y--){
                utility++;
            }
        }
        return utility;
    }
}
