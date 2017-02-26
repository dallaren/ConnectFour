import java.util.ArrayList;

/**
 * Created by Jonas on 26-Feb-17.
 */
public class MinimaxAB {
    private static final int INFINITY = Integer.MAX_VALUE;
    private static final int NEGATIVE_INFINITY = Integer.MIN_VALUE;

    public static int minimaxDecision(State state){
        int action = 0;
        int v = NEGATIVE_INFINITY;

        for (int a: actions(state)){
            int temp = minValue(result(state, a), NEGATIVE_INFINITY, INFINITY);
            if(temp > v){
                v = temp;
                action = a;
            }
        }

        return action;
    }

    private static int maxValue(State state, int alpha, int beta){
        int winner = state.checkWin();
        if (winner > -1){
            return utility(state, winner);
        }
        int v = NEGATIVE_INFINITY;
        int localAlpha = alpha;
        for (int action: actions(state)){
            v = Math.max(v, minValue(result(state, action), localAlpha, beta));
            if(v >= beta) return v;
            localAlpha = Math.max(localAlpha,v);
        }
        return v;
    }

    private static int minValue(State state, int alpha, int beta){
        int winner = state.checkWin();
        if (winner > -1){
            //System.out.println("terminal");
            return utility(state, winner);
        }
        int v = INFINITY;
        int localBeta = beta;
        for (int action: actions(state)){
            v = Math.min(v, maxValue(result(state, action), alpha, localBeta));
            if(v <= alpha) return v;
            localBeta = Math.min(localBeta,v);
        }
        return v;
    }

    private static Iterable<Integer> actions(State state) {
        int columns = state.getColumns();
        int rows = state.getRows();
        byte[][] gameBoard = state.getGameBoard();
        ArrayList<Integer> actions = new ArrayList<>(columns);

        for (int column = 0; column < columns; column++) {
            if(gameBoard[column][rows-1] == 0) {
                actions.add(column);
            }
        }

        return actions;
    }

    private static State result(State state, int column) {
        int player = state.getPlayer();
        State resultState = new State(state);
        resultState.insertCoin(column, player);
        return resultState;
    }

    private static int utility(State state, int winner) {
        int playerId = state.getPlayerId();
        int utility;

        if (winner < 1) {
            utility = 0;
        } else {
            utility = playerId == winner ? 2 : -2;
        }

        return utility;
    }
}
