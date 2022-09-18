package src.rules;

import java.util.Objects;

import src.Agent;
import src.Constants;
import src.Direction;
import src.Environment;
import src.Grid.GridIndex;

public class TurnLeftTowardsGoal extends Rule {

    public TurnLeftTowardsGoal() {
        antecedentString = "<GOAL LOCATION IS KNOWN> and " + 
                            "<GOAL IS OUT OF PERIPHERALS> and " + 
                            "<LEFT TURN ORIENTS AGENT TOWARDS GOAL>";
        actionString = "<TURN LEFT>";
    }

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

    @Override
    public void action(Environment env, Agent agent) {
        agent.turnLeft();
    }

    protected boolean leftTurnOrientsTowardsGoal(Agent agent, GridIndex goalLocation) {
        return turnOrientsTowardsGoal(agent, goalLocation, Direction.DEGREES_LEFT_TURN);
    }
}
