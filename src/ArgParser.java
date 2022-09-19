package src;

public class ArgParser {

    private String inputFilePath;
    private String outputFilePath;
    private boolean displayOutputFlag;
    private boolean displayRulesFiredFlag;
    private boolean prettyPrintingFlag;

    private int rows;
    private int cols;
    private double percentObstacles;

    private static String USAGE = "ExpertSystem:\n" +
                                        "\t-d\t\t Display grid after each move\n" +
                                        "\t-p\t\t Display grids with pretty printing (better spacing and more intuitive object labels)\n" +
                                        "\t-r\t\t Display all rules fired after finished\n" +
                                        "\t-g <percent>\t Percent of the board that should be obstacles [0-100] (eg. pass in 10 for 10%)\n" +
                                        "\t-f <filename>\t Full path to the input file we wish to initialize our board to\n" +
                                        "\t-o <filename>\t Full path to the output file we wish to write the output to\n";

    /**
     * Simple constructor
     */
    public ArgParser() {
        inputFilePath = null;
        outputFilePath = "../output/output.txt";
        displayOutputFlag = false;
        displayRulesFiredFlag = false;
        prettyPrintingFlag = false;
        rows = 35;
        cols = 45;
        percentObstacles = 0.10;
    }
    
    /**
     * Parses the command line args passed in to the executable.
     * 
     * @param args string of command line args
     */
    public void parseArgs(String[] args) {
        for (int i=0; i<args.length; ++i) {
            if (args[i].equals("-d")) {
                displayOutputFlag = true;
            } else if (args[i].equals("-p")) {
                prettyPrintingFlag = true;
            } else if (args[i].equals("-r")) {
                displayRulesFiredFlag = true;
            } else if (args[i].equals("-f")) {
                ++i;
                inputFilePath = args[i];
            } else if (args[i].equals("-o")) {
                ++i;
                outputFilePath = args[i];
            } else if (args[i].equals("-g")) {
                ++i;
                double percentBeforeDiv = (double)Integer.valueOf(args[i]);
                percentObstacles = percentBeforeDiv/100;
            } else {
                System.out.println(USAGE);
                System.exit(1);
            }
        }
    }

    /**
     * Getter for outputFilePath
     * 
     * @return outputFilePath
     */
    public String getOutputFilePath() {
        return outputFilePath;
    }

    /**
     * Getter for inputFilePath
     * 
     * @return inputFilePath
     */
    public String getInputFilePath() {
        return inputFilePath;
    }

    /**
     * Getter for displayOutputFlag
     * 
     * @return displayOutputFlag
     */
    public boolean getDisplayOutputFlag() {
        return displayOutputFlag;
    }

    /**
     * Getter for prettyPrintingFlag
     * 
     * @return prettyPrintingFlag
     */
    public boolean getPrettyPrintingFlag() {
        return prettyPrintingFlag;
    }

    /**
     * Getter for displayRulesFiredFlag
     * 
     * @return displayRulesFiredFlag
     */
    public boolean getDisplayRulesFiredFlag() {
        return displayRulesFiredFlag;
    }

    /**
     * Getter for percent of obstacles
     * 
     * @return Percent of obstacles
     */
    public double getPercentObstacles() {
        return percentObstacles;
    }

    /**
     * Getter for number of rows
     * 
     * @return Number of rows
     */
    public int getNumRows() {
        return rows;
    }

    /**
     * Getter for number of columns
     * 
     * @return Number of columns
     */
    public int getNumCols() {
        return cols;
    }
}
