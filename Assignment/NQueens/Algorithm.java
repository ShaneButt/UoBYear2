import java.util.ArrayList;

public class Algorithm {
    private Board thisBoard; // The board to perform the Algorithm on
    private Queen firstQueen; // The first queen placed
    private ArrayList<Queen> Queens = new ArrayList<Queen>(8); // The queens
    private ArrayList<Tile> Safe; // Safe tiles
    private int iterations; // Number of times the algorithm has operated

    public Algorithm(Board b, Queen q) { // Simple assignment constructor, no explanation needed.
        thisBoard = b;
        firstQueen = q;
        Queens.add(q);
        iterations = 0;
        Safe = thisBoard.safeTiles();
    }

    public boolean solve(int queensToPlace, ArrayList<Tile[]> tiles) {
        if (queensToPlace == 0) { // Have we reached the end / Placed all queens?
            return true; // Yes, return true.
        }
        // No, iterate up
        iterations++;
        int width = thisBoard.getWidth();

        for (int y = 0; y < width; y++) {
            for (int x = 0; x < width; x++) {
                // Nested for loop for x and y values.
                Tile t = tiles.get(x)[y]; // Grabs the current Tile
                if (t.tileEmpty()) { // Is the tile free to place a queen on?
                    Queen q = thisBoard.placeQueen(x, y); // Places a queen at the current location
                    Queens.add(q); // Adds it to the Queens array list
                    if (solve(queensToPlace - 1, thisBoard.tiles())) // Can we find a solution with this queen?
                        return true; // If yes, return true
                    Queens.remove(q); // Else remove the queen from the array
                    thisBoard.removeQueen(q); // and remove it off the board
                    regenerateConflicts(); // Simple regeneration of conflicting tiles
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
            q.checkDiagonals(TileState.CONFLICT); // Regenerates the board by checking diagonals of all queens and resetting the tiles to conflicting
        }
    }

    public int getIterations() {
        return iterations;
    }

}
