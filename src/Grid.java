package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Grid object that can store an arbitrary object.
 */
public class Grid<T> {

    private final List<T> grid;
    private final Integer cols;
    private final Integer rows;
    private final char spacer = ' ';

    public Grid(int numCols, int numRows) {
        this(numCols, numRows, null);
    }

    public Grid(int numCols, int numRows, T defaultValue) {
        cols = numCols;
        rows = numRows;
        grid = new ArrayList<>(Collections.nCopies(numCols*numRows, defaultValue));
    }

    public int getNumRows() {
        return rows;
    }

    public int getNumCols() {
        return cols;
    }

    public void setXY(int x, int y, T item) {
        grid.set(y*cols+x, item);
    }

    public T getXY(int x, int y) {
        return grid.get(y*cols+x);
    }

    @Override
    public String toString(){
        StringBuilder toStringBuilder = new StringBuilder();
        for (int y=0; y<rows; ++y) {
            for (int x=0; x<cols; ++x) {
                toStringBuilder.append(getXY(x,y));
                toStringBuilder.append(spacer);
            }
            toStringBuilder.append('\n');
        }
        return toStringBuilder.toString();
    }

    public static class GridIndex {
        public int x; 
        public int y;
        
        public GridIndex(int indexX, int indexY) {
            x = indexX;
            y = indexY;
        }

        public GridIndex add(GridIndex other) {
            this.x += other.x;
            this.y += other.y;
            return this;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }
}