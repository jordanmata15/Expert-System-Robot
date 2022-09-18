package src.rules;

import src.Agent;
import src.Constants;
import src.Direction;
import src.Environment;
import src.Grid.GridIndex;

public abstract class Rule {

    protected String antecedentString;
    protected String actionString;

    /**
     * Validates whether this rule can be applied to the current environment given
     * the state of the agent.
     * 
     * @param env The state of the current environment.
     * @param agent The state of the agent in the environment.
     * @return True if this rule is applicable. False otherwise.
     */
    abstract public boolean condition(Environment env, Agent agent);

    /**
     * Applies this rule. Updates the state of the environment and the state of the agent.
     * 
     * @param env The state of the current environment.
     * @param agent The state of the agent in the environment.
     */
    abstract public void action(Environment env, Agent agent);


    protected Direction goalDirectionFromAgent(Agent agent, GridIndex goalIndex) {
        GridIndex agentIndex = agent.getCurrIndex();
        GridIndex directionRelativeToAgent = new GridIndex(0, 0);
        
        directionRelativeToAgent.x = goalIndex.x - agentIndex.x;
        directionRelativeToAgent.y = goalIndex.y - agentIndex.y;

        return Direction.directionOfOffset(directionRelativeToAgent);
    }


    protected boolean turnOrientsTowardsGoal(Agent agent, 
                                                GridIndex goalLocation, 
                                                int degreesToTurn) {
        // the direction of the goal if we don't take the current direction of
        // agent into account. Eg. the goal may be North of the agent (absolute direction), 
        // but if the agent is facing South, the relative direction should be South. 
        Direction absoluteDirection = goalDirectionFromAgent(agent, goalLocation);
        int degreesFromGoal = Math.abs(agent.getCurrDirection().degrees - absoluteDirection.degrees);
        int degreesAfterRotatingLeft = agent.getCurrDirection().degrees + degreesToTurn;
        int degreesFromGoalAfterTurning = Math.abs(degreesAfterRotatingLeft - absoluteDirection.degrees);

        if (degreesFromGoalAfterTurning < degreesFromGoal) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Used to determine if the desired character is directly in one of the 3
     * directions relative to a specific index. 
     * Eg. if the start index  is (8,8) and the charToFind is at (1,1), we 
     * return true if the offset is (-1,-1)
     * 
     * @param startIndex The index where we wish to find the character from.
     * @param env The environment grid we are exploring.
     * @param offsetToMoveBy The offset with North being true North. 
     * @param charToFind Character we wish to locate
     * @return True if the desired object is along this path relative to the start.
     */
    protected boolean existsInDirectionFromIndex(GridIndex startIndex, 
                                                    Environment env,
                                                    GridIndex offsetToMoveBy,
                                                    Character charToFind) {
        GridIndex indexToExplore = new GridIndex(startIndex.x, startIndex.y);
        indexToExplore = indexToExplore.add(offsetToMoveBy);

        while (env.isValidIndex(indexToExplore)) {
            Character charFound = env.getAtIndex(indexToExplore);
            if (charFound == charToFind) {
                return true;
            }
            if (charFound != Constants.EMPTY){
                break;
            }
            indexToExplore = indexToExplore.add(offsetToMoveBy);
        }
        return false;
    }

    /**
     * 
     * @param agent
     * @param env
     * @param charToFind
     * @return
     */
    protected boolean isVisibleByAgent(Agent agent, 
                                        Environment env,
                                        Character charToFind) {
        GridIndex agentIndex = agent.getCurrIndex();
        Direction agentDirection = agent.getCurrDirection();
        return existsInDirectionFromIndex(agentIndex, env, 
                        agentDirection.diagLeftOffset(), charToFind)
                || existsInDirectionFromIndex(agentIndex, env, 
                        agentDirection.forwardOffset(), charToFind)
                || existsInDirectionFromIndex(agentIndex, env, 
                        agentDirection.diagRightOffset(), charToFind);

    }


    private String getAntecedentString() {
        return antecedentString;
    }


    private String getActionString() {
        return actionString;
    }


    @Override
    public String toString() {
        return "If " + getAntecedentString() + "\n\tthen " + getActionString();
    }
}
