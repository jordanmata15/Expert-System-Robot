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
    
    public Database(Environment env) {
        ruleList = new ArrayList<>();
        rulesFired = new Stack<>();

        moveCounter = 0;
        actualEnvironment = env;
        unexploredEnvironment = new Environment(actualEnvironment).withAgentStartLocation()
                                                                    .withUnexploredGrid();
        try {
            GridIndex agentStart = unexploredEnvironment.getAgentStartIndex();
            actualEnvironment.setAgentStartIndex(agentStart);
        } catch (IllegalAccessError e) {
            e.printStackTrace();
        }

        agent = new Agent(unexploredEnvironment.getAgentStartIndex());
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
        return ruleToApply.condition(unexploredEnvironment, agent);
    }


    public void fireRule(Rule ruleToApply) {
        ruleToApply.action(unexploredEnvironment, agent);
        updateVisibility();
        moveCounter++;
        rulesFired.push(ruleToApply);
        noRuleFired = false;
    }


    public boolean goalIsReached() {
        Direction agentDirection = agent.getCurrDirection();
        Character diagLeftItem = getObjectAdjacentToAgent(agentDirection.diagLeft());
        Character forwardItem = getObjectAdjacentToAgent(agentDirection);
        Character diagRightItem = getObjectAdjacentToAgent(agentDirection.diagRight());
        return diagLeftItem == Constants.GOAL 
            || forwardItem == Constants.GOAL
            || diagRightItem == Constants.GOAL;
    }

    
    public boolean rulesNoLongerFiring() {
        return noRuleFired && !currRule.hasNext();
    }


    private void updateVisibility() {
        Direction agentDirection = agent.getCurrDirection();
        exploreDirection(agentDirection.diagLeft());
        exploreDirection(agentDirection);
        exploreDirection(agentDirection.diagRight());
    }


    private void exploreDirection(Direction directionToExplore) {

        GridIndex indexToExplore = agent.getCurrIndex();
        Character objectFound;

        indexToExplore.add(directionToExplore.forwardOffset());

        while (unexploredEnvironment.isValidIndex(indexToExplore)) {
            objectFound = unexploredEnvironment.getAtIndex(indexToExplore);

            if (objectFound == Constants.UNKNOWN) {
                objectFound = actualEnvironment.getAtIndex(indexToExplore);
                unexploredEnvironment.setAtIndex(indexToExplore, objectFound);
            }
            if (objectFound == Constants.EMPTY) {
                indexToExplore.add(directionToExplore.forwardOffset());
                continue;
            }
            if (objectFound == Constants.GOAL) {
                unexploredEnvironment.setGoalIndex(new GridIndex(indexToExplore.x, indexToExplore.y));
            }
            // this index contains some other object/obstacle
            break;
        }
    }

    private Character getObjectAdjacentToAgent(Direction directionToExplore) {
        GridIndex indexToExplore = agent.getCurrIndex();
        indexToExplore.add(directionToExplore.forwardOffset());

        if (!unexploredEnvironment.isValidIndex(indexToExplore)) {
            return Constants.OUT_OF_BOUNDS;
        } else {
            return unexploredEnvironment.getAtIndex(indexToExplore);
        }
    }


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


    public String allRulesFiredSoFarString() {
        StringBuilder rulesFiredBuilder = new StringBuilder();
        for (Rule r: rulesFired) {
            // prepend since the stack is backwards
            rulesFiredBuilder.insert(0, r.toString() + "\n\n");
        }
        return rulesFiredBuilder.toString();
    }


    public String toString() {
        return "Actual environment:\n" + actualEnvironment.toString() + "\n" + 
                "Explored environment:\n" + unexploredEnvironment.toString() + "\n" + 
                agent.toString();
    }

    public String currentBoard() {
        return unexploredEnvironment.toString();
    }
}
