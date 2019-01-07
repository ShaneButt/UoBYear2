import java.util.ArrayList;

public class Algorithm {
    private Board thisBoard;
    private Queen firstQueen;
    private ArrayList<Queen> Queens = new ArrayList<Queen>(8);
    private ArrayList<Tile[]> Safe;
    private int iterations;

    public Algorithm(Board b, Queen q) {
        thisBoard = b;
        firstQueen = q;
        Queens.add(q);
        iterations = 0;
        Safe = thisBoard.safeTiles();
    }

    public boolean solve(int queensToPlace, ArrayList<Tile[]> tiles) {
        if (queensToPlace == 0) {
            return true;
        }
        iterations++;
        int width = thisBoard.getWidth();

        for (int y = 0; y < width; y++) {
            for (int x = 0; x < width; x++) {
                Tile t = tiles.get(x)[y];
                if (t.getState() == TileState.EMPTY) {
                    Queen q = thisBoard.placeQueen(x, y);
                    Queens.add(q);
                    if (solve(queensToPlace - 1, thisBoard.tiles()))
                        return true;
                    Queens.remove(q);
                    thisBoard.removeQueen(q);
                    regenerateConflicts();
                }
            }
        }
        return false;
    }

    public ArrayList<Queen> getQueens() {
        return Queens;
    }

    public Board getSolution() {
        return thisBoard;
    }

    public void regenerateConflicts() {
        for (Queen q : Queens) {
            q.checkDiagonals(TileState.CONFLICT);
        }
    }

    public int getIterations() {
        return iterations;
    }

}
