import java.time.Instant;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Information:\n- M = User Queen\n- Q = Generated Queen\n- X = Conflicting tile\n\nOutputs one singular solution to a given placement of a Queen.\n");
        Scanner scanner = new Scanner(System.in);
        Board b = new Board();
        System.out.println("Please enter the position of the first Queen");
        String pos = scanner.nextLine();
        Queen q = b.PlaceMyQueen(pos);
        System.out.println("Current Board layout:");
        b.Display();
        System.out.println();
        Algorithm a = new Algorithm(b, q);
        long start = Instant.now().toEpochMilli();
        boolean computed = a.Solve(7, b.Tiles());
        long end = Instant.now().toEpochMilli();
        if (computed) {
            a.GetSolution().Display();
            System.out.println("\n\nSolution (found in " + (end - start) + "ms): ");
            System.out.println("Number of backtracks: " + a.GetIterations());
        } else
            System.out.println("No solution found! Program ran for " + (Instant.now().toEpochMilli() - start) + "ms");
    }

}
