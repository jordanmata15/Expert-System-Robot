package src.rules;

import java.util.Objects;

import src.Agent;
import src.Constants;
import src.Environment;
import src.Grid.GridIndex;

public class MoveDiagRightToGoal extends Rule {

    /**
     * Simple constructor
     */
    public MoveDiagRightToGoal() {
        antecedentString = "<GOAL LOCATION IS KNOWN> and " + 
                            "<GOAL IS VISIBLE DIAGONALLY RIGHT>";
        actionString = "<MOVE DIAGONALLY RIGHT>";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean condition(Environment env, Agent agent) {
        GridIndex goalIndex = env.getGoalIndex();
        GridIndex agentIndex = agent.getCurrIndex();
        GridIndex offsetToMoveBy = agent.getCurrDirection().diagRightOffset();
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
        agent.moveForwardDiagonallyRight(env);
    }
}
