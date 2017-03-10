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

    //check for a win in all directions except up
    //returns -1 for not finished, 0 for tie, 1 for player1 win, 2 for player2 win
    public int checkWin() {
        int player = gameBoard[lastPlayedColumn][lastPlayedRow];


        if (lastPlayedRow > 2) {
            if (player == gameBoard[lastPlayedColumn][lastPlayedRow - 1] &&
                    player == gameBoard[lastPlayedColumn][lastPlayedRow - 2] &&
                    player == gameBoard[lastPlayedColumn][lastPlayedRow - 3])
                return player;
        }

        //limits for how many spots we can go left/right/down/up
        //from the current token before hitting the edge of the board.
        //we're only interested in looking at most 3 spots in either direction (because it's connect 4)
        int leftLimit = lastPlayedColumn > 2 ? -3 : -lastPlayedColumn;
        int rightLimit = lastPlayedColumn < columns-3 ? 3 : columns- lastPlayedColumn -1;
        int downLimit = lastPlayedRow > 2 ? -3 : -lastPlayedRow;
        int upLimit = lastPlayedRow < rows-3 ? 3 : rows- lastPlayedRow -1;

        int count = 0;
        //check the horizontal line going through the token that was just placed
        for (int c = leftLimit; c <= rightLimit; c++) {
            if (gameBoard[lastPlayedColumn+c][lastPlayedRow] == player) {
                count++;
                if (count >= 4) return player;
            } else {
                count = 0;
            }
        }

        count = 0;
        //check ascending diagonal going through the token that was just placed
        for (int i = Math.max(leftLimit, downLimit); i <= Math.min(rightLimit, upLimit); i++) {
            if (gameBoard[lastPlayedColumn+i][lastPlayedRow+i] == player) {
                count++;
                if (count >= 4) return player;
            } else {
                count = 0;
            }
        }

        count = 0;
        //check descending diagonal going through the token that was just placed
        for (int i = Math.max(leftLimit, -upLimit); i <= Math.min(rightLimit, -downLimit); i++) {
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
