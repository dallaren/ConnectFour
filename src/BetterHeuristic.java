/**
 * Created by mikke on 24-Feb-17.
 */
public class BetterHeuristic implements IHeuristic {

    /**
     * currently only checks for connections downwards
     */
    public int getUtility(State s) {
        int columns = s.getColumns();
        int rows = s.getRows();
        int player = s.getPlayerId();
        int[][] board = s.getGameBoard();
        int utility = 0;
        int sign = 1;
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows ; y++) {
                //find the first empty spot in each column
                if (board[x][y] != 0) continue;
                //if not on the bottom layer
                if (y > 0) {
                    //first check downwards
                    int current = board[x][y - 1];
                    sign = (current == player)?1:-1;
                    int inRow = 0;
                    for (int i = y; i >= 0; i--) {
                        if (board[x][i] == current) {
                            inRow++;
                        } else {
                            utility += Math.pow(5, inRow) * sign;
                        }
                    }
                }
            }
        }
        return utility;
    }
}
