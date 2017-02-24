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
