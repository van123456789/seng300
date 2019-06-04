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

import com.fasterxml.jackson.databind.ObjectMapper;

public class Instructor extends User
{
	private Scanner sc = new Scanner(System.in);
	private String user_choice = "";
	private ObjectMapper objmapper = new ObjectMapper();
	
	// start new instructor session for specific id
	public Instructor(String id) 
	{
		run(id);
	}

	// for user creation
	public Instructor (String id, String privilege, String fn, String ln)
	{
		this.id = id;
		this.privilege = privilege;
		this.firstname = fn;
		this.lastname = ln;
		this.teaching = new ArrayList<Course>();
	}

	public void run(String id) 
	{
		
		System.out.println("Please select your option:");
		System.out.println("1 - request to teach a course");
		System.out.println("2 - request to remove a course");
		System.out.println("3 - update course information");
				
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
