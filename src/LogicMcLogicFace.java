
public class LogicMcLogicFace implements IGameLogic {
    private State state;
    private final int MAX_DEPTH = 11;
    
    public LogicMcLogicFace() {

    }
	
    public void initializeGame(int columns, int rows, int playerID) {
        state = new State(columns, rows, playerID);
    }
	
    public Winner gameFinished() {
        Winner returnWinner;
        int winner = state.checkWin();

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
        state.insertCoin(column,playerID);
    }

    public int decideNextMove() {
        State minimaxState = new State(state);
        long startTime = System.nanoTime();
        System.out.println("Thinking...");
        int decision = LimitedDepthAB.minimaxDecision(minimaxState, MAX_DEPTH);
        long currentTime = System.nanoTime();
        System.out.println("Decision took " + ((currentTime-startTime)/1000000) + " ms");
        return decision;
    }
}

