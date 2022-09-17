package src.rules;

import src.Agent;
import src.Direction;
import src.Environment;
import src.Grid.GridIndex;

public class IsBlockedAheadRule extends Rule{

    public IsBlockedAheadRule() {
        antecedentString = "CANNOT MOVE FORWARD";
        actionString = "TURN RIGHT";
    }
    
    public boolean isApplicable(Environment env, Agent agent) {
        Direction currDirection = agent.getCurrDirection();
        GridIndex currIndex = agent.getCurrIndex();
        
        GridIndex forwardIndex = currIndex.add(currDirection.forwardOffset());
        GridIndex diagLeftIndex = currIndex.add(currDirection.diagLeftOffset());
        GridIndex diagRightIndex = currIndex.add(currDirection.diagRightOffset());

        // if forward, diag left, and diag right are blocked
        return !env.cellIsEmpty(forwardIndex)
                && !env.cellIsEmpty(diagLeftIndex)
                && !env.cellIsEmpty(diagRightIndex);
    }

    public void invoke(Environment env, Agent agent) {
        agent.turnRight();
    }
}
