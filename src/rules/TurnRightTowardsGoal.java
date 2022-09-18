package src.rules;

import java.util.Objects;

import src.Agent;
import src.Constants;
import src.Direction;
import src.Environment;
import src.Grid.GridIndex;

public class TurnRightTowardsGoal extends Rule {

    /**
     * Simple constructor
     */
    public TurnRightTowardsGoal() {
        antecedentString = "<GOAL LOCATION IS KNOWN> and " + 
                            "<GOAL IS OUT OF PERIPHERALS> and " + 
                            "<RIGHT TURN ORIENTS AGENT TOWARDS GOAL>";
        actionString = "<TURN RIGHT>";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean condition(Environment env, Agent agent) {
        GridIndex goalIndex = env.getGoalIndex();
        if (Objects.nonNull(goalIndex)
            && !isVisibleByAgent(agent, env, Constants.GOAL)
            && rightTurnOrientsTowardsGoal(agent, env.getGoalIndex())) {
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void action(Environment env, Agent agent) {
        agent.turnRight();
    }

    /**
     * Validates if turning right makes the agent closer to facing the goal. 
     * 
     * @param agent The agent in the environment. Containing currDirection and currIndex.
     * @param goalLocation The location of the goal in the environment.
     * @return True if turning right makes the agent closer to facing the goal. False otherwise.
     */
    protected boolean rightTurnOrientsTowardsGoal(Agent agent, GridIndex goalLocation) {
        return turnOrientsTowardsGoal(agent, goalLocation, Direction.DEGREES_RIGHT_TURN);
    }
}
