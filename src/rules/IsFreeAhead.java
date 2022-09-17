package src.rules;

import src.Agent;
import src.Direction;
import src.Environment;
import src.Grid.GridIndex;

public class IsFreeAhead extends Rule{

    public IsFreeAhead() {
        antecedentString = "FREE SPACE AHEAD (3 DIRECTIONS)";
        actionString = "MOVE FORWARD";
    }

    @Override
    public boolean isApplicable(Environment env, Agent agent) {
        Direction currDirection = agent.getCurrDirection();
        GridIndex currIndex = agent.getCurrIndex();
        
        GridIndex forwardIndex = currIndex.add(currDirection.forwardOffset());
        GridIndex diagLeftIndex = currIndex.add(currDirection.diagLeftOffset());
        GridIndex diagRightIndex = currIndex.add(currDirection.diagRightOffset());

        // if forward, diag left, and diag right are blocked
        return env.cellIsEmpty(forwardIndex)
                || env.cellIsEmpty(diagLeftIndex)
                || env.cellIsEmpty(diagRightIndex);
    }

    @Override
    public void invoke(Environment env, Agent agent) {
        Direction currDirection = agent.getCurrDirection();
        GridIndex currIndex = agent.getCurrIndex();
        
        GridIndex forwardIndex = currIndex.add(currDirection.forwardOffset());
        GridIndex diagLeftIndex = currIndex.add(currDirection.diagLeftOffset());
        
        if (env.cellIsEmpty(forwardIndex)) {
            agent.moveForward(env);
        } else if (env.cellIsEmpty(diagLeftIndex)) {
            agent.moveForwardDiagonallyLeft(env);
        } else {
            agent.moveForwardDiagonallyRight(env);
        }
    }
    
}
