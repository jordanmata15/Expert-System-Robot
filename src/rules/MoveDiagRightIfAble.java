package src.rules;

import src.Agent;
import src.Direction;
import src.Environment;
import src.Grid.GridIndex;

public class MoveDiagRightIfAble extends Rule{

    public MoveDiagRightIfAble() {
        antecedentString = "<FREE SPACE AHEAD DIAGONALLY RIGHT>";
        actionString = "<MOVE DIAG RIGHT>";
    }

    @Override
    public boolean condition(Environment env, Agent agent) {
        Direction currDirection = agent.getCurrDirection();
        GridIndex currIndex = agent.getCurrIndex();
        GridIndex diagRightIndex = currIndex.add(currDirection.diagRightOffset());
        return env.cellIsEmpty(diagRightIndex);
    }

    @Override
    public void action(Environment env, Agent agent) {
        agent.moveForwardDiagonallyRight(env);
    }
    
}
