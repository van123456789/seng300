package seng300;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

	public static void main(String[] args) throws Exception {
		//String combination;
		//combination = getInput();
		//checker(combination);
		
		
		Course aCourse = new Course("58347");
		System.out.println(aCourse.getCourseName());
	}
	
	private static boolean checker(String combination) {
		boolean toReturn = false;
		id_checker checker = new id_checker(combination);
		int id_type = checker.authenticate();
		if (id_type == 1) {
			System.out.println("Student found");
			toReturn = true;
		}
		if (id_type == 2) {
			System.out.println("Instructor found");
			toReturn = true;
		}
		if (id_type == 3) {
			System.out.println("Head Department found");
			toReturn = true;
		}
		if (id_type == -1) {
			System.out.println("User not found");
		}
		
		
		return toReturn;
	}
	
	private static String getInput() {
		System.out.println("Enter your unique combination to continue: ");
		Scanner myScanner = new Scanner(System.in);
		String unique_combination = "Error";
		try {
			unique_combination = myScanner.nextLine();
		}
		catch (InputMismatchException e) {
			System.out.println("Invalid input");
		}
		
		return unique_combination;
	}

}
