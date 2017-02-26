
public class GameLogic implements IGameLogic {
    private int columns = 0;
    private int rows = 0;
    private int playerID;
    private State state;
    
    public GameLogic() {
        //TODO Write your implementation for this method
    }
	
    public void initializeGame(int columns, int rows, int playerID) {
        this.columns = columns;
        this.rows = rows;
        this.playerID = playerID;
        //TODO Write your implementation for this method
        state = new State(columns, rows, playerID);
    }
	
    public Winner gameFinished() {
        //TODO Write your implementation for this method
        Winner returnWinner;
        int winner = state.checkWin();
        //System.out.println("Winner: " + winner);

        switch (winner) {
            case 0:
                returnWinner = Winner.TIE;
                break;

            case 1:
                returnWinner = Winner.PLAYER1;
                break;

            case 2:
                returnWinner = Winner.PLAYER2;
                break;

            default:
                returnWinner = Winner.NOT_FINISHED;
                break;
        }
        return returnWinner;
    }


    public void insertCoin(int column, int playerID) {
        //TODO Write your implementation for this method
        state.insertCoin(column,playerID);
    }

    public int decideNextMove() {
        //TODO Write your implementation for this method
        State minimaxState = new State(state);
        long startTime = System.nanoTime();
        int decision = Minimax.minimaxDecision(minimaxState);
        long currentTime = System.nanoTime();
        System.out.println("Decision took " + ((currentTime-startTime)/1000000) + " ms");
        return decision;
    }
}

