/**
 * Created by Jonas on 24-Feb-17.
 */
public class State {
    private static int playerTurn;
    private static int[][] gameBoard;

    public static void initialize(int columns, int rows) {
        gameBoard = new int[columns][rows];
        playerTurn = 1;
    }

    public static int getPlayer() {
        return playerTurn;
    }

    public static int[][] getGameBoard() {
        return gameBoard;
    }
}
