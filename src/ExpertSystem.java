package src;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.Thread;
import java.util.Objects;

import src.rules.Rule;

public class ExpertSystem {
    
    private Database database;
    private boolean displayMoveFlag;
    private boolean displayRulesFlag;
    private boolean prettyPrintingFlag;
    private String outputFilePath;

    /**
     * Simple constructor that uses argparser to pass in the arguments
     * 
     * @param argParser container for all of our arguments
     */
    public ExpertSystem(ArgParser argParser) {
        if (Objects.nonNull(argParser.getInputFilePath())) {
            // initialize grid from file
            Environment env = new Environment(argParser.getInputFilePath());
            database = new Database(env);
            displayMoveFlag = argParser.getDisplayOutputFlag();
        } else {
            // initialize grid randomly with predefined size
            int cols = argParser.getNumCols();
            int rows = argParser.getNumRows();
            int numCells = cols*rows;
            int numObstacles = (int) (numCells * argParser.getPercentObstacles());
            Environment env = new Environment(cols, rows, numObstacles);
            database = new Database(env);
        }
        
        outputFilePath = argParser.getOutputFilePath();
        displayMoveFlag = argParser.getDisplayOutputFlag();
        prettyPrintingFlag = argParser.getPrettyPrintingFlag();
        displayRulesFlag = argParser.getDisplayRulesFiredFlag();
    }

    /**
     * Runs the search on the environment and prints stats at the end.
     */
    public void search() {
        if (displayMoveFlag){
            display();
        }
        while (!haveGivenUp() && !database.goalIsReached()) {
            Rule ruleToApply = database.getNextRule();
            if (database.canFireRule(ruleToApply)) {
                database.fireRule(ruleToApply);
                if (displayMoveFlag){
                    display();
                }
            }
        }
        
        printSummary();

        if (displayRulesFlag) {
            System.out.println(database.allRulesFiredSoFarString());
        }

        writeOutputToFile();
    }

    /**
     * Displays the current state of the grid we are searching.
     * Sleeps after displaying to allow the move to be seen.
     */
    private void display() {
        String databaseStr;
        if (prettyPrintingFlag) {
            databaseStr = database.getCurrentBoardPrettyString();
        } else {
            databaseStr = database.getCurrentBoardString();
        }
        System.out.println("Move #" + database.getMoveCount() + "\n" +
                                    databaseStr);
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

        if (prettyPrintingFlag) {
            summaryStrBuilder.append(database.prettyToString());
        } else {
            summaryStrBuilder.append(database.toString());
        }

        summaryStrBuilder.append("\nSUMMARY:");
        if (database.goalIsReached()) {
            summaryStrBuilder.append("\nGoal status:\t\tFound!\n");
        } else {
            summaryStrBuilder.append("\nGoal status:\t\tNot found!\n");
            summaryStrBuilder.append("Rules stopped firing?\t" + database.rulesNoLongerFiring() + "\n");
        }
        summaryStrBuilder.append("# moves used:\t\t" + 
                                    database.getMoveCount() + "/" + 
                                    Constants.MAX_NUM_MOVES + "\n\n");

        System.out.println(summaryStrBuilder.toString());
    }

    /**
     * Writes the final grid after we are done searching to a specific file.
     */
    private void writeOutputToFile() {
        try {
            FileWriter myWriter = new FileWriter(outputFilePath);
            myWriter.write(database.getCurrentBoardString());
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
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
        ArgParser argParser = new ArgParser();
        ExpertSystem es;

        argParser.parseArgs(args);
        es = new ExpertSystem(argParser);

        es.search();        
    }
}
