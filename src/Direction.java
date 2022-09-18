package src;

import src.Grid.GridIndex;

public enum Direction {
    N (0,   new GridIndex(0, -1)),
    NE(45,  new GridIndex(1, -1)),
    E (90,  new GridIndex(1, 0)),
    SE(135, new GridIndex(1, 1)),
    S (180, new GridIndex(0, 1)),
    SW(225, new GridIndex(-1, 1)),
    W (270, new GridIndex(-1, 0)),
    NW(315, new GridIndex(-1, -1));

    public final Integer degrees;
    private final GridIndex forwardOffset;

    private static int DEGREES_PER_TURN = 45;
    public static int DEGREES_LEFT_TURN = -DEGREES_PER_TURN;
    public static int DEGREES_RIGHT_TURN = DEGREES_PER_TURN;

    /**
     * Simple constructor
     * 
     * @param degrees Number of degrees relative from N (increasing clockwise).
     * @param fo The x and y offset needed to move in that direction. 
     *              Eg. North has offset (0, -1) since we don't move horizontally (0)
     *              and we need to move up once (-1).
     */
    Direction(Integer degrees, GridIndex fo) {
        this.degrees = degrees;
        this.forwardOffset = fo;
    }

    /**
     * Used to get the offset needed to move in this direction.
     * Eg. East.diagLeftOffset() would return (1, 0)
     * 
     * @return the offset (x,y) for x and y both either -1, 0, or 1. 
     */
    public GridIndex forwardOffset() {
        return forwardOffset;
    }

    /**
     * Used to get the offset needed to move diagonally to the left in this direction.
     * Eg. East.diagLeftOffset() would return (1, -1)
     * 
     * @return the offset (x,y) for x and y both either -1, 0, or 1. 
     */
    public GridIndex diagLeftOffset() {
        return directionAtDegrees(this.degrees - 45).forwardOffset();
    }

    /**
     * Used to get the offset needed to move diagonally to the right in this direction.
     * Eg. East.diagRightOffset() would return (1, 1)
     * 
     * @return the offset (x,y) for x and y both either -1, 0, or 1. 
     */
    public GridIndex diagRightOffset() {
        return directionAtDegrees(this.degrees + 45).forwardOffset();
    }

    /**
     * Used to get the direction that is diagonally left to this direction.
     * Eg. East.diagLeft() would return NorthEast
     * 
     * @return the direction enum to the diagonal left of this direction.
     */
    public Direction diagLeft() {
        return directionAtDegrees(this.degrees - 45);
    }

    /**
     * Used to get the direction that is diagonally right to this direction.
     * Eg. East.diagRight() would return SouthEast
     * 
     * @return the direction enum to the diagonal right of this direction.
     */
    public Direction diagRight() {
        return directionAtDegrees(this.degrees + 45);
    }

    /**
     * Used to calculate the direction associated with a degree value.
     * The degree value can be negative or greater than 359, but it must be 
     * a multiple of 45. If not a multiple of 45, then North is returned by default.
     * 
     * @param degrees The number of degrees relative to North (0)
     * @return The direction at that degrees.
     */
    public static Direction directionAtDegrees(int degrees) {
        if (degrees < 0) {
            degrees %= 360;
            degrees += 360;
        }
        if (degrees > 359) {
            degrees %= 360;
            degrees -= 360;
        }
        for (Direction d: Direction.values()) {
            if (d.degrees == degrees) {
                return d;
            }
        }
        return Direction.N; // North is the default direction
    }

    /**
     * Used to calculate the direction associated with a particular offset direction.
     * The offset direction must be integer pair (x, y). Both x and y will be normalized to
     * be either -1, 0, or 1.
     * Eg. (-32, 43) will be (-1, 1) and will return SouthWest
     * 
     * @param offset The offset to get the direction for.
     * @return The direction associated with this offset.
     */
    public static Direction directionOfOffset(GridIndex offset) {
        // normalize the offset to -1, 0, or 1
        if (offset.x != 0){
            offset.x /= Math.abs(offset.x);
        }
        if (offset.y != 0){
            offset.y /= Math.abs(offset.y);
        }
        for (Direction d: Direction.values()) {
            if (d.forwardOffset == offset) {
                return d;
            }
        }
        return Direction.N; // North is the default direction
    }
}
