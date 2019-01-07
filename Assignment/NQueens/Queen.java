import java.util.ArrayList;

public class Queen {
    private Board thisBoard;
    private Tile QueenTile;
    private Tile[] Row;
    private int iRow;
    private int iCol;
    public String QUEEN_CHAR = "Q";

    public Queen() {

    }

    public Queen(Board b, Tile[] Row, int iRow, int iCol, TileState QueenState) {
        thisBoard = b;
        this.Row = Row;
        this.iRow = iRow;
        this.iCol = iCol;
        QueenTile = Row[this.iCol];
        QueenTile.SetState(QueenState);
    }

    public void GenerateConflicts() {
        CheckDiagonals(TileState.CONFLICT);
    }

    public void CheckDiagonals(TileState state) {
        int width = thisBoard.GetWidth();
        ArrayList<Tile[]> tiles = thisBoard.Tiles();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) {
                int dif = Math.abs(y - iCol);
                if ((iRow == x)
                        || (iCol == y)
                        || (iRow + dif == x)
                        || (iRow - dif == x)) {
                    Tile t = tiles.get(x)[y];
                    if (t.GetState() != TileState.QUEEN && t.GetState() != TileState.MYQUEEN) {
                        t.SetState(state);
                    }
                }
            }
        }
    }

    public Tile GetQueenTile() {
        return QueenTile;
    }

    public void SetQueenTile(Tile tile) {
        QueenTile = tile;
    }

    @Override
    public String toString() {
        return QueenTile.toString();
    }
}
