package src;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import src.Grid.GridIndex;

public class Environment {

    Grid<Character> environmentGrid;

    private GridIndex agentIndex;
    private GridIndex goalIndex;

    public Environment(int numCols, int numRows) {
        this(numCols, numRows, 0);
    }

    public Environment(int numCols, int numRows, int numObstacles) {
        environmentGrid = new Grid<>(numCols, numRows, Constants.EMPTY);
        assignGoalLocation();
        addObstacles(numObstacles);
    }

    public Environment(Environment other) {
        if (Objects.nonNull(other.agentIndex)) {
            agentIndex = new GridIndex(other.agentIndex.x, other.agentIndex.y);
        }
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
        for (int x=0; x<environmentGrid.getNumCols(); ++x) {
            for (int y=0; y<environmentGrid.getNumRows(); ++y) {
                Character currChar = environmentGrid.getXY(x, y);
                if (currChar != Constants.AGENT 
                        && currChar != Constants.GOAL) {
                    environmentGrid.setXY(x, y, Constants.UNKNOWN);
                }
            }
        }
        return this;
    }

    public Environment withAgentStartLocation() {
        agentIndex = getRandomEmptyCell();
        environmentGrid.setXY(agentIndex.x, agentIndex.y, Constants.AGENT);
        return this;
    }

    public GridIndex getAgentIndex() {
        return agentIndex;
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
        return environmentGrid.toString();
    }
    
}
