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
		String option = "";

		while (true)
		{
			System.out.println("**** menu ****");
			System.out.println("1. login");
			System.out.println("2. register");
			System.out.println("q. quit");
			System.out.println("**************");

			option = sc.nextLine();
			switch(option)
			{
				case "1":
					new Login();	// creates a new session for a user, if they are successfully authenticated
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
	}	
}
