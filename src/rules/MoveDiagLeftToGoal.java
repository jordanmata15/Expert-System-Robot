package src.rules;

import java.util.Objects;

import src.Agent;
import src.Constants;
import src.Environment;
import src.Grid.GridIndex;

public class MoveDiagLeftToGoal extends Rule {

    /**
     * Simple constructor
     */
    public MoveDiagLeftToGoal() {
        antecedentString = "<GOAL LOCATION IS KNOWN> and " + 
                            "<GOAL IS VISIBLE DIAGONALLY LEFT>";
        actionString = "<MOVE DIAGONALLY LEFT>";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean condition(Environment env, Agent agent) {
        GridIndex goalIndex = env.getGoalIndex();
        GridIndex agentIndex = agent.getCurrIndex();
        GridIndex offsetToMoveBy = agent.getCurrDirection().diagLeftOffset();
        if (Objects.isNull(goalIndex)
            || !existsInDirectionFromIndex(agentIndex, env, offsetToMoveBy, Constants.GOAL)) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void action(Environment env, Agent agent) {
        agent.moveForwardDiagonallyLeft(env);
    }
}
