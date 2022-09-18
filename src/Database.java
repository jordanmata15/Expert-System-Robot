package src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

import src.Grid.GridIndex;
import src.rules.IsBlockedAheadRule;
import src.rules.MoveDiagLeftIfAble;
import src.rules.MoveDiagRightIfAble;
import src.rules.MoveDiagLeftToGoal;
import src.rules.MoveDiagRightToGoal;
import src.rules.MoveForwardIfAble;
import src.rules.MoveForwardToGoal;
import src.rules.Rule;
import src.rules.TurnLeftTowardsGoal;
import src.rules.TurnRightTowardsGoal;


public class Database {
    private Environment actualEnvironment;
    private Environment unexploredEnvironment;
    private Agent agent;

    private int moveCounter;
    private List<Rule> ruleList;
    private Stack<Rule> rulesFired;
    private Iterator<Rule> currRule;
    private boolean noRuleFired;
    
    /**
     * Constructor that will create copy of the environment and set it to be unexplored.
     * @param env The the actual environment that should have the goal, agent, and
     *            obstacles already set.
     */
    public Database(Environment env) {
        ruleList = new ArrayList<>();
        rulesFired = new Stack<>();

        moveCounter = 0;
        actualEnvironment = env;
        unexploredEnvironment = new Environment(actualEnvironment)
                                            .withUnexploredGrid();
        agent = new Agent(unexploredEnvironment.getAgentStartIndex());
        initializeRules();
    }

    /**
     * Getter for the number of rules that have fired so far.
     * 
     * @return integer between 0 and the MAX_NUM_MOVES defined in the Constants file.
     */
    public int getMoveCount() {
        return moveCounter;
    }

    /**
     * Gets the next rule to apply from our database of ordered rules. 
     * We will cycle rulesonce we've exhausted them all.
     * 
     * @return The next rule to apply. If no rules in the database, returns null.
     */
    public Rule getNextRule() {
        if (ruleList.isEmpty()) {
            System.out.println("There are no rules to apply in the database!");
            return null;
        } else if (Objects.isNull(currRule) || !currRule.hasNext()) {
            currRule = ruleList.listIterator();
            noRuleFired = true;
        }
        return currRule.next();
    }

    /**
     * Verifies if a specific rule should fire based on the knowledge base.
     * 
     * @param ruleToApply The rule we wish to verify if it should fire based on 
     *                    the current environment.
     * @return True if it can fire. False otherwise.
     */
    public boolean canFireRule(Rule ruleToApply) {
        return ruleToApply.condition(unexploredEnvironment, agent);
    }

    /**
     * Fires the rule passed in. The rule itself will update the environment.
     * Assumes canFireRule() was called and returned true. 
     * 
     * @param ruleToApply The rule to fire.
     */
    public void fireRule(Rule ruleToApply) {
        ruleToApply.action(unexploredEnvironment, agent);
        updateVisibility();
        moveCounter++;
        rulesFired.push(ruleToApply);
        noRuleFired = false;
    }

    /**
     * Verifies if we have reached the goal. We say the agent has reached the goal
     * if the goal is visible to the agent and can be reached by moving forward or 
     * diagonally.
     * 
     * @return True if we have reached the goal. False otherwise.
     */
    public boolean goalIsReached() {
        Direction agentDirection = agent.getCurrDirection();
        Character diagLeftItem = getObjectAdjacentToAgent(agentDirection.diagLeft());
        Character forwardItem = getObjectAdjacentToAgent(agentDirection);
        Character diagRightItem = getObjectAdjacentToAgent(agentDirection.diagRight());
        return diagLeftItem == Constants.GOAL 
            || forwardItem == Constants.GOAL
            || diagRightItem == Constants.GOAL;
    }

    /**
     * Determines if rules are no longer firing. No longer firing is defined
     * as we have cycled through the entire list of rules and not a single one fired.
     * 
     * @return True if no rule fired in the last cycle. False otherwise.
     */
    public boolean rulesNoLongerFiring() {
        return noRuleFired && !currRule.hasNext();
    }

    /**
     * Updates the known region to reflect the line of sight of the agent.
     * The line of sight is in 3 directions only (forward, diagonally left, and
     * diagonally right). The line of sight is up until we find a non-empty cell
     * or if we reach the end of the grid.
     */
    private void updateVisibility() {
        Direction agentDirection = agent.getCurrDirection();
        exploreDirection(agentDirection.diagLeft());
        exploreDirection(agentDirection);
        exploreDirection(agentDirection.diagRight());
    }

    /**
     * Reveals cells on our grid until we find a non-empty cell or if we reach the 
     * end of the grid. Cells are revealed in the direction from the agent and starting
     * at the agent's index.
     * 
     * @param directionToExplore The direction we wish to reveal on the grid relative
     *                              to the agent.
     */
    private void exploreDirection(Direction directionToExplore) {
        GridIndex indexToExplore = agent.getCurrIndex();
        Character objectFound;

        indexToExplore = indexToExplore.add(directionToExplore.forwardOffset());

        while (unexploredEnvironment.isValidIndex(indexToExplore)) {
            objectFound = unexploredEnvironment.getAtIndex(indexToExplore);

            if (objectFound == Constants.UNKNOWN) {
                objectFound = actualEnvironment.getAtIndex(indexToExplore);
                unexploredEnvironment.setAtIndex(indexToExplore, objectFound);
            }
            if (objectFound == Constants.EMPTY) {
                indexToExplore = indexToExplore.add(directionToExplore.forwardOffset());
                continue;
            }
            if (objectFound == Constants.GOAL) {
                unexploredEnvironment.setGoalIndex(new GridIndex(indexToExplore.x, indexToExplore.y));
            }
            // this index contains some other object/obstacle
            break;
        }
    }

    /**
     * Returns the object directly next to the agent in the specified direction.
     * 
     * @param directionToExplore The direction relative to the agent.
     * @return The character/object found in that direction.
     */
    private Character getObjectAdjacentToAgent(Direction directionToExplore) {
        GridIndex indexToExplore = agent.getCurrIndex();
        indexToExplore = indexToExplore.add(directionToExplore.forwardOffset());

        if (!unexploredEnvironment.isValidIndex(indexToExplore)) {
            return Constants.OUT_OF_BOUNDS;
        } else {
            return unexploredEnvironment.getAtIndex(indexToExplore);
        }
    }

    /**
     * Populates the list of rules we wish to run.
     */
    private void initializeRules() {
        ruleList.add(new IsBlockedAheadRule());

        // ensure that at least a few rules can fire when the environment is unexplored
        ruleList.add(new MoveDiagLeftIfAble());
        ruleList.add(new MoveForwardIfAble());
        ruleList.add(new MoveDiagRightIfAble());
        
        // if goal is is in our line of sight, just move towards it
        // the order of these relative to each other doesn't matter
        // since if one fires, the others should not
        ruleList.add(new MoveDiagLeftToGoal());
        ruleList.add(new MoveForwardToGoal());
        ruleList.add(new MoveDiagRightToGoal());

        // if we know where the goal is, turn the agent to face the goal
        // again, the order relative to each other doesn't matter
        // since one firing implies the other should not
        ruleList.add(new TurnLeftTowardsGoal());
        ruleList.add(new TurnRightTowardsGoal());
    }

    /**
     * Returns a string containing all the rules we have fired so far.
     * 
     * @return String of all rules fired.
     */
    public String allRulesFiredSoFarString() {
        StringBuilder rulesFiredBuilder = new StringBuilder();
        for (Rule r: rulesFired) {
            // prepend since the stack is backwards
            rulesFiredBuilder.insert(0, r.toString() + "\n\n");
        }
        return rulesFiredBuilder.toString();
    }

    /**
     * Returns the board we are currently exploring as a string.
     * 
     * @return the current board string.
     */
    public String getCurrentBoardString() {
        return unexploredEnvironment.toString();
    }

    public String toString() {
        return "Actual environment:\n" + 
                actualEnvironment.toStringWithoutAgentStart() + "\n" + 
                "Explored environment:\n" + 
                unexploredEnvironment.toString() + "\n" + 
                agent.toString();
    }
}
