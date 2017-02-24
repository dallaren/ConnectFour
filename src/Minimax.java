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

    private static State result(State state, int column) {
        int player = state.isPlayerOne() ? 1 : 2;
        state.insertCoin(column, player);
        return state;
    }

    private static int utility(State state) {
        int winner = state.checkWin();
        int utility;

        switch (winner) {
            case 0:
                utility = 1;
                break;

            case 1:
                utility = 2;
                break;

            case 2:
                utility = -2;
                break;

            default:
                utility = 0;
                break;
        }
        return utility;
    }
}
