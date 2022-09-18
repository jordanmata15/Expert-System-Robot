package src;

public class Constants {
    public static char EMPTY = '-';
    public static char AGENT = '@';
    public static char GOAL = 'G';
    public static char UNKNOWN = '?';
    public static char OBSTACLE = '#';
    public static char OUT_OF_BOUNDS = 'I';

    public static int MAX_NUM_MOVES = 2000;
    // 30 MS is minimum value that still makes the display easily visible
    public static int MS_BEFORE_DISPLAYING_NEXT_MOVE = 30;

    private Constants(){}; // hide constructor
}
