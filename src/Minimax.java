import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonas on 24-Feb-17.
 */
public class Minimax {
    static int minimaxDecision(){
        return 1;
    }
    static int maxValue(){
        return 1;
    }
    static int minValue(){
        return 1;
    }

    private static Iterable<Integer> actions(State state) {
        int columns = state.getColumns();
        int rows = state.getRows();
        int[][] gameBoard = state.getGameBoard();
        ArrayList<Integer> actions = new ArrayList<>(columns);

        for (int column = 0; column < column; column++) {
            if(gameBoard[column][rows-1] == 0) {
                actions.add(column);
            }
        }
        
        return actions;
    }
}
