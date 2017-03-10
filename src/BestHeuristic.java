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
                int dx, dy;
                //find the first empty spot in each column
                if (board[x][y] != 0) continue;
                //count up how many

                //<editor-fold desc = "first check downwards if possible">
                {
                    int down = 0;
                    RowScore downStat;
                    dx = 0;
                    dy = -1;

                    if (y > 0) {
                        down = board[x + dx][y + dy];
                    }
                    downStat = getRowScore(columns, rows, board, x, y, dx, dy, down);

                    checkForPoints(down, 0, downStat, new RowScore(), player);
                }
                //</editor-fold>
                //<editor-fold desc= "then check left and right if possible">
                if (!bothWon()) {
                    int left = 0, right = 0;
                    RowScore leftStat, rightStat;

                    //check left
                    dx = -1;
                    dy = 0;
                    if (x > 0) {
                        left = board[x + dx][y + dy];
                    }
                    leftStat = getRowScore(columns, rows, board, x, y, dx, dy, left);

                    //check right
                    dx = 1;
                    dy = 0;
                    if (x < columns - 1) {
                        right = board[x + dx][y + dy];
                    }
                    rightStat = getRowScore(columns, rows, board, x, y, dx, dy, right);

                    checkForPoints(left, right, leftStat, rightStat, player);
                }
                //</editor-fold>
                //<editor-fold desc = "then check diagonally">
                if(!bothWon()) {
                    int leftUp = 0, rightUp = 0, leftDown = 0, rightDown = 0;
                    RowScore rightUpStat, rightDownStat, leftUpStat, leftDownStat;
                    //down left
                    dx = -1;
                    dy = -1;
                    if (x > 0 && y > 0) {
                        leftDown = board[x + dx][y + dy];
                    }
                    leftDownStat = getRowScore(columns, rows, board, x, y, dx, dy, leftDown);
                    //down right
                    dx = 1;
                    dy = -1;
                    if (x < columns - 1 && y > 0) {
                        rightDown = board[x + dx][y + dy];
                    }
                    rightDownStat = getRowScore(columns, rows, board, x, y, dx, dy, rightDown);
                    //up left
                    dx = -1;
                    dy = 1;
                    if (x > 0 && y < rows - 1) {
                        leftUp = board[x + dx][y + dy];
                    }
                    leftUpStat = getRowScore(columns, rows, board, x, y, dx, dy, leftUp);
                    //up right
                    dx = 1;
                    dy = 1;
                    if (x < columns - 1 && y < rows - 1) {
                        rightUp = board[x + dx][y + dy];
                    }
                    rightUpStat = getRowScore(columns, rows, board, x, y, dx, dy, rightUp);
                    //utility
                    checkForPoints(leftUp, rightDown, leftUpStat, rightDownStat, player);
                    checkForPoints(leftDown, rightUp, leftDownStat, rightUpStat, player);
                }
                //</editor-fold>
                if (iWin) {utility += 10;} else if (iPair) {utility += 1;}
                if (uWin) {utility -= 10;} else if (uPair) {utility -= 1;}
            }
        }
        return utility;
    }

    private RowScore getRowScore(int columns, int rows, byte[][] board, int x, int y, int dx, int dy, int current) {
        int inRow = 0;
        int potentialInRow = 1;
        if (current != 0){
            int i, ix, iy;
            for (i = 1; i <= 3; i++) {
                ix = x + dx * i;
                iy = y + dy * i;
                if ((dx == 0 || (ix >= 0 && ix < columns)) && (dy == 0 || (iy >= 0 && iy < rows))) {
                    if (board[ix][iy] != current) {
                        break;
                    }
                    inRow++;
                }
            }
            //check if there are enough spaces here to actually win
            potentialInRow += inRow;
            for (; potentialInRow < 4; i++) {
                ix = x + dx * i;
                iy = y + dy * i;
                if ((dx == 0 || (ix >= 0 && ix < columns)) && (dy == 0 || (iy >= 0 && iy < rows))) {
                    if (board[ix][iy] == oppositePlayer(current)) {
                        break;
                    }
                    potentialInRow++;
                }
            }
            return new RowScore(inRow,potentialInRow);
        }
        return new RowScore();
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
    private void checkForPoints(int a, int b, RowScore aStat, RowScore bStat, int player) {
        int aInRow = aStat.getRowLength();
        int bInRow = bStat.getRowLength();
        int potAInRow = aStat.getPotential();
        int potBInRow = bStat.getPotential();
        if (a != 0 && a == b) {
            givePoints(a, aInRow + bInRow, potAInRow + potBInRow, player);
        } else {
            givePoints(a, aInRow, potAInRow, player);
            givePoints(b, bInRow, potBInRow, player);
        }
    }

    private void givePoints(int current, int inRow, int potInRow, int player) {
        int sign;
        if(potInRow >= 4) {
            sign = (current == player) ? 1 : -1;
            if (inRow >= 3) {
                whoWins(sign);
            } else if (inRow >= 2) {
                whoHasPair(sign);
            }
        }
    }
    private class RowScore{
        int _rowLength = 0;
        int _potential = 0;
        public RowScore(){
        }
        public RowScore(int rowLength, int potential){
            _rowLength = rowLength;
            _potential = potential;
        }
        public int getRowLength(){ return _rowLength; }
        public int getPotential(){ return _potential; }
    }
}
