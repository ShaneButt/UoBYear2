import java.util.ArrayList;

public class Algorithm {
    private Board thisBoard;
    private Queen firstQueen;
    private ArrayList<Queen> Queens = new ArrayList<Queen>(8);
    private int iterations;

    public Algorithm(Board b, Queen q) {
        thisBoard = b;
        firstQueen = q;
        Queens.add(q);
        iterations = 0;
    }

    public boolean Solve(int queensToPlace, ArrayList<Tile[]> tiles) {
        if (queensToPlace == 0) {
            return true;
        }
        iterations++;
        int width = thisBoard.GetWidth();

        for (int y = 0; y < width; y++) {
            for (int x = 0; x < width; x++) {
                Tile t = tiles.get(x)[y];
                if (t.GetState() == TileState.EMPTY) {
                    Queen q = thisBoard.PlaceQueen(x, y);
                    Queens.add(q);
                    if (Solve(queensToPlace - 1, thisBoard.Tiles()))
                        return true;
                    Queens.remove(q);
                    thisBoard.RemoveQueen(q);
                    RegenerateConflicts();
                }
            }
        }
        return false;
    }

    public ArrayList<Queen> GetQueens() {
        return Queens;
    }

    public Board GetSolution() {
        return thisBoard;
    }

    public void RegenerateConflicts() {
        for (Queen q : Queens) {
            q.CheckDiagonals(TileState.CONFLICT);
        }
    }

    public int GetIterations() {
        return iterations;
    }

}
