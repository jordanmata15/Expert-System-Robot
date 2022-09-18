package src;

import src.Grid.GridIndex;

public class Agent {
    private Direction currDirection;
    private GridIndex currIndex;

    public Agent(GridIndex idx) {
        currDirection = Direction.N;
        currIndex = idx;
    }

    public Direction getCurrDirection() {
        return currDirection;
    }

    public GridIndex getCurrIndex() {
        return new GridIndex(currIndex.x, currIndex.y);
    }

    public void turnLeft() {
        currDirection = currDirection.diagLeft();
    }

    public void turnRight() {
        currDirection = currDirection.diagRight();
    }

    public void moveForward(Environment env) {
        env.setAtIndex(currIndex, Constants.EMPTY);
        currIndex = currIndex.add(currDirection.forwardOffset());
        env.setAtIndex(currIndex, Constants.AGENT);
    }

    public void moveForwardDiagonallyLeft(Environment env) {
        turnLeft();
        moveForward(env);
    }

    public void moveForwardDiagonallyRight(Environment env) {
        turnRight();
        moveForward(env);
    }

    public String toString() {
        return "Agent <Direction=" + currDirection + ", Index=" + currIndex + ">";
    }
}
