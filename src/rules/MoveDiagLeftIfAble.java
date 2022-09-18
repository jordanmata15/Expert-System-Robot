package src.rules;

import src.Agent;
import src.Direction;
import src.Environment;
import src.Grid.GridIndex;

public class MoveDiagLeftIfAble extends Rule{

    /**
     * Simple constructor
     */
    public MoveDiagLeftIfAble() {
        antecedentString = "<FREE SPACE AHEAD DIAGONALLY LEFT>";
        actionString = "<MOVE DIAGONALLY LEFT>";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean condition(Environment env, Agent agent) {
        Direction currDirection = agent.getCurrDirection();
        GridIndex currIndex = agent.getCurrIndex();
        GridIndex diagLeftIndex = currIndex.add(currDirection.diagLeftOffset());
        return env.cellIsEmpty(diagLeftIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void action(Environment env, Agent agent) {
        agent.moveForwardDiagonallyLeft(env);
    }
    
}
