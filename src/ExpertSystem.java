package src;

public class ExpertSystem {
    Grid<Character> environmentGrid;
    Database database;

    public ExpertSystem(int m, int n) {
        environmentGrid = new Grid<Character>(m,n);
        database = new Database(m,n);
    }

    public static void main(String[] args) {
        int m = 20;
        int n = 10;
        int numObstacles = 10;
        Environment env = new Environment(m, n, numObstacles);
        System.out.println(env);
    }
}
