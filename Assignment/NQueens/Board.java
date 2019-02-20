import java.util.ArrayList;
import java.util.Scanner;

public class Board {

    private int Width = 8; // Board width/length
    private ArrayList<Tile[]> Tiles = new ArrayList<Tile[]>(Width*Width); //
    private ArrayList<Tile> Safe = new ArrayList<Tile>(Width*Width);
    /*
     * Width: The board's width
     * Tiles: An ArrayList of columns of tiles
     *
     */

    public Board() {
        setup();
    } // constructor

    private void setup() {
        for (int x = 0; x < Width; x++) { // Iterates through the rows
            Tile[] Column = new Tile[Width]; // Grabs the column
            for (int y = 0; y < Width; y++) { // Iterates through the column
                Tile t = new Tile(this, TileState.EMPTY, x, y); // Fills the column with Empty Tiles
                Column[y] = t;
                Safe.add(t);
            }
            Tiles.add(Column); // Adds the column to the Tiles arraylist
        }
        System.out.println("SETUP SAFE: " + Safe.size());
    }


    /*  Returning the queen in the following
        methods allows them to be used later on
        in other parts of the program.
     */

    public Queen placeMyQueen(String pos) {
        Scanner sc = new Scanner(System.in);
        int letter = pos.toLowerCase().charAt(0) - 97; // changes letter to lowercase, subtracts the ascii code for ('a'+1) to make it 0-based
        int number = Integer.parseInt(pos.substring(1, 2)) - 1; // attempt to parse '1' as 1, then subtracts 0 to make it 0-based

        if ((letter < 0 || number < 0) || (letter > Width || number > Width)) // ArrayIndexOutOfBounds Catch
        {
            System.out.println("Please enter a valid position..."); // Not valid, try again
            return placeMyQueen(sc.next()); // Recursive call until a valid position is entered
        }
        sc.close();
        Queen q = new Queen(this, Tiles.get(number), number, letter, TileState.MYQUEEN); // Construct a new queen, as MyQueen state
        q.generateConflicts(); // Generate the conflicts on the board for the queen
        return q;
    }

    public Queen placeQueen(String pos) {
        // same as above other than commented lines
        Scanner sc = new Scanner(System.in);
        int letter = pos.toLowerCase().charAt(0) - 97;
        int number = Integer.parseInt(pos.substring(1, 2)) - 1;

        if ((letter < 0 || number < 0) || (letter > Width || number > Width)) // ArrayIndexOutOfBounds Catch
        {
            System.out.println("Please enter a valid position...");
            return placeQueen(sc.next());
        }
        sc.close();
        Queen q = new Queen(this, Tiles.get(number), number, letter, TileState.QUEEN); // Constructs a new queen, as Queen state.
        q.generateConflicts();
        return q;
    }

    public Queen placeQueen(int row, int col) {
        Queen q = new Queen(this, Tiles.get(row), row, col, TileState.QUEEN); // Constructs a new queen as Queen state, at a specific location
        q.generateConflicts(); // Generates the conflicts for the queen
        return q;
    }

    public void removeQueen(Queen q) {
        q.checkDiagonals(TileState.EMPTY); // Make the conflicting tiles now empty (this has an issue with unconflicting actually conflicting tiles because of other queens
        // hence the "regenerateConflicts" method in Algorithm.java
        q.getQueenTile().setState(TileState.EMPTY); // Set the queen tile to empty
    }

    public void display() {
        // Iterates through the tiles on the board and displays their State (M, Q, X, .) - MyQueen, Queen, Conflict, Empty
        System.out.println("\\|_A__B__C__D__E__F__G__H_"); // A-H for 0-7 x axis
        for (int col = 0; col < Tiles.size(); col++) {
            System.out.print(col + 1 + "|"); // 1-8 for 0-7 y axis
            for (int row = 0; row < Width; row++) {
                Tile t = Tiles.get(col)[row];
                System.out.print(" " + t + " "); // Displays state
            }
            System.out.println();
        }
    }

    public ArrayList<Tile> safeTiles() { return Safe; }

    public void setSafe(ArrayList<Tile> safeTiles) { Safe = safeTiles; }

    public int getWidth() {
        return this.Width;
    }

    public ArrayList<Tile[]> tiles() {
        return Tiles;
    }

}