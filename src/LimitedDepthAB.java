import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Jonas on 26-Feb-17.
 */
public class LimitedDepthAB {
    private static final int INFINITY = Integer.MAX_VALUE;
    private static final int NEGATIVE_INFINITY = Integer.MIN_VALUE;
    private static int MAX_DEPTH;
    private static IHeuristic heuristic;

    public static int minimaxDecision(State state, int maxDepth){
        int action = 0;
        int value = NEGATIVE_INFINITY;
        MAX_DEPTH = maxDepth;
        heuristic = new BetterHeuristic();
        //System.out.println(actions(state));

        for (int a: actions(state)){
            int tempValue = minValue(result(state, a), NEGATIVE_INFINITY, INFINITY, 1);
            System.out.print(tempValue + " ");
            if(tempValue > value){
                value = tempValue;
                action = a;
            }
        }
        System.out.println();
        return action;
    }

    private static int maxValue(State state, int alpha, int beta, int depth){
        int winner = state.checkWin();
        if (winner > -1){
            return utility(state, winner, depth);
        }
        if (depth >= MAX_DEPTH) {
            return heuristic.getUtility(state);
        }

        int value = NEGATIVE_INFINITY;
        int localAlpha = alpha;
        for (int action: actions(state)){
            value = Math.max(value, minValue(result(state, action), localAlpha, beta, depth+1));
            if(value >= beta) return value;
            localAlpha = Math.max(localAlpha,value);
        }
        return value;
    }

    private static int minValue(State state, int alpha, int beta, int depth){
        int winner = state.checkWin();
        if (winner > -1){
            return utility(state, winner, depth);
        }
        if (depth >= MAX_DEPTH) {
            return heuristic.getUtility(state);
        }

        int value = INFINITY;
        int localBeta = beta;
        for (int action: actions(state)){
            value = Math.min(value, maxValue(result(state, action), alpha, localBeta, depth+1));
            if(value <= alpha) return value;
            localBeta = Math.min(localBeta,value);
        }
        return value;
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

        Collections.sort(actions, (a1, a2) ->
            {
                //sort moves in order of closest to the middle
                int v1 = Math.abs(a1 - columns/2);
                int v2 = Math.abs(a2 - columns/2);
                return Integer.compare(v1,v2);
            });

        return actions;
    }

    private static State result(State state, int column) {
        int player = state.getPlayer();
        State resultState = new State(state);
        resultState.insertCoin(column, player);
        return resultState;
    }

    private static int utility(State state, int winner, int depth) {
        int playerId = state.getPlayerId();
        int utility;

        if (winner < 1) {
            utility = 0;
        } else {
            utility = playerId == winner ? 1000-depth : -1000+depth;
        }

        return utility;
    }
}
