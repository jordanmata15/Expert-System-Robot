package src;

import java.util.Stack;

public class Database {
    Grid<Character> g;
    Integer moveCounter;
    Move currentMove;
    Stack<Move> moves;

    public Database(int m, int n) {
        g = new Grid<>(m,n);
        moves = new Stack<>();
        moveCounter = 0;
    }

    public void makeMove(Move newMove) {
        // move the robot
        Move lastMove = moves.peek();
        currentMove = newMove;
        g.setIJ(lastMove.getI(), lastMove.getJ(), Constants.EMPTY);
        g.setIJ(currentMove.getI(), currentMove.getJ(), Constants.AGENT);
        moveCounter++;
        // add the move to our list
        moves.push(newMove);
    }

    public void undoMove() {
        moves.pop();
        makeMove(moves.peek());
    }

    public boolean isSolved() {
        return true;
    }

    public String toString() {
        return g.toString();
    }
}
