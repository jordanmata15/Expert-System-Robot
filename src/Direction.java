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

    private final Integer degrees;
    // offset to add if we wish to move forward in this direction
    private final GridIndex forwardOffset;

    Direction(Integer degrees, GridIndex fo) {
        this.degrees = degrees;
        this.forwardOffset = fo;
    }

    public Direction directionAtDegrees(int degrees) {
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

    public GridIndex forwardOffset() {
        return forwardOffset;
    }

    public GridIndex diagLeftOffset() {
        return directionAtDegrees(this.degrees - 45).forwardOffset();
    }

    public GridIndex diagRightOffset() {
        return directionAtDegrees(this.degrees + 45).forwardOffset();
    }

    public Direction diagLeft() {
        return directionAtDegrees(this.degrees - 45);
    }

    public Direction diagRight() {
        return directionAtDegrees(this.degrees + 45);
    }
}
