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
    private final char print_spacer = ' ';

    /**
     * Simple constructor that defaults each record to null.
     * 
     * @param numCols Number of columns
     * @param numRows Number of rows
     */
    public Grid(int numCols, int numRows) {
        this(numCols, numRows, null);
    }

    /**
     * Simple constructor that defaults each record to null.
     * 
     * @param numCols Number of columns
     * @param numRows Number of rows
     * @param defaultValue The value to set each cell to
     */
    public Grid(int numCols, int numRows, T defaultValue) {
        cols = numCols;
        rows = numRows;
        grid = new ArrayList<>(Collections.nCopies(numCols*numRows, defaultValue));
    }

    /**
     * Getter for the number of rows
     * 
     * @return Number of rows
     */
    public int getNumRows() {
        return rows;
    }

    /**
     * Getter for the number of columns
     * 
     * @return Number of columns
     */
    public int getNumCols() {
        return cols;
    }

    /**
     * Sets the value at an index
     * 
     * @param index The index with the row/column value
     * @param item The value to put at this index
     */
    public void setAtIndex(GridIndex index, T item) {
        grid.set(index.y*cols+index.x, item);
    }

    /**
     * Sets the value at an index
     * @param index The index with the row/column value 
     * @return
     */
    public T getAtIndex(GridIndex index) {
        return grid.get(index.y*cols+index.x);
    }

    @Override
    public String toString(){
        StringBuilder toStringBuilder = new StringBuilder();
        for (int y=0; y<rows; ++y) {
            for (int x=0; x<cols; ++x) {
                toStringBuilder.append(getAtIndex(new GridIndex(x, y)));
            }
            toStringBuilder.append('\n');
        }
        return toStringBuilder.toString();
    }

    /**
     * toString with better spacing horizontally
     * 
     * @return the 2d environment as a string
     */
    public String prettyToString(){
        StringBuilder toStringBuilder = new StringBuilder();
        for (int y=0; y<rows; ++y) {
            for (int x=0; x<cols; ++x) {
                toStringBuilder.append(getAtIndex(new GridIndex(x, y)));
                toStringBuilder.append(print_spacer);
            }
            toStringBuilder.append('\n');
        }
        return toStringBuilder.toString();
    }

    /**
     * Class to represent an ordered pair for specifying coordinate indices
     */
    public static class GridIndex {
        public int x;
        public int y;
        
        /**
         * Simple constructor
         * 
         * @param indexX The column index
         * @param indexY The row index
         */
        public GridIndex(int indexX, int indexY) {
            x = indexX;
            y = indexY;
        }

        /**
         * Method to be able to add 2 GridIndex objects. Similar to overloading the
         * addition operator.
         * 
         * @param other The object to add to this GridIndex
         * @return The sum of grid indices
         */
        public GridIndex add(GridIndex other) {
            return new GridIndex(x+other.x, y+other.y);
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }
}