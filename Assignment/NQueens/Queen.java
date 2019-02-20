import java.util.ArrayList;

public class Queen {
    /*
        Queen is effectively a psuedo-extension of Tile, without the junk
     */
    private Board thisBoard; // The board the Queen belongs to
    private Tile QueenTile; // The tile the Queen is on
    private Tile[] Row; // The row-array that the Queen is on
    private int iRow;
    private int iCol;

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
        ArrayList<Tile> safeTiles = thisBoard.safeTiles();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) { // Iterates through the Tiles of the board
                int dif = Math.abs(y - iCol);
                if ((iRow == x)
                        || (iCol == y)
                        || (iRow + dif == x)
                        || (iRow - dif == x)) { // Checks the axis and diagonals for conflicting tiles
                    Tile t = tiles.get(x)[y]; // grabs the tile
                    if (t.getState() != TileState.QUEEN && t.getState() != TileState.MYQUEEN) {
                        t.setState(state); // Sets them to conflicting if the Tile is either a computer Queen or a user Queen.
                    }
                    TileState tState = t.getState();
                    if( tState==TileState.CONFLICT || tState==TileState.QUEEN || tState==TileState.MYQUEEN)
                    {
                        safeTiles.remove(t);
                    }
                }
            }
        }
        thisBoard.setSafe(safeTiles);
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
    } // Utilises the Tile to display what the Queen is actually
}
