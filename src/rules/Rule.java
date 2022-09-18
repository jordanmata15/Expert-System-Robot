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
     * the state of the agent
     * 
     * @param env The state of the current environment
     * @param agent The state of the agent in the environment
     * @return True if this rule is applicable. False otherwise
     */
    abstract public boolean condition(Environment env, Agent agent);

    /**
     * Applies this rule. Updates the state of the environment and the state of the agent
     * 
     * @param env The state of the current environment
     * @param agent The state of the agent in the environment
     */
    abstract public void action(Environment env, Agent agent);

    /**
     * Determine where the goal is relative to the agent.
     * The direction does NOT account for the way the agent is facing.
     * Eg. If the agent is at (3,3) facing South and the goal is at (3,0)
     * then the goal is North of the agent
     * 
     * @param agent
     * @param goalIndex
     * @return The direction of the goal in relation to the agent
     */
    protected Direction goalDirectionFromAgent(Agent agent, GridIndex goalIndex) {
        GridIndex agentIndex = agent.getCurrIndex();
        GridIndex directionRelativeToAgent = new GridIndex(0, 0);
        
        directionRelativeToAgent.x = goalIndex.x - agentIndex.x;
        directionRelativeToAgent.y = goalIndex.y - agentIndex.y;

        return Direction.directionOfOffset(directionRelativeToAgent);
    }

    /**
     * Validates if turning by a certain number of degrees will orient the agent to the goal.
     * 
     * @param agent The agent with currIndex and currDirection
     * @param goalIndex The index of the goal
     * @return The direction of the goal in relation to the agent
     */
    protected boolean turnOrientsTowardsGoal(Agent agent, 
                                                GridIndex goalLocation, 
                                                int degreesToTurn) {
        // the direction of the goal if we don't take the current direction of
        // agent into account. 
        // Eg. the goal may be North of the agent (absolute direction), 
        // but if the agent is facing South, the relative direction should be South. 
        Direction absoluteDirection = goalDirectionFromAgent(agent, goalLocation);
        int degreesFromGoalBeforeTurn = Math.abs(agent.getCurrDirection().degrees - absoluteDirection.degrees);
        
        int directionDegreesAfterTurn = agent.getCurrDirection().degrees + degreesToTurn;
        int degreesFromGoalAfterTurn = Math.abs(directionDegreesAfterTurn - absoluteDirection.degrees);

        if (degreesFromGoalAfterTurn < degreesFromGoalBeforeTurn) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Determine if the desired character is directly in one of the 3 directions relative
     * to a specific index. 
     * Eg. if the start index  is (8,8) and the charToFind is at (1,1), we 
     * return true if the offset is (-1,-1)
     * 
     * @param startIndex The index where we wish to find the character from.
     * @param env The environment grid we are exploring.
     * @param offsetToMoveBy The offset with North being true North. 
     * @param charToFind Character we wish to locate
     * @return True if the desired object is along this path relative to the start. False otherwise.
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
     * Determines if a specifies object/character is currently visible by the agent.
     * 
     * @param agent The agent with currIndex and currDirection.
     * @param env The current environment.
     * @param charToFind The character/object to find.
     * @return True if the chracter/object is directly forward, directly diagonally left,
     *          or direclty diagonally right from the agent given its current direction. 
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

    @Override
    public String toString() {
        return "If " + antecedentString + "\n\t" + 
                    "then " + actionString;
    }
}
