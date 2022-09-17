package src;

import src.Grid.GridIndex;
import src.rules.Rule;

public class ExpertSystem {
    
    private Database database;

    public ExpertSystem(int m, int n, int numObstacles) {
        Environment env = new Environment(m, n, numObstacles);
        database = new Database(env);
    }

    public void search() {
        //Agent agent = database.getAgent();
        //Environment env = database.unexploredEnvironment;
        
        while (continueSearching()) {
            Rule ruleToApply = database.getNextRule();
            if (database.canFireRule(ruleToApply)) {
                database.fireRule(ruleToApply);
            }
        }

        printSummary();
    }

    private void printSummary() {
        StringBuilder summaryStrBuilder = new StringBuilder();

        if (database.goalIsFound()) {
            summaryStrBuilder.append("\nGoal found!\n");
        } else {
            summaryStrBuilder.append("\nGoal not found!\n");
        }
        summaryStrBuilder.append(database.toString());
        System.out.println(summaryStrBuilder.toString());
    }

    private boolean continueSearching() {
        return !haveGivenUp()
            && !database.goalIsFound()  
            && !database.rulesNoLongerFiring();
    }

    private boolean haveGivenUp() {
        if (database.getMoveCount() > 2000) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        int cols = 20;
        int rows = 10;
        int numObstacles = 10;
        /* */
        if (args.length > 1) {
            System.out.println("Got here!");
        }

        ExpertSystem es = new ExpertSystem(cols, rows, numObstacles);
        es.search();
    }
}
