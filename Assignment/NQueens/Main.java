import java.time.Instant;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Information:\n- M = User Queen\n- Q = Generated Queen\n- X = Conflicting tile\n\nOutputs one singular solution to a given placement of a Queen.\n. = Empty tile\n");
        Scanner scanner = new Scanner(System.in);
        Board b = new Board(); // initialises a new board
        System.out.println("Please enter the position of the first Queen");
        String pos = scanner.nextLine(); // gets the user input position
        Queen q = b.placeMyQueen(pos); // places the queen at said position
        System.out.println("Current Board layout:");
        b.display(); // displays the current board with the first queen on it
        System.out.println("MAIN SAFE: " + b.safeTiles().size());
        System.out.println();
        Algorithm a = new Algorithm(b, q); // initialises a new algorithm with the board and first queen as inputs
        long start = Instant.now().toEpochMilli(); // grabs the start time in milliseconds from Jan 1st 1970 (Epoch)
        boolean computed = a.solve(7, b.tiles(), b.safeTiles()); // boolean to store the success of the algorithm
        long end = Instant.now().toEpochMilli(); // grabs the end time in milliseconds from the Epoch, after the algorithm has run
        if (computed) {
            a.getSolution().display(); // display the solution
            System.out.println("\n\nSolution (found in " + (end - start) + "ms): "); // display the program run time
            System.out.println("Number of backtracks: " + a.getIterations()); // display the number of backtracks
        } else
            System.out.println("No solution found! Program ran for " + (Instant.now().toEpochMilli() - start) + "ms"); // display the program run time if the program fails
    }
}