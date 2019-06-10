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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;



public class Instructor extends User{
	private String aSession;

	public Instructor (String id, String privilege, String fn, String ln)
	{
		this.id = id;
		this.privilege = privilege;
		this.firstname = fn;
		this.lastname = ln;
		this.teaching = new ArrayList<Course>();
	}
	
	public Instructor (String id) {
		super.id = id;
	}
	
	public void run() {		
		boolean stillOn = true;
		while (stillOn) {
			System.out.println("Enter your action:");
			System.out.println("Type 1 to request to teach a course");
			System.out.println("Type 2 to view current request's result");
			System.out.println("Type 3 to exit the system");
			

			Scanner sc = new Scanner(System.in);
			String user_choice = sc.nextLine();
			
			switch(user_choice) 
			{
				case "1":
					requestcourse();
					break;

				case "2":
					reviewrequest();
					break;
					
				case "3":
					stillOn = false;
					break;
	
				default:
					System.out.println("invalid option");
					break;
			}		
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
		System.out.println("These are unassigned courses available: ");
		print_unassigned_courses(input);
		get_request(input);
		
	}
	
	
	public void get_request(String aSession) {
		boolean isFinished = false;
		Scanner myScanner = new Scanner(System.in);
		String input = null;
		List<String> unassigned_courses = this.getUnassigned_courses(aSession);
		List<String> requesting_courses = new ArrayList<>();
		int state = 1;
		
		while (!isFinished) {
			
			
			if (state == 1) {
				System.out.println("Please type in the course you want to request");
				System.out.println("Please type exit to stop adding");
				input = myScanner.nextLine();
				if (unassigned_courses.contains(input)) {
					requesting_courses.add(input);
					System.out.println("Courses you are requesting: ");
					System.out.println(requesting_courses);
				}
				if (input.contentEquals("exit")) {
					state = 2;
				}
				
				if (!unassigned_courses.contains(input) && !input.equals("exit")) {
					System.out.println("Invalid input");
				}
			}
			
			if (state == 2) {
				System.out.println("These are courses you are requesting:");
				System.out.println(requesting_courses);
				System.out.println("Type ok to confirm this request");
				System.out.println("Type remove to remove courses in the list");
				input = myScanner.nextLine();
				
				
				switch(input) {
					case "ok":
						isFinished = true;
						send_request(requesting_courses);
						break;
					
					case "remove":
						boolean isDone = false;
						while (!isDone) {
							System.out.println("Please enter course name you want to remove");
							System.out.println("Please type exit to exit");
							input = myScanner.nextLine();
							
							if (!requesting_courses.contains(input) && !input.equals("exit")) {
								System.out.println("Cant find the mentioned course");
							}
							
							if (requesting_courses.contains(input)) {
								requesting_courses.remove(input);
								System.out.println("Removed successfully, requesting courses are:");
								System.out.println(requesting_courses);
								
							}
							if (input.equals("exit")) {
								isDone = true;
							}
							
							
							
						}
						break;
						
					default:
						System.out.println("Invalid option");
						break;
					
				}
			}
		}
	}
	
	
	
	public void send_request(List<String> requesting_courses) {
		
	}
	
	public void print_unassigned_courses(String aSession) {
		System.out.println(this.getUnassigned_courses(aSession));
	}
	
	public List<String> getUnassigned_courses(String aSession){
		ArrayList<String> result = new ArrayList<>();
		JSONParser jsonParser = new JSONParser();
		ArrayList <String> courseload = new ArrayList<>();
        
        try (FileReader reader = new FileReader("test1.json")) {
        	
            Object obj = jsonParser.parse(reader);
            JSONArray courseList = (JSONArray) obj;
            
			JSONObject courseDetails=new JSONObject();
            for (int i = 0; i < courseList.size(); i++) {
            	courseDetails = (JSONObject) courseList.get(i);
            	String instructor = (String) courseDetails.get("instructor");
            	String session = (String) courseDetails.get("session");
            	String coursename = (String) courseDetails.get("coursename");
            	if (session.equals(aSession) && instructor.equals("")) {
            		result.add(coursename);
            	}
            }
            

        }
        catch (Exception e) {
        	System.out.println("Error in print_course_load");
        }
        
        return result;
        
	
	}
		
	
	public void reviewrequest() {
		
	}
	
	public static void main(String[] args) {
		//Instructor aInstructor = new Instructor("7643541004");
		//aInstructor.run();
		//Instructor.inititialize();
	}
	
	public static void inititialize() {
		
		
		JSONObject aRequest = new JSONObject();
        aRequest.put("instructor", "7643541004");
        ArrayList<String> requestingList = new ArrayList<>();
        requestingList.add("91903");
        requestingList.add("47326");
        aRequest.put("course_id", requestingList.toString());
         

        JSONArray request_total_list = new JSONArray();
        request_total_list.add(aRequest);
         
        //Write JSON file
        try (FileWriter file = new FileWriter("requesting.json")) {
 
            file.write(request_total_list.toJSONString());
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
	}


	
}
