package src;

import java.lang.Thread;

import src.rules.Rule;

public class ExpertSystem {
    
    private Database database;
    

    public ExpertSystem(int m, int n, int numObstacles) {
        Environment env = new Environment(m, n, numObstacles);
        database = new Database(env);
    }

    public void search() {

        while (continueSearching()) {
            Rule ruleToApply = database.getNextRule();
            if (database.canFireRule(ruleToApply)) {
                database.fireRule(ruleToApply);
            }
            display();
        }

        printSummary();
        
        // Enable this line to see a list of all 
        // System.out.println(database.allRulesFiredSoFarString());
    }


    private void display() {
        System.out.println("Move #" + database.getMoveCount() + "\n" +
                            database.currentBoard());
        try {
            Thread.sleep(Constants.MS_BEFORE_DISPLAYING_NEXT_MOVE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void printSummary() {
        StringBuilder summaryStrBuilder = new StringBuilder();

        summaryStrBuilder.append("\nSUMMARY:");
        if (database.goalIsReached()) {
            summaryStrBuilder.append("\nGoal status:\t\tFound!\n");
        } else {
            summaryStrBuilder.append("\nGoal status:\t\tNot found!\n");
            summaryStrBuilder.append("Did agent gave up?\t" + haveGivenUp() + "\n");
            summaryStrBuilder.append("Rules stopped firing?\t" + database.rulesNoLongerFiring() + "\n");
        }
        summaryStrBuilder.append("# moves used:\t\t" + 
                                    database.getMoveCount() + "/" + 
                                    Constants.MAX_NUM_MOVES + "\n\n");
        summaryStrBuilder.append(database.toString());
        System.out.println(summaryStrBuilder.toString());
    }

    private boolean continueSearching() {
        return !haveGivenUp()
            && !database.goalIsReached();
    }

    private boolean haveGivenUp() {
        if (database.getMoveCount() >= Constants.MAX_NUM_MOVES || database.rulesNoLongerFiring()) {
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
