/**
 * Created by mikke on 24-Feb-17.
 */
public class BestHeuristic implements IHeuristic {

    /**
     * currently only checks for connections downwards
     */
    private static boolean iWin = false, uWin = false;
    private static boolean iPair = false, uPair = false;
    public int getUtility(State s) {
        int columns = s.getColumns();
        int rows = s.getRows();
        int player = s.getPlayerId();
        byte[][] board = s.getGameBoard();
        int utility = 0;
        //go through the game board
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows ; y++) {
                iWin = false; uWin = false;
                iPair = false; uPair = false;
                //find the first empty spot in each column
                if (board[x][y] != 0) continue;
                //count up how many
                //<editor-fold desc = "first check downwards if possible">
                int down = 0, downInRow = 0;
                if (y > 0) {
                    down = board[x][y - 1];
                }
                for (int i = 1; y-i >= 0 && i <= 3; i++) {
                    if (board[x][y-i] != down) {break;}
                    downInRow++;
                }
                if (down!= 0){
                    int potentialInRow = 1;
                    //check if there are enough spaces here to actually win
                    potentialInRow += downInRow;
                    int i;
                    for (i = 1; y+i < rows && potentialInRow < 4; i++) {
                        if (board[x][y+i] == oppositePlayer(down)) {break;}
                        potentialInRow++;
                    }
                    int sign = (down == player) ? 1 : -1;
                    //utility += (downInRow>=3?sign:0);
                    if (potentialInRow >=4) {whoWins(sign);}
                }
                //</editor-fold>
                //<editor-fold desc= "then check left and right if possible">
                if (!bothWon()) {
                    int left = 0, right = 0;
                    int leftInRow = 0, rightInRow = 0;
                    if (x > 0) {
                        //first check downwards
                        left = board[x - 1][y];
                    }
                    if (x < columns - 1) {
                        //first check downwards
                        right = board[x + 1][y];
                    }
                    int potentialLeftInRow = 1;
                    int potentialRightInRow = 1;
                    //check left
                    if (left != 0) {
                        int i;
                        for (i = 1; x - i >= 0 && i <= 3; i++) {
                            if (board[x - i][y] != left) {
                                break;
                            }
                            leftInRow++;
                        }
                        //check how many in a row there potentially could be
                        potentialLeftInRow += leftInRow;
                        for (; x - i >= 0 && potentialLeftInRow < 4; i++) {
                            if (board[x - i][y] == oppositePlayer(down)) {break;}
                            potentialLeftInRow++;
                        }
                    }
                    //check right
                    if (right != 0) {
                        int i;
                        for (i = 1; x + i < columns && i <= 3; i++) {
                            if (board[x + i][y] != left) {
                                break;
                            }
                            rightInRow++;
                        }
                        //check how many in a row there potentially could be
                        potentialRightInRow += rightInRow;
                        for (; x + i < columns && potentialRightInRow < 4; i++) {
                            if (board[x + i][y] == oppositePlayer(down)) {break;}
                            potentialRightInRow++;
                        }
                    }
                    somethingInRow(left, right, leftInRow, rightInRow, potentialLeftInRow, potentialRightInRow, player);
                }
                //</editor-fold>
                //<editor-fold desc = "then check diagonally">
                if(!bothWon()) {
                    int leftUp = 0, rightUp = 0, leftDown = 0, rightDown = 0;
                    int leftUpInRow = 0, rightUpInRow = 0, leftDownInRow = 0, rightDownInRow = 0;
                    int potentialLeftUpInRow = 1, potentialRightUpInRow = 1;
                    int potentialLeftDownInRow = 1, potentialRightDownInRow = 1;
                    if (x > 0 && y > 0) {
                        //first check downwards
                        leftDown = board[x - 1][y - 1];
                    }
                    if (x < columns - 1 && y > 0) {
                        //first check downwards
                        rightDown = board[x + 1][y - 1];
                    }
                    if (x > 0 && y < rows - 1) {
                        //first check downwards
                        leftUp = board[x - 1][y + 1];
                    }
                    if (x < columns - 1 && y < rows - 1) {
                        //first check downwards
                        rightUp = board[x + 1][y + 1];
                    }
                    int temp;
                    //down left
                    if (leftDown != 0) {
                        int i;
                        for (i = 1; x - i >= 0 && y - i >= 0 && i <= 3; i++) {
                            temp = board[x - i][y - i];
                            if (temp != leftDown) {
                                break;
                            }
                            leftDownInRow++;
                        }
                        potentialLeftDownInRow += leftDownInRow;
                        for (; x - i >= 0 && y - i >= 0 && potentialLeftDownInRow < 4; i++) {
                            if (board[x - i][y- i] == oppositePlayer(down)) {break;}
                            potentialLeftDownInRow++;
                        }
                    }
                    //down right
                    if (rightDown != 0) {
                        int i;
                        for (i = 1; x + i < columns && y - i >= 0 && i <= 3; i++) {
                            temp = board[x + i][y - i];
                            if (temp != rightDown) {
                                break;
                            }
                            rightDownInRow++;
                        }
                        potentialRightDownInRow += rightDownInRow;
                        for (; x + i < columns && y - i >= 0 && potentialRightDownInRow < 4; i++) {
                            if (board[x + i][y - i] == oppositePlayer(down)) {break;}
                            potentialRightDownInRow++;
                        }
                    }
                    //up left
                    if (leftUp != 0) {
                        int i;
                        for (i = 1; x - i >= 0 && y + i < rows && i <= 3; i++) {
                            if (board[x - i][y + i] != leftUp) {
                                break;
                            }
                            leftUpInRow++;
                        }
                        potentialLeftUpInRow += leftUpInRow;
                        for (; x - i >= 0 && y + i < rows && potentialLeftUpInRow < 4; i++) {
                            if (board[x - i][y + i] == oppositePlayer(down)) {break;}
                            potentialLeftUpInRow++;
                        }
                    }
                    //up right
                    if (rightUp != 0) {
                        int i;
                        for (i = 1; x + i < columns && y + i < rows && i <= 3; i++) {
                            if (board[x + i][y + i] != rightDown) {
                                break;
                            }
                            rightDownInRow++;
                        }
                        potentialRightUpInRow += rightUpInRow;
                        for (; x + i < columns && y + i < rows && potentialRightUpInRow < 4; i++) {
                            if (board[x + i][y + i] == oppositePlayer(down)) {break;}
                            potentialRightUpInRow++;
                        }
                    }
                    //utility
                    somethingInRow(leftUp, rightDown, leftUpInRow, rightDownInRow, potentialLeftUpInRow, potentialRightDownInRow, player);
                    somethingInRow(leftDown, rightUp, leftDownInRow, rightUpInRow, potentialLeftDownInRow, potentialRightUpInRow, player);
                }
                //</editor-fold>
                if (iWin) {utility += 10;} else if (iPair) {utility += 1;}
                if (uWin) {utility -= 10;} else if (uPair) {utility -= 1;}
            }
        }
        return utility;
    }

    private void whoWins(int sign) {
        if(sign==1){iWin =true;} else {uWin = true;}
    }
    private void whoHasPair(int sign) {
        if(sign==1){iPair =true;} else {uPair = true;}
    }
    private boolean bothWon(){
        return (iWin == true && uWin == true);
    }

    private int oppositePlayer(int player){
        return ((player==1)?2:1);
    }
    private void somethingInRow(int a, int b, int aInRow, int bInRow, int potAInRow, int potBInRow, int player){
        int sign;
        if (a != 0 && a == b) {
            int pot = potAInRow + potBInRow;
            if(pot >= 4) {
                sign = (a == player) ? 1 : -1;
                if (aInRow + bInRow >= 3) {
                    whoWins(sign);
                } else if (aInRow + bInRow >= 2) {
                    whoHasPair(sign);
                }
                //utility += (aInRow + bInRow >= 3) ? sign : 0;
            }
        } else {
            if(potAInRow >= 4) {
                sign = (a == player) ? 1 : -1;
                if (aInRow >= 3) {
                    whoWins(sign);
                } else if (aInRow >= 2) {
                    whoHasPair(sign);
                }
                //utility += (aInRow >= 3) ? sign : 0;
            }
            if(potBInRow >= 4) {
                sign = (b == player) ? 1 : -1;
                if (bInRow >= 3) {
                    whoWins(sign);
                } else if (bInRow >= 2) {
                    whoHasPair(sign);
                }
                //utility += (bInRow >= 3) ? sign : 0;
            }
        }
    }
}
