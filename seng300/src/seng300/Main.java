package seng300;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

	
	public static void main(String[] args) throws Exception 
	{
		Scanner sc = new Scanner(System.in);

		System.out.println("**** menu ****");
		System.out.println("1. login");
		System.out.println("2. register");
		System.out.println("q. quit");
		System.out.println("**************");

		String option = "";
		while (true)
		{
			option = sc.nextLine();
			switch(option)
			{
				case "1":
					new Login();	// creates a new session for a user, if authenticated
					break;
				
				case "2":
					new Registration();
					break;
				
				case "q":
					System.exit(0);

				default:
					System.out.println("select a valid option");
					break;					
			}
		}
	/*
		String combination;
		combination = getInput();
		int id_type = checker(combination);
		if (id_type == 1) {
			runAsStudent(combination);
		}
		if (id_type == 2) {
			runAsInstructor(combination);
		}
		if (id_type == 3) {
			runAsHeadDep(combination);
		}
*/				
	}
	
	
	
	
	
	
	
	
/*	
	private static void runAsStudent(String combination) {
		Student aStudent = new Student(combination);
		aStudent.run();
	}
	private static void runAsInstructor(String combination) {
		
	}
	private static void runAsHeadDep(String combination) {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private static int checker(String combination) {
		int toReturn = -1;
		id_checker checker = new id_checker(combination);
		int id_type = checker.authenticate();
		if (id_type == 1) {
			System.out.println("Student found");
			toReturn = 1;
		}
		if (id_type == 2) {
			System.out.println("Instructor found");
			toReturn = 2;
		}
		if (id_type == 3) {
			System.out.println("Head Department found");
			toReturn = 3;
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
*/
}
