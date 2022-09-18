package src.rules;

import src.Agent;
import src.Direction;
import src.Environment;
import src.Grid.GridIndex;

public class IsBlockedAheadRule extends Rule{

    public IsBlockedAheadRule() {
        antecedentString = "<CANNOT MOVE DIAGONALLY LEFT> and " + 
                            "<CANNOT MOVE DIRECTLY FORWARD> and " + 
                            "<CANNOT MOVE DIAGONALLY RIGHT>";
        actionString = "<TURN LEFT>";
    }
    
    public boolean condition(Environment env, Agent agent) {
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

    public void action(Environment env, Agent agent) {
        agent.turnLeft();
    }
}
