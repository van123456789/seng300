package seng300;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Instructor extends User{
	private String aSession;

	public Instructor(String id) {
		super(id);
	}
	
	
	public void run() {
		System.out.println("Enter your action:");
		System.out.println("Type 1 to request to teach a course");
		System.out.println("Type 2 to view current request's result");
		Scanner myScanner = new Scanner(System.in);
		String action = null;
		boolean notDone = true;
		while (notDone) {
			action = myScanner.nextLine();
			if (action.equals("1") || action.equals("2")) {
				notDone = false;
			}
			else {
				System.out.println("Incorrect input");
			}
		}
		
		
		if (action.equals("1")) {
			requestcourse();
		}
		else {
			reviewrequest();
		}
	}
	
	public void requestcourse() {
		System.out.println("Please enter the term you want to request to teach");
		Scanner myScanner = new Scanner(System.in);
		
		String input = "";
		boolean validInput = false;
		
		while  (!validInput) {
			input = myScanner.nextLine();
			if (Course.isValidSession(input)) {
				validInput = true;
				aSession = input;
			}
			else {
				System.out.println("Invalid type input");
			}
		}
		System.out.println("These are unassigned courses");
		print_unassigned_courses();
		System.out.println("Please enter course you want to teach, type exit to stop adding");
		get_request();
		
	}
	
	
	public void get_request() {
		boolean isFinished = false;
		Scanner myScanner = new Scanner(System.in);
		String input = null;
		List<String> courses = this.getUnassigned_courses();
		List<String> requesting_courses = new ArrayList<>();
		int state = 1;
		
		while (!isFinished) {
			input = myScanner.nextLine();
			
			if (state == 1) {
				if (courses.contains(input)) {
					requesting_courses.add(input); 
					System.out.println(requesting_courses);
				}
				if (input.contentEquals("exit")) {
					state = 2;
				}
				
				else {
					System.out.println("Invalid Input");
				}
			}
			
			if (state == 2) {
				System.out.println("These are courses you are requesting:");
				System.out.println(requesting_courses);
				System.out.println("Type ok to confirm this request");
				System.out.println("Type remove to remove courses in the list");
				
				if (input.contentEquals("ok")) {
					send_request(requesting_courses);
				}
				if (input.contentEquals("remove")) {
					boolean isDone = false;
					while (!isDone) {
						System.out.println("Please enter course name you want to remove");
						System.out.println("Please type exit to exit");
						input = myScanner.nextLine();
						
						if (requesting_courses.contains(input)) {
							requesting_courses.remove(input);
							System.out.println(requesting_courses);
							
						}
						else {
							System.out.println("Cant find the mentioned course");
						}
					}
					
				}
			}
		}
	}
	
	
	
	public void send_request(List<String> requesting_courses) {
		
	}
	
	public void print_unassigned_courses() {
		System.out.println(this.getUnassigned_courses());
	}
	
	public List<String> getUnassigned_courses(){
		List<String> courses = new ArrayList<>();
		try {
			FileReader courselist = new FileReader("courselist.data");
			BufferedReader courselist_reader = new BufferedReader(courselist);
			
			
			String temp;
			while ((temp = courselist_reader.readLine()) != null) {
				String[] splitedLine = temp.split("-");
				if (splitedLine[2].contentEquals("")) {
					courses.add(splitedLine[1]);
				}
			}
		}
		
		catch (Exception e) {
			System.out.println("Error in print_unassigned_course");
		}
		return courses;
	}
		
	
	public void reviewrequest() {
		
	}
	
	public static void main(String[] args) {
		Instructor aInstructor = new Instructor("7643541004");
		aInstructor.run();
	}

}
