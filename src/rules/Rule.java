package src.rules;

import src.Agent;
import src.Environment;

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
    abstract public boolean isApplicable(Environment env, Agent agent);

    /**
     * Applies this rule. Updates the state of the environment and the state of the agent.
     * 
     * @param env The state of the current environment.
     * @param agent The state of the agent in the environment.
     */
    abstract public void invoke(Environment env, Agent agent);

    private String getAntecedentString() {
        return antecedentString;
    }

    private String getActionString() {
        return actionString;
    }

    @Override
    public String toString() {
        return "If " + getAntecedentString() + " then " + getActionString();
    }
}
