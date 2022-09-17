package src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

import src.Grid.GridIndex;
import src.rules.IsBlockedAheadRule;
import src.rules.IsFreeAhead;
import src.rules.Rule;

public class Database {
    private Environment actualEnvironment;
    Environment unexploredEnvironment;
    Agent agent;
    private GridIndex goal;

    private int moveCounter;
    private Stack<Action> actionsHistory;
    private List<Rule> ruleList;
    private Iterator<Rule> currRule;
    private boolean noRuleFired;
    
    public Database(Environment env) {
        actionsHistory = new Stack<>();
        ruleList = new ArrayList<>();

        moveCounter = 0;
        actualEnvironment = env;
        unexploredEnvironment = new Environment(env).withAgentStartLocation()
                                                    .withUnexploredGrid();
        agent = new Agent(unexploredEnvironment.getAgentIndex());
        initializeRules();
    }

    public int getMoveCount() {
        return moveCounter;
    }

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

    public boolean canFireRule(Rule ruleToApply) {
        return ruleToApply.isApplicable(unexploredEnvironment, agent);
    }

    public void fireRule(Rule ruleToApply) {
        ruleToApply.invoke(unexploredEnvironment, agent);
        updateVisibility();
        moveCounter++;
        //stateHistory.push();
        noRuleFired = false;
    }

    public void undoAction() {
        actionsHistory.pop();
    }

    public boolean goalIsFound() {
        Character diagLeftItem = getObjectAdjacentToAgent(agent.currDirection.diagLeft());
        Character forwardItem = getObjectAdjacentToAgent(agent.currDirection);
        Character diagRightItem = getObjectAdjacentToAgent(agent.currDirection.diagRight());
        return diagLeftItem == Constants.GOAL 
            || forwardItem == Constants.GOAL
            || diagRightItem == Constants.GOAL;
    }
    
    public boolean rulesNoLongerFiring() {
        return noRuleFired && !currRule.hasNext();
    }

    private void updateVisibility() {
        exploreDirection(agent.currDirection.diagLeft());
        exploreDirection(agent.currDirection);
        exploreDirection(agent.currDirection.diagRight());
    }

    private void exploreDirection(Direction directionToExplore) {

        GridIndex indexToExplore = new GridIndex(agent.currIndex.x, agent.currIndex.y);
        Character objectFound;

        while (unexploredEnvironment.isValidIndex(indexToExplore)) {
            objectFound = unexploredEnvironment.getAtIndex(indexToExplore);

            if (objectFound == Constants.UNKNOWN) {
                objectFound = actualEnvironment.getAtIndex(indexToExplore);
                unexploredEnvironment.setAtIndex(indexToExplore, objectFound);
            } 
            if (objectFound == Constants.OBSTACLE) {
                break;
            }
            indexToExplore.add(directionToExplore.forwardOffset());
        }
    }

    private Character getObjectAdjacentToAgent(Direction directionToExplore) {
        GridIndex indexToExplore = new GridIndex(agent.currIndex.x, agent.currIndex.y);
        indexToExplore.add(directionToExplore.forwardOffset());

        if (!unexploredEnvironment.isValidIndex(indexToExplore)) {
            return Constants.OUT_OF_BOUNDS;
        } else {
            return unexploredEnvironment.getAtIndex(indexToExplore);
        }
    }

    private void initializeRules() {
        ruleList.add(new IsBlockedAheadRule());
        ruleList.add(new IsFreeAhead());
    }

    public String toString() {
        return "Moves elapsed: " + getMoveCount() + "\n\n" + 
                "Actual environment:\n" + actualEnvironment.toString() + "\n" + 
                "Explored environment:\n" + unexploredEnvironment.toString();
    }
}
