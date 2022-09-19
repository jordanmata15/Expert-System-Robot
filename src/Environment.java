package src;

import java.util.Objects;

import src.Grid.GridIndex;

public class Environment {

    private Grid<Character> environmentGrid;
    private GridIndex agentStartIndex;
    private GridIndex goalIndex;

    /**
     * Simple constructor
     * 
     * @param numCols Number of columns in our grid
     * @param numRows Number of rows in our grid
     */
    public Environment(int numCols, int numRows) {
        this(numCols, numRows, 0);
    }

    /**
     * Simple constructor
     * 
     * @param numCols Number of columns in our grid
     * @param numRows Number of rows in our grid
     * @param numObstacles Number of obstacles to initialize
     */
    public Environment(int numCols, int numRows, int numObstacles) {
        environmentGrid = new Grid<>(numCols, numRows, Constants.EMPTY);
        assignGoalLocation();
        assignAgentLocation();
        addObstacles(numObstacles);
    }

    /**
     * Copy constructor for an environment object
     * 
     * @param other The Environement to create a deep copy of
     */
    public Environment(Environment other) {
        agentStartIndex = new GridIndex(other.agentStartIndex.x, other.agentStartIndex.y);
        goalIndex = new GridIndex(other.goalIndex.x, other.goalIndex.y);
        environmentGrid = new Grid<>(other.environmentGrid.getNumCols(), 
                                     other.environmentGrid.getNumRows());
        for (int x=0; x<environmentGrid.getNumCols(); ++x) {
            for (int y=0; y<environmentGrid.getNumRows(); ++y) {
                GridIndex index = new GridIndex(x, y);
                Character ch = other.environmentGrid.getAtIndex(index);
                environmentGrid.setAtIndex(index, ch);
            }
        }
    }

    /**
     * Sets the environment to an unexplored state. Only the agent
     * direction and index will be known.
     * 
     * @return This environment object to reset state of
     */
    public Environment withUnexploredGrid() {
        goalIndex = null;
        for (int x=0; x<environmentGrid.getNumCols(); ++x) {
            for (int y=0; y<environmentGrid.getNumRows(); ++y) {
                GridIndex index = new GridIndex(x, y);
                if (environmentGrid.getAtIndex(index) == Constants.AGENT) {
                    agentStartIndex = new GridIndex(x, y);
                    environmentGrid.setAtIndex(index, Constants.AGENT);
                } else {
                    environmentGrid.setAtIndex(index, Constants.UNKNOWN);
                }
            }
        }
        return this;
    }

    /**
     * Setter for the agentStartIndex. Ensures we cannot modify it onces set.
     * 
     * @param startIdx The index to set it to
     * @throws IllegalAccessError If we try to set it after setting it already
     */
    public void setAgentStartIndex(GridIndex startIdx) throws IllegalAccessError {
        if (Objects.isNull(agentStartIndex)) {
            agentStartIndex = new GridIndex(startIdx.x, startIdx.y);
        } else {
            throw new IllegalAccessError("Cannot update agent start index once set!");
       }
    }

    /**
     * Getter for the agentStartIndex
     * 
     * @return The agent start index.
     */
    public GridIndex getAgentStartIndex() {
        return agentStartIndex;
    }

    /**
     * Getter for the goal index 
     * 
     * @return The index of the goal
     */
    public GridIndex getGoalIndex() {
        return goalIndex;
    }

    /**
     * Setter for the goalIndex. Ensures we only set it once.
     * 
     * @param goalIdx
     * @return
     */
    public void setGoalIndex(GridIndex goalIdx) {
        // We need our database to be able to set this once we find it.
        // but the goal doesn't move, so we need to make sure it doesn't get updated
        if (Objects.isNull(goalIdx)) {
            goalIndex = goalIdx;
        } else {
            throw new IllegalAccessError("Cannot update agent start index once set!");
       }
    }

    /**
     * Validates if an index pair is a valid index within our Environment grid
     * 
     * @param indexPair The index pair to validate
     * @return True if the index pair is in our grid. False otherwise.
     */
    public boolean isValidIndex(GridIndex indexPair) {
        return indexPair.x >= 0 
                && indexPair.x < environmentGrid.getNumCols()
                && indexPair.y >= 0 
                && indexPair.y < environmentGrid.getNumRows();
    }

    /**
     * Get the object/character held at a specific index in our grid
     * 
     * @param indexPair The index to search at
     * @return The character/object at that index
     */
    public Character getAtIndex(GridIndex indexPair) {
        return environmentGrid.getAtIndex(indexPair);
    }

    /**
     * Set the object/character at a specific index in our grid
     * 
     * @param indexPair The index to set the value at
     */
    public void setAtIndex(GridIndex indexPair, Character c) {
        environmentGrid.setAtIndex(indexPair, c);
    }

    /**
     * Validates if a cell is empty.
     * 
     * @param indexPair
     * @return True if the cell at that index is empty. False otherwise.
     */
    public boolean cellIsEmpty(GridIndex indexPair) {
        if (!isValidIndex(indexPair)) { // invalid indexes are not empty by convention
            return false;
        }
        Character charAtIndex = environmentGrid.getAtIndex(indexPair);
        return charAtIndex.equals(Constants.EMPTY);
    }

    /**
     * Assigns the location of the agent within our environment to a random 
     * empty location.
     */
    public void assignAgentLocation() {
        agentStartIndex = getRandomEmptyCell();
        environmentGrid.setAtIndex(agentStartIndex, Constants.AGENT);
    }
    /**
     * Assigns the location of the goal within our environment to a random 
     * empty location.
     */
    private void assignGoalLocation() {
        goalIndex = getRandomEmptyCell();
        environmentGrid.setAtIndex(goalIndex, Constants.GOAL);
    }

    /**
     * Adds obstacles randomly to our environment at empty locations.
     * 
     * @param numObstacles The number of obstacles to add
     */
    private void addObstacles(int numObstacles) {
        GridIndex indexPair = null;
        for (int n=0; n<numObstacles; ++n) {
            indexPair = getRandomEmptyCell();
            environmentGrid.setAtIndex(indexPair, Constants.OBSTACLE);
        }
    }

    /**
     * Gets a random cell that is currently empty.
     * 
     * @return The index of a cell that is empty in this environment.
     */
    private GridIndex getRandomEmptyCell() {
        GridIndex indexPair = new GridIndex(-1, -1);
        while (!cellIsEmpty(indexPair)) {
            indexPair.x = getRandomInt(0, environmentGrid.getNumCols());
            indexPair.y = getRandomInt(0, environmentGrid.getNumRows());
        }
        return indexPair;
    }

    /**
     * Random integer generator
     * 
     * @param min The minimum integer value to bound the return by
     * @param max The minimum integer value to bound the return by
     * @return A randomly generated in between [min, max]
     */
    private int getRandomInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @Override
    public String toString() {
        return environmentGrid.toString();
    }
}
