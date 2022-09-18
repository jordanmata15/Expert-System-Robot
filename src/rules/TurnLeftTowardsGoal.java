package src.rules;

import java.util.Objects;

import src.Agent;
import src.Constants;
import src.Direction;
import src.Environment;
import src.Grid.GridIndex;

public class TurnLeftTowardsGoal extends Rule {

    /**
     * Simple constructor
     */
    public TurnLeftTowardsGoal() {
        antecedentString = "<GOAL LOCATION IS KNOWN> and " + 
                            "<GOAL IS OUT OF PERIPHERALS> and " + 
                            "<LEFT TURN ORIENTS AGENT TOWARDS GOAL>";
        actionString = "<TURN LEFT>";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean condition(Environment env, Agent agent) {
        GridIndex goalIndex = env.getGoalIndex();
        if (Objects.nonNull(goalIndex)
            && !isVisibleByAgent(agent, env, Constants.GOAL)
            && leftTurnOrientsTowardsGoal(agent, env.getGoalIndex())) {
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void action(Environment env, Agent agent) {
        agent.turnLeft();
    }

    /**
     * Validates if turning left makes the agent closer to facing the goal. 
     * 
     * @param agent The agent in the environment. Containing currDirection and currIndex.
     * @param goalLocation The location of the goal in the environment.
     * @return True if turning left makes the agent closer to facing the goal. False otherwise.
     */
    private boolean leftTurnOrientsTowardsGoal(Agent agent, GridIndex goalLocation) {
        return turnOrientsTowardsGoal(agent, goalLocation, Direction.DEGREES_LEFT_TURN);
    }
}
