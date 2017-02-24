/**
 * Created by Jonas on 24-Feb-17.
 */
public class State {
    private boolean playerOne;
    private int columns;
    private int rows;
    private int[][] gameBoard;

    public State(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        gameBoard = new int[columns][rows];
        playerOne = true;
    }

    public boolean isPlayerOne() {
        return playerOne;
    }

    public int[][] getGameBoard() {
        return gameBoard;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public void insertCoin(int column, int playerId) {
        updateGameBoard(column, playerId);
        playerOne = !playerOne;
    }

    public int checkWin() {
        boolean isTie = true;
        for (int row = 0; row < rows; row++) { // iterate rows, bottom to top
            for (int column = 0; column < columns; column++) { // iterate columns, left to right
                int player = gameBoard[column][row];
                if (player == 0) {
                    isTie = false;
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
        if(isTie) return 0;
        return -1; //no winner found
    }

    private void updateGameBoard(int column, int playerID) {

        for (int row = 0; row < rows; row++) {
            if(gameBoard[column][row] == 0) {
                gameBoard[column][row] = playerID;
                break;
            }
        }
        //printGameBoard();
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
