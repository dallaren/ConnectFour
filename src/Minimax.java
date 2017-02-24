import java.util.ArrayList;

/**
 * Created by Jonas on 24-Feb-17.
 */
public class Minimax {
    static int MAX_NUMBER = Integer.MAX_VALUE;

    static int minimaxDecision(State state){
        int action = 0;
        int v = -MAX_NUMBER;
        
        for (int a: actions(state)){
            int temp = minValue( result(state, a));
            if(temp > v){
                action = a;
            }            
        }
        return action;
    }
    
    static int maxValue(State state){
        if (state.checkWin()>-1){
            return utility(state);
        }
        int v = -MAX_NUMBER;
        for (int a: actions(state)){
            v = Math.max(v, minValue( result(state, a)));           
        }
        return v;
    }
    
    static int minValue(State state){
        if (state.checkWin()>-1){
            return utility(state);
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
  
        for (int column = 0; column < column; column++) {
            if(gameBoard[column][rows-1] == 0) {
                actions.add(column);
            }
        }
        
        return actions;
    }

    private static State result(State state, int column) {
      int player = state.isPlayerOne() ? 1 : 2;
      state.insertCoin(column, player);
      return state;
    }

    private static int utility(State state) {
        int winner = state.checkWin();
        int playerId = state.getPlayerId();
        int utility;
  
        switch (winner) {
            case 0:
                utility = 1;
                break;
  
            case 1:
                utility = playerId == 1 ? 2 : -2;
                break;
  
            case 2:
                utility = playerId == 2 ? 2 : -2;
                break;
  
            default:
                utility = 0;
                break;
        }
        return utility;
    }
}
