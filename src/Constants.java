package src;

public class Constants {
    public static char EMPTY = '1';
    public static char AGENT = 'S';
    public static char GOAL = 'D';
    public static char UNKNOWN = '2';
    public static char OBSTACLE = '0';

    public static char PRETTY_EMPTY = '-';
    public static char PRETTY_AGENT = '@';
    public static char PRETTY_GOAL = 'G';
    public static char PRETTY_UNKNOWN = '?';
    public static char PRETTY_OBSTACLE = '#';

    public static char OUT_OF_BOUNDS = 'I';

    // 75 ms is a relatively fast speed while still visible
    public static int MS_BEFORE_DISPLAYING_NEXT_MOVE = 75;
    public static int MAX_NUM_MOVES = 2000;

    private Constants(){}; // hide constructor
}
