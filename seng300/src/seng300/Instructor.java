package seng300;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Instructor extends User
{
	private Scanner sc;
	private String user_choice = "";
	
	public Instructor(String id) {
		super(id);
	}

	public void run() 
	{
		System.out.println("Please select your option:");
		System.out.println("1 - request to teach a course");
		System.out.println("2 - request to remove a course");
		System.out.println("3 - update course information");
		
		sc = new Scanner(System.in);
		
		while (true) {
			user_choice = sc.nextLine();
			if (user_choice.equals("1")) {
				break;
			}
			if (user_choice.equals("2")) {
				break;
			}
			if (user_choice.equals("3")) {
				break;
			}			
			else {
				System.out.println("Incorrect response");
			}

		}
	}	
}
