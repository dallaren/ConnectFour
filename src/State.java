/**
 * Created by Jonas on 24-Feb-17.
 */
public class State {
    private boolean playerOne;
    private int columns;
    private int rows;
    private byte[][] gameBoard;
    private int playerId;
    private int lastPlayedColumn;
    private int lastPlayedRow;

    public State(int columns, int rows, int playerId) {
        this.columns = columns;
        this.rows = rows;
        this.playerId = playerId;
        gameBoard = new byte[columns][rows];
        playerOne = true;
    }

    public State(State oldState) {
        playerOne = oldState.isPlayerOne();
        columns = oldState.getColumns();
        rows = oldState.getRows();
        gameBoard = new byte[columns][rows];
        for (int column = 0; column < columns; column++) {
            for (int row = 0; row < rows; row++) {
                gameBoard[column][row] = oldState.getGameBoard()[column][row];
            }
        }
        playerId = oldState.getPlayerId();
    }

    public boolean isPlayerOne() {
        return playerOne;
    }

    public int getPlayer() {
        return playerOne ? 1 : 2;
    }

    public int getPlayerId() {
        return playerId;
    }

    public byte[][] getGameBoard() {
        return gameBoard;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public void insertCoin(int column, int playerId) {
        lastPlayedColumn = column;
        lastPlayedRow = updateGameBoard(column, playerId);
        playerOne = !playerOne;
    }

    /*public int checkWin() { //TODO optimize
        for (int row = 0; row < rows; row++) { // iterate rows, bottom to top
            for (int column = 0; column < columns; column++) { // iterate columns, left to right
                int player = gameBoard[column][row];
                //System.out.println("Player " + player);
                if (player == 0) {
                    continue; // don't check empty slots
                }

                if (column + 3 < columns &&
                        player == gameBoard[column+1][row] && // look right
                        player == gameBoard[column+2][row] &&
                        player == gameBoard[column+3][row])
                    return player;

                if (row + 3 < rows) {
                    if (player == gameBoard[column][row+1] && // look up
                            player == gameBoard[column][row+2] &&
                            player == gameBoard[column][row+3])
                        return player;
                    if (column + 3 < columns &&
                            player == gameBoard[column+1][row+1] && // look up & right
                            player == gameBoard[column+2][row+2] &&
                            player == gameBoard[column+3][row+3])
                        return player;

                    if (column - 3 >= 0 &&
                            player == gameBoard[column-1][row+1] && // look up & left
                            player == gameBoard[column-2][row+2] &&
                            player == gameBoard[column-3][row+3])
                        return player;
                }
            }
        }

        if(isTie()) return 0;
        return -1; //no winner found
    }*/

    //check for a win in all directions except up
    public int checkWin() {
        int player = gameBoard[lastPlayedColumn][lastPlayedRow];

        if (lastPlayedRow > 2) {
            if (player == gameBoard[lastPlayedColumn][lastPlayedRow - 1] && // look down
                    player == gameBoard[lastPlayedColumn][lastPlayedRow - 2] &&
                    player == gameBoard[lastPlayedColumn][lastPlayedRow - 3])
                return player;
        }

        int columnLowerLimit = lastPlayedColumn > 2 ? -3 : -lastPlayedColumn;
        int columnUpperLimit = lastPlayedColumn < columns-3 ? 3 : columns- lastPlayedColumn -1;
        int rowLowerLimit = lastPlayedRow > 2 ? -3 : -lastPlayedRow;
        int rowUpperLimit = lastPlayedRow < rows-3 ? 3 : rows- lastPlayedRow -1;
        /*System.out.println(columnLowerLimit);
        System.out.println(columnUpperLimit);
        System.out.println(rowLowerLimit);
        System.out.println(rowUpperLimit);*/

        int count = 0;
        //check the horizontal line going through the token that was just placed
        for (int c = columnLowerLimit; c <= columnUpperLimit; c++) {
            if (gameBoard[lastPlayedColumn+c][lastPlayedRow] == player) {
                count++;
                if (count >= 4) return player;
            } else {
                count = 0;
            }
        }

        count = 0;
        //check ascending diagonal going through the token that was just placed
        for (int i = Math.max(columnLowerLimit, rowLowerLimit); i < Math.min(columnUpperLimit, rowUpperLimit); i++) {
            if (gameBoard[lastPlayedColumn+i][lastPlayedRow+i] == player) {
                count++;
                if (count >= 4) return player;
            } else {
                count = 0;
            }
        }

        count = 0;
        //check descending diagonal going through the token that was just placed TODO figure out why the +1 needs to be there
        for (int i = Math.max(columnLowerLimit, -rowUpperLimit); i < Math.min(columnUpperLimit, -rowLowerLimit)+1; i++) {
            if (gameBoard[lastPlayedColumn+i][lastPlayedRow-i] == player) {
                count++;
                if (count >= 4) return player;
            } else {
                count = 0;
            }
        }

        if(isTie()) return 0;
        return -1; //no winner found
    }

    private boolean isTie() {
        for (int column = 0; column < columns; column++) {
            if (gameBoard[column][rows-1] == 0) {
                return false;
            }
        }
        return true;
    }

    private int updateGameBoard(int column, int playerID) {

        for (int row = 0; row < rows; row++) {
            if(gameBoard[column][row] == 0) {
                gameBoard[column][row] = (byte)playerID;
                return row;
            }
        }
        return -1;
    }

    private void printGameBoard() {
        for (int row = rows-1; row >= 0; row--) {
            for (int column = 0; column < columns; column++) {
                System.out.print(gameBoard[column][row]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
