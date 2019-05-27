package seng300;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		System.out.println("Enter your unique combination to continue: ");
		Scanner myScanner = new Scanner(System.in);
		try {
			int unique_combination = myScanner.nextInt();
		}
		catch (InputMismatchException e) {
			System.out.println("Invalid input");
		}
	}
	
	private boolean checker(int combination) {
		boolean toReturn = false;
		
		
		
		return toReturn;
	}

}
