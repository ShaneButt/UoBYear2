public class Tile {
    private Board thisBoard; // the board the tile belongs to
    private TileState thisState; // the state that belongs to the tile

    public Tile(Board board, TileState state) {
        thisBoard = board;
        thisState = state;
    }

    public void setBoard(Board b) {
        thisBoard = b;
    }

    public Board getBoard() {
        return thisBoard;
    }

    public void setState(TileState state) {
        thisState = state;
    }

    public TileState getState() {
        return thisState;
    }

    public boolean tileEmpty() {
        return thisState == TileState.EMPTY;
    }

    /*
        Simply overrides the toString() method of Object,
        in order to Display the State, these can be modified, easily.
     */
    @Override
    public String toString() {
        switch (thisState) {
            case EMPTY:
                return ".";
            case MYQUEEN:
                return "M";
            case QUEEN:
                return "Q";
            case CONFLICT:
                return "X";
            default:
                return ".";
        }
    }
}
