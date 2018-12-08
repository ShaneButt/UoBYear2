public class Tile {
    private Board thisBoard;
    private TileState thisState;

    public Tile(Board board, TileState state) {
        thisBoard = board;
        thisState = state;
    }

    public void SetBoard(Board b) {
        thisBoard = b;
    }

    public Board GetBoard() {
        return thisBoard;
    }

    public void SetState(TileState state) {
        thisState = state;
    }

    public TileState GetState() {
        return thisState;
    }

    public boolean TileEmpty() {
        return thisState == TileState.EMPTY;
    }

    @Override
    public String toString() {
        switch (thisState) {
            case EMPTY:
                return ".";
            case QUEEN:
                return "Q";
            case CONFLICT:
                return "X";
            default:
                return ".";
        }
    }
}
