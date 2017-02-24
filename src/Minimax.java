import java.util.ArrayList;

/**
 * Created by Jonas on 24-Feb-17.
 */
public class Minimax {
    static int MAX_NUMBER = Integer.MAX_VALUE;

    public static int minimaxDecision(State state){
        int action = 0;
        int v = -MAX_NUMBER;
        
        for (int a: actions(state)){
            int temp = minValue( result(state, a));
            if(temp > v){
                v = temp;
                action = a;
            }
        }

        return action;
    }
    
    private static int maxValue(State state){
        int winner = state.checkWin();
        if (winner > -1){
            return utility(state, winner);
        }
        int v = -MAX_NUMBER;
        for (int a: actions(state)){
            v = Math.max(v, minValue( result(state, a)));
        }
        return v;
    }
    
    private static int minValue(State state){
        int winner = state.checkWin();
        if (winner > -1){
            System.out.println("terminal");
            return utility(state, winner);
        }
        int v = MAX_NUMBER;
        for (int a: actions(state)){
            v = Math.min(v, maxValue( result(state, a)));
        }
        return v;
    }
    
    private static Iterable<Integer> actions(State state) {
        int columns = state.getColumns();
        int rows = state.getRows();
        int[][] gameBoard = state.getGameBoard();
        ArrayList<Integer> actions = new ArrayList<>(columns);
  
        for (int column = 0; column < columns; column++) {
            if(gameBoard[column][rows-1] == 0) {
                actions.add(column);
            }
        }
        
        return actions;
    }

    private static State result(State state, int column) {
      int player = state.isPlayerOne() ? 1 : 2;
      State resultState = new State(state);
      resultState.insertCoin(column, player);
      return resultState;
    }

    private static int utility(State state, int winner) {
        int playerId = state.getPlayerId();
        int utility;
  
        switch (winner) {
            case 1:
                utility = playerId == winner ? 2 : -2;
                break;
  
            case 2:
                utility = playerId == winner ? 2 : -2;
                break;
  
            default:
                utility = 0;
                break;
        }
        //System.out.println(utility);
        return utility;
    }
}
