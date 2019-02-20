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

    public boolean solve(int queensToPlace, ArrayList<Tile[]> tiles, ArrayList<Tile> safe) {
        if (queensToPlace == 0) { // Have we reached the end / Placed all queens?
            return true; // Yes, return true.
        }
        regenerateConflicts();
        // No, iterate up
        iterations++;
        int width = thisBoard.getWidth();
        //System.out.println(iterations);
        thisBoard.display();
        System.out.println("ALGORITHM [" + iterations + "]: " + safe.size());

        while(Queens.size() <= queensToPlace)
        {
            for(int i = 0; i < safe.size(); i++)
            {
                Tile t = safe.get(i);
                Queen q = thisBoard.placeQueen(t.getRow(), t.getCol());
                Queens.add(q);
                if(solve(queensToPlace-1, thisBoard.tiles(), thisBoard.safeTiles()))
                    return true;
                Queens.remove(q);
                thisBoard.removeQueen(q);
                regenerateConflicts();
                queensToPlace--;
                safe = thisBoard.safeTiles();
                System.out.println(safe);
                i -= Math.abs((thisBoard.safeTiles().size()-safe.size()));
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
