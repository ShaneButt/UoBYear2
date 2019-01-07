import java.util.ArrayList;

public class Queen {
    private Board thisBoard;
    private Tile QueenTile;
    private Tile[] Row;
    private int iRow;
    private int iCol;
    public String QUEEN_CHAR = "Q";

    public Queen(Board b, Tile[] Row, int iRow, int iCol, TileState QueenState) {
        thisBoard = b;
        this.Row = Row;
        this.iRow = iRow;
        this.iCol = iCol;
        QueenTile = Row[this.iCol];
        QueenTile.setState(QueenState);
    }

    public void generateConflicts() {
        checkDiagonals(TileState.CONFLICT);
    }

    public void checkDiagonals(TileState state) {
        int width = thisBoard.getWidth();
        ArrayList<Tile[]> tiles = thisBoard.tiles();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) {
                int dif = Math.abs(y - iCol);
                if ((iRow == x)
                        || (iCol == y)
                        || (iRow + dif == x)
                        || (iRow - dif == x)) {
                    Tile t = tiles.get(x)[y];
                    if (t.getState() != TileState.QUEEN && t.getState() != TileState.MYQUEEN) {
                        t.setState(state);
                    }
                }
            }
        }
    }

    public Tile getQueenTile() {
        return QueenTile;
    }

    public void setQueenTile(Tile tile) {
        QueenTile = tile;
    }

    @Override
    public String toString() {
        return QueenTile.toString();
    }
}
