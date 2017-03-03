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
        byte[][] board = s.getGameBoard();
        int utility = 0;
        int sign = 1;
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows ; y++) {
                //find the first empty spot in each column
                if (board[x][y] != 0) continue;
                //if not on the bottom layer
                //<editor-fold desc = "first check downwards if possible">
                if (y > 0) {
                    int current = board[x][y - 1];
                    sign = (current == player)?1:(current == 0)?0:-1;
                    int inRow = 0;
                    for (int i = y; i >= 0; i--) {
                        if (board[x][i] == current) {
                            inRow++;
                        } else {
                            utility += Math.pow(5, inRow) * sign;
                        }
                    }
                }
                //</editor-fold>
                //<editor-fold desc= "then check left and right if possible">
                sign = 0;
                int left = 0, right = 0;
                if (x > 0) {
                    //first check downwards
                    left = board[x-1][y];}
                if (x < columns-1) {
                    //first check downwards
                    right = board[x+1][y];}

                //the case where on both sides are the same
                if (left != 0 && left == right) {
                    int current = left;
                    sign = (current == player) ? 1 : (current == 0) ? 0 : -1;
                    int inRow = 0;
                    int temp;
                    for (int i = x - 1; i >= 0 && i >= x - 3; i--) {
                        temp = board[i][y];
                        if (temp != current) {
                            //check if there is empty space on the other side of three
                            //if (i == x-4 && temp == 0){inRow++;}
                            //is commented out since it will be counted twice anyhow
                            break;
                        }
                        inRow++;
                    }
                    for (int i = x + 1; i <= columns - 1 && i <= x + 3; i++) {
                        temp = board[i][y];
                        if (temp != current) {
                            //check if there is empty space on the other side of three
                            //if (i == x+4 && temp == 0){inRow++;}
                            //is commented out since it will be counted twice anyhow
                            break;
                        }
                        inRow++;
                    }
                    utility += Math.pow(5, inRow) * sign;
                }
                //the case where both sides aren't the same
                else{
                    if (left != 0) {
                        sign = (left == player) ? 1 : -1;
                        int inRow = 0;
                        int temp;
                        for (int i = x - 1; i >= 0 && i >= x - 3; i--) {
                            temp = board[i][y];
                            if (temp != left) {
                                //check if there is empty space on the other side of three
                                //if (i == x-4 && temp == 0){inRow++;}
                                //is commented out since it will be counted twice anyhow
                                break;
                            }
                            inRow++;
                        }
                        utility += Math.pow(5, inRow) * sign;
                    }
                    if (right != 0) {
                        sign = (right == player) ? 1 : -1;
                        int inRow = 0;
                        int temp;
                        for (int i = x + 1; i <= columns && i <= x + 3; i++) {
                            temp = board[i][y];
                            if (temp != right) {
                                //check if there is empty space on the other side of three
                                //if (i == x-4 && temp == 0){inRow++;}
                                //is commented out since it will be counted twice anyhow
                                break;
                            }
                            inRow++;
                        }
                        utility += Math.pow(5, inRow) * sign;
                    }

                }
                //</editor-fold>
                //<editor-fold desc = "then check diagonally">
                int leftup = 0, rightup = 0,leftdown = 0, rightdown = 0;
                int leftUpInRow = 0, rightUpInRow = 0,leftDownInRow = 0, rightDownInRow = 0;
                if (x > 0 && y > 0) {
                    //first check downwards
                    leftdown = board[x-1][y-1];}
                if (x < columns-1 && y > 0) {
                    //first check downwards
                    rightdown = board[x+1][y];}
                if (x > 0 && y < rows-1) {
                    //first check downwards
                    leftup = board[x-1][y-1];}
                if (x < columns-1 && y < rows-1) {
                    //first check downwards
                    rightup = board[x+1][y];}
                //</editor-fold>
            }
        }
        return utility;
    }
}
