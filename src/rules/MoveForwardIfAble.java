package src.rules;

import src.Agent;
import src.Direction;
import src.Environment;
import src.Grid.GridIndex;

public class MoveForwardIfAble extends Rule{

    /**
     * Simple constructor
     */
    public MoveForwardIfAble() {
        antecedentString = "<FREE SPACE DIRECTLY AHEAD>";
        actionString = "<MOVE FORWARD>";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean condition(Environment env, Agent agent) {
        Direction currDirection = agent.getCurrDirection();
        GridIndex currIndex = agent.getCurrIndex();
        GridIndex forwardIndex = currIndex.add(currDirection.forwardOffset());
        return env.cellIsEmpty(forwardIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void action(Environment env, Agent agent) {
        agent.moveForward(env);
    }
}
