/**
 * Created by mikke on 24-Feb-17.
 */
public interface IHeuristic {

    /**
     * currently only checks for connections of two
     */
    public int getUtility(State s);
}
