package src;

import java.util.Objects;

import src.Grid.GridIndex;

public class Environment {

    private Grid<Character> environmentGrid;
    private GridIndex agentStartIndex;
    private GridIndex goalIndex;

    public Environment(int numCols, int numRows) {
        this(numCols, numRows, 0);
    }

    public Environment(int numCols, int numRows, int numObstacles) {
        environmentGrid = new Grid<>(numCols, numRows, Constants.EMPTY);
        assignGoalLocation();
        assignAgentLocation();
        addObstacles(numObstacles);
    }

    public Environment(Environment other) {
        agentStartIndex = new GridIndex(other.agentStartIndex.x, other.agentStartIndex.y);
        goalIndex = new GridIndex(other.goalIndex.x, other.goalIndex.y);
        environmentGrid = new Grid<>(other.environmentGrid.getNumCols(), 
                                     other.environmentGrid.getNumRows());
        for (int x=0; x<environmentGrid.getNumCols(); ++x) {
            for (int y=0; y<environmentGrid.getNumRows(); ++y) {
                Character ch = other.environmentGrid.getXY(x, y);
                environmentGrid.setXY(x, y, ch);
            }
        }
    }

    public Environment withUnexploredGrid() {
        goalIndex = null;
        for (int x=0; x<environmentGrid.getNumCols(); ++x) {
            for (int y=0; y<environmentGrid.getNumRows(); ++y) {
                if (environmentGrid.getXY(x, y) == Constants.AGENT) {
                    agentStartIndex = new GridIndex(x, y);
                    environmentGrid.setXY(x, y, Constants.AGENT);
                } else {
                    environmentGrid.setXY(x, y, Constants.UNKNOWN);
                }
            }
        }
        return this;
    }

    public void assignAgentLocation() {
        agentStartIndex = getRandomEmptyCell();
        environmentGrid.setXY(agentStartIndex.x, agentStartIndex.y, Constants.AGENT);
    }

    public void setAgentStartIndex(GridIndex startIdx) throws IllegalAccessError {
        if (Objects.isNull(agentStartIndex)) {
            agentStartIndex = new GridIndex(startIdx.x, startIdx.y);
        } else {
            throw new IllegalAccessError("Cannot update agent start index once set!");
       }
    }

    public GridIndex getAgentStartIndex() {
        return agentStartIndex;
    }

    public GridIndex getGoalIndex() {
        return goalIndex;
    }

    public GridIndex setGoalIndex(GridIndex goalIdx) {
        return goalIndex = goalIdx;
    }

    public boolean isValidIndex(GridIndex indexPair) {
        return indexPair.x >= 0 
                && indexPair.x < environmentGrid.getNumCols()
                && indexPair.y >= 0 
                && indexPair.y < environmentGrid.getNumRows();
    }

    public char getAtIndex(GridIndex indexPair) {
        return environmentGrid.getXY(indexPair.x, indexPair.y);
    }

    public void setAtIndex(GridIndex indexPair, Character c) {
        environmentGrid.setXY(indexPair.x, indexPair.y, c);
    }

    public boolean cellIsEmpty(GridIndex indexPair) {
        if (!isValidIndex(indexPair)) { // invalid indexes are not empty by convention
            return false;
        }
        Character charAtIndex = environmentGrid.getXY(indexPair.x,indexPair.y);
        return charAtIndex.equals(Constants.EMPTY);
    }

    private void assignGoalLocation() {
        goalIndex = getRandomEmptyCell();
        environmentGrid.setXY(goalIndex.x, goalIndex.y, Constants.GOAL);
    }

    private void addObstacles(int numObstacles) {
        GridIndex indexPair = null;
        for (int n=0; n<numObstacles; ++n) {
            indexPair = getRandomEmptyCell();
            environmentGrid.setXY(indexPair.x, indexPair.y, Constants.OBSTACLE);
        }
    }

    private GridIndex getRandomEmptyCell() {
        GridIndex indexPair = new GridIndex(-1, -1);
        while (!cellIsEmpty(indexPair)) {
            indexPair.x = getRandomInt(0, environmentGrid.getNumRows());
            indexPair.y = getRandomInt(0, environmentGrid.getNumCols());
        }
        return indexPair;
    }

    private int getRandomInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @Override
    public String toString() {
        //environmentGrid.setXY(agentStartIndex.x, agentStartIndex.y, Constants.AGENT);
        return environmentGrid.toString();
    }
    

    public String toStringWithoutAgentStart() {
        environmentGrid.setXY(agentStartIndex.x, agentStartIndex.y, Constants.EMPTY);
        return this.toString();
    }
}
