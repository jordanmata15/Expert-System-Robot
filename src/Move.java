package src;

public class Move {
    private final Direction direction;
    private final Integer i;
    private final Integer j;

    public Move (Direction dir, Integer i, Integer j) {
        this.direction = dir;
        this.i = i;
        this.j = j;
    }

    public Direction getDirection() {
        return direction;
    }

    public Integer getI() {
        return i; 
    }

    public Integer getJ() {
        return j; 
    }
}
