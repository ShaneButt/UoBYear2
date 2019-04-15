package Lab2;

import java.util.Scanner;

public class Main {
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		int Num = sc.nextInt();
		sc.close();
		for(int i = 1; i <= 12; i++)
		{
			System.out.printf("%2d * %1d = %3d\n", i, Num, i*Num);
		}
	}
}
