package src;

import java.util.AbstractMap.SimpleEntry;

public class Environment {

    Grid<Character> environmentGrid;

    // current location of our agent/robot
    private int agentI = -1;
    private int agentJ = -1;

    public Environment(int m, int n) {
        this(m, n, 0);
    }

    public Environment(int m, int n, int numObstacles) {
        environmentGrid = new Grid<>(m, n, Constants.EMPTY);
        assignStartingAgentLocation();
        assignGoalLocation();
        addObstacles(numObstacles);
    }

    private void assignStartingAgentLocation() {
        SimpleEntry<Integer,Integer> indexPair = getRandomEmptyCell();
        agentI = indexPair.getKey();
        agentJ = indexPair.getValue();
        environmentGrid.setIJ(agentI, agentJ, Constants.AGENT);
    }

    private void assignGoalLocation() {
        SimpleEntry<Integer,Integer> indexPair = getRandomEmptyCell();
        int goalI = indexPair.getKey();
        int goalJ = indexPair.getValue();
        environmentGrid.setIJ(goalI, goalJ, Constants.GOAL);
    }

    private void addObstacles(int numObstacles) {
        SimpleEntry<Integer,Integer> indexPair = null;
        int i, j;
        for (int n=0; n<numObstacles; ++n) {
            indexPair = getRandomEmptyCell();
            i = indexPair.getKey();
            j = indexPair.getValue();
            environmentGrid.setIJ(i, j, Constants.OBSTACLE);
        }
    }

    private SimpleEntry<Integer,Integer> getRandomEmptyCell() {
        int i = -1,
            j = -1;
        while (!cellIsEmpty(i,j)) {
            i = getRandomInt(0, environmentGrid.getNumRows());
            j = getRandomInt(0, environmentGrid.getNumCols());
        }
        // avoid having to download javafx for the Pair object
        // use a map to return the pair
        return new SimpleEntry<Integer,Integer>(i,j);
    }

    private boolean cellIsEmpty(int i, int j) {
        if (i < 0 || i >= environmentGrid.getNumRows()
            || j < 0 || j >= environmentGrid.getNumCols()) {
            return false; // indexes outside our grid are not empty by convention
        } else {
            return environmentGrid.getIJ(i,j).equals(Constants.EMPTY);
        }
    }

    private int getRandomInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @Override
    public String toString() {
        return environmentGrid.toString();
    }
    
}
