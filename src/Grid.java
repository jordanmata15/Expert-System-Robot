package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Grid object that can store an arbitrary object.
 */
public class Grid<T> {

    protected final List<T> grid;
    private final Integer rows;
    private final Integer cols;
    private final char spacer = ' ';

    /**
     * Default constructor for the grid object. Every object is null initially.
     * @param m The number of rows in the grid
     * @param n The number of columns in the grid
     */
    public Grid(int m, int n) {
        rows = m;
        cols = n;
        grid = new ArrayList<>(Collections.nCopies(m*n, null));
    }

    /**
     * Default constructor for the grid object. Every object is null initially.
     * @param m The number of rows in the grid
     * @param n The number of columns in the grid
     * @param defaultValue The value each cell will take on by default
     */
    public Grid(int m, int n, T defaultValue) {
        rows = m;
        cols = n;
        grid = new ArrayList<>(Collections.nCopies(m*n, defaultValue));
    }

    public int getNumRows() {
        return rows;
    }

    public int getNumCols() {
        return cols;
    }

    /**
     * Sets the value of the grid in row i and column j to the value of item.
     * @param i The row to modify.
     * @param j The column to modify.
     * @param item The item to place in this row/column of the grid.
     */
    public void setIJ(int i, int j, T item) {
        grid.set(i*cols+j, item);
    }

    /**
     * Gets the value of the grid at row i and column j.
     * @param i The row to modify.
     * @param j The column to modify.
     * @return The item at the specified index.
     */
    public T getIJ(int i, int j) {
        return grid.get(i*cols+j);
    }

    /**
     * Used to get a stringified version of the grid. Mostly for debugging purposes.
     * @return The grid in a format that can be printed easily.
     */
    @Override
    public String toString(){
        StringBuilder toStringBuilder = new StringBuilder();
        for (int i=0; i<rows; ++i) {
            for (int j=0; j<cols; ++j) {
                toStringBuilder.append(getIJ(i,j));
                toStringBuilder.append(spacer);
            }
            toStringBuilder.append('\n');
        }
        return toStringBuilder.toString();
    }
}