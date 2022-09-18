package src;

import src.Grid.GridIndex;

public class Agent {
    private Direction currDirection;
    private GridIndex currIndex;

    /**
     * Simple constructor
     * 
     * @param idx The index on the grid where the agent currently is located.
     */
    public Agent(GridIndex idx) {
        currDirection = Direction.N;
        currIndex = idx;
    }

    /**
     * Getter for the current direction the agent is facing.
     * 
     * @return direction as defined in Direction enum
     */
    public Direction getCurrDirection() {
        return currDirection;
    }

    /**
     * Getter for the current index location of the agent on our grid.
     * 
     * @return Grid index object (x,y) where x and y should be bounded 
     *          by the size of our grid
     */
    public GridIndex getCurrIndex() {
        return new GridIndex(currIndex.x, currIndex.y);
    }

    /**
     * Turns the agent left.
     */
    public void turnLeft() {
        currDirection = currDirection.diagLeft();
    }

    /**
     * Turns the agent right.
     */
    public void turnRight() {
        currDirection = currDirection.diagRight();
    }

    /**
     * Moves the agent forward and updates the environment to reflect the new location.
     * 
     * @param env the environment to update.
     */
    public void moveForward(Environment env) {
        env.setAtIndex(currIndex, Constants.EMPTY);
        currIndex = currIndex.add(currDirection.forwardOffset());
        env.setAtIndex(currIndex, Constants.AGENT);
    }

    /**
     * Moves the agent diagonally left. The agent should also be facing diagonally left of
     * its starting direction. Also updates the environment to reflect the new location.
     * 
     * @param env the environment to update.
     */
    public void moveForwardDiagonallyLeft(Environment env) {
        turnLeft();
        moveForward(env);
    }

    /**
     * Moves the agent diagonally right. The agent should also be facing diagonally right of
     * its starting direction. Also updates the environment to reflect the new location.
     * 
     * @param env the environment to update.
     */
    public void moveForwardDiagonallyRight(Environment env) {
        turnRight();
        moveForward(env);
    }

    public String toString() {
        return "Agent <Direction=" + currDirection + ", Index=" + currIndex + ">";
    }
}
