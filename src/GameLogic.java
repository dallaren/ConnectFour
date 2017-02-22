
public class GameLogic implements IGameLogic {
    private int columns = 0;
    private int rows = 0;
    private int playerID;
    private int[][] gameBoard;
    
    public GameLogic() {
        //TODO Write your implementation for this method
    }
	
    public void initializeGame(int columns, int rows, int playerID) {
        this.columns = columns;
        this.rows = rows;
        this.playerID = playerID;
        //TODO Write your implementation for this method
        gameBoard = new int[columns][rows];
    }
	
    public Winner gameFinished() {
        //TODO Write your implementation for this method
        int winner;
        Winner returnWinner = Winner.NOT_FINISHED;

        winner = checkWin();

        switch (winner) {
            case 0:
                returnWinner = Winner.NOT_FINISHED;
                break;

            case 1:
                returnWinner = Winner.PLAYER1;
                break;

            case 2:
                returnWinner = Winner.PLAYER2;
                break;
        }
        return returnWinner;
    }


    public void insertCoin(int column, int playerID) {
        //TODO Write your implementation for this method
        updateGameBoard(column, playerID);
    }

    public int decideNextMove() {
        //TODO Write your implementation for this method
        if(gameBoard[5][rows-1] == 0) {
            return 5;
        } else {
            return 0;
        }
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

    private int checkWin() {
        for (int row = 0; row < rows; row++) { // iterate rows, bottom to top
            for (int column = 0; column < columns; column++) { // iterate columns, left to right
                int player = gameBoard[column][row];
                if (player == 0)
                    continue; // don't check empty slots

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
        return 0; //no winner found
    }
}

