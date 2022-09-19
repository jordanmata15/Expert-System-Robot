package src;

public class Constants {
    public static char EMPTY = '-';
    public static char AGENT = '@';
    public static char GOAL = 'G';
    public static char UNKNOWN = '?';
    public static char OBSTACLE = '#';
    public static char OUT_OF_BOUNDS = 'I';

    // 75 ms is a relatively fast speed while still visible
    public static int MS_BEFORE_DISPLAYING_NEXT_MOVE = 75;
    public static int MAX_NUM_MOVES = 2000;

    private Constants(){}; // hide constructor
}
