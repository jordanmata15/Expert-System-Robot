package src.rules;

import src.Agent;
import src.Direction;
import src.Environment;
import src.Grid.GridIndex;

public class MoveDiagRightIfAble extends Rule{

    /**
     * Simple constructor
     */
    public MoveDiagRightIfAble() {
        antecedentString = "<FREE SPACE AHEAD DIAGONALLY RIGHT>";
        actionString = "<MOVE DIAG RIGHT>";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean condition(Environment env, Agent agent) {
        Direction currDirection = agent.getCurrDirection();
        GridIndex currIndex = agent.getCurrIndex();
        GridIndex diagRightIndex = currIndex.add(currDirection.diagRightOffset());
        return env.cellIsEmpty(diagRightIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void action(Environment env, Agent agent) {
        agent.moveForwardDiagonallyRight(env);
    }
    
}
