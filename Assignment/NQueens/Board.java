import java.util.ArrayList;
import java.util.Scanner;

public class Board {

    private int Width = 8;
    private ArrayList<Tile[]> Tiles = new ArrayList<Tile[]>();
    /*
     * Width: The board's width
     * Tiles: An ArrayList of columns of tiles
     * Queens: A queen's row index
     *
     */

    public Board() {
        //Width = size;
        setup();
    }

    private void setup() {
        for (int x = 0; x < Width; x++) {
            Tile[] Column = new Tile[Width];
            for (int y = 0; y < Width; y++) {
                Column[y] = new Tile(this, TileState.EMPTY);
            }
            Tiles.add(Column);
        }
    }

    public Queen PlaceMyQueen(String pos) {
        Scanner sc = new Scanner(System.in);
        int letter = pos.toLowerCase().charAt(0) - 97;
        int number = Integer.parseInt(pos.substring(1, 2)) - 1;

        if ((letter < 0 || number < 0) || (letter > Width || number > Width)) // ArrayIndexOutOfBounds Catch
        {
            System.out.println("Please enter a valid position...");
            return PlaceMyQueen(sc.next());
        }
        sc.close();
        Queen q = new Queen(this, Tiles.get(number), number, letter, TileState.MYQUEEN);
        q.GenerateConflicts();
        return q;
    }

    public Queen PlaceQueen(String pos) {
        Scanner sc = new Scanner(System.in);
        int letter = pos.toLowerCase().charAt(0) - 97;
        int number = Integer.parseInt(pos.substring(1, 2)) - 1;

        if ((letter < 0 || number < 0) || (letter > Width || number > Width)) // ArrayIndexOutOfBounds Catch
        {
            System.out.println("Please enter a valid position...");
            return PlaceQueen(sc.next());
        }
        sc.close();
        Queen q = new Queen(this, Tiles.get(number), number, letter, TileState.QUEEN);
        q.GenerateConflicts();
        return q;
    }

    public Queen PlaceQueen(int row, int col) {
        Queen q = new Queen(this, Tiles.get(row), row, col, TileState.QUEEN);
        q.GenerateConflicts();
        return q;
    }

    public void RemoveQueen(Queen q) {
        q.CheckDiagonals(TileState.EMPTY);
        q.GetQueenTile().SetState(TileState.EMPTY);
    }

    public ArrayList<Tile[]> safeTiles() {
        ArrayList<Tile[]> tiles = this.Tiles;
        for (Tile[] column : tiles) {
            for (Tile t : column) {
                if (t.GetState() != TileState.EMPTY) {
                    tiles.remove(t);
                }
            }
        }
        return tiles;
    }

    public void Display() {
        System.out.println("\\|_A__B__C__D__E__F__G__H_");
        for (int col = 0; col < Tiles.size(); col++) {
            System.out.print(col + 1 + "|");
            for (int row = 0; row < Width; row++) {
                Tile t = Tiles.get(col)[row];
                System.out.print(" " + t + " ");
            }
            System.out.println();
        }
    }

    public int GetWidth() {
        return this.Width;
    }

    public ArrayList<Tile[]> Tiles() {
        return Tiles;
    }

}
