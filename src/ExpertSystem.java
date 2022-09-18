package src;

import java.lang.Thread;

import src.rules.Rule;

public class ExpertSystem {
    
    private Database database;
    
    /**
     * Simple constructor
     * 
     * @param m The number of columns in our environment grid
     * @param n The number of rows in our environment grid
     * @param numObstacles
     */
    public ExpertSystem(int m, int n, int numObstacles) {
        Environment env = new Environment(m, n, numObstacles);
        database = new Database(env);
    }

    /**
     * Runs the search on the environment and prints stats at the end.
     */
    public void search() {

        while (!haveGivenUp() && !database.goalIsReached()) {
            Rule ruleToApply = database.getNextRule();
            if (database.canFireRule(ruleToApply)) {
                database.fireRule(ruleToApply);
            }
            display();
        }

        printSummary();
        
        // Enable this line to see a list of all rules fired 
        // System.out.println(database.allRulesFiredSoFarString());
    }

    /**
     * Displays the current state of the grid we are searching.
     * Sleeps after displaying to allow the move to be seen.
     */
    private void display() {
        System.out.println("Move #" + database.getMoveCount() + "\n" +
                            database.getCurrentBoardString());
        try {
            Thread.sleep(Constants.MS_BEFORE_DISPLAYING_NEXT_MOVE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates and prints the summary message of our search.
     */
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

    /**
     * Determines when we should stop our search.
     * 
     * @return True if we should stop searching. False otherwise.
     */
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
        int numCells = cols*rows;
        double pctObjects = 0.20;
        int numObstacles = (int) ((int)numCells*pctObjects);

        if (args.length > 1) {
            System.out.println("Got here!");
        }

        ExpertSystem es = new ExpertSystem(cols, rows, numObstacles);
        es.search();
    }
}
