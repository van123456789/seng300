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
			System.out.println("----------------------------------------");
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
		aSession = input;
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
		
		JSONParser jsonParser = new JSONParser();
		 try (FileReader reader = new FileReader("requesting.json")) {
	        	
	            Object obj = jsonParser.parse(reader);
	            JSONArray requestList = (JSONArray) obj;
	            
	            for (String course_name : requesting_courses) {
	    			Course aCourse = new Course(course_name, aSession);
	    			aCourse.fillout1();
	    			String course_id = aCourse.getCourse_id();
	    			
	    			JSONObject request = new JSONObject();
	                request.put("instructor", this.id);
	                request.put("course_id", course_id);
	                request.put("status", "waiting");
	                requestList.add(request);
	    		}
	            
	            FileWriter file = new FileWriter("requesting.json");
	            
	            file.write(requestList.toJSONString());
	            file.flush();
	            
	            System.out.println("Request sent");
	            

	     }
		 catch (Exception e) {
			 e.printStackTrace();
		 }
		 
		 remove_duplicated_request();
		
		
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
		JSONParser jsonParser = new JSONParser();
        
        try (FileReader reader = new FileReader("requesting.json")) {
        	
            Object obj = jsonParser.parse(reader);
            JSONArray requestList = (JSONArray) obj;
            
			JSONObject request = new JSONObject();
            for (int i = 0; i < requestList.size(); i++) {
            	request = (JSONObject) requestList.get(i);
            	String instructor_id = (String) request.get("instructor");
            	
            	if (instructor_id.equals(this.id)) {
            		String status = (String) request.get("status");
                	String course_id = (String) request.get("course_id");
                	
                	Course aCourse = new Course(course_id);
                	aCourse.fillout2();
                	System.out.println("Course name:" + aCourse.getCoursename() + " - " + "session: " + aCourse.getSession() + " - " + "status : " + status);
            	}
            	
            	
            	
            	
            }
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
	
	private void remove_duplicated_request() {
		JSONParser jsonParser = new JSONParser();
        
        try (FileReader reader = new FileReader("requesting.json")) {
        	
            Object obj = jsonParser.parse(reader);
            JSONArray requestList = (JSONArray) obj;
            
            ArrayList<JSONObject> objects_to_delelete = new ArrayList<>();
            
			JSONObject request1 = new JSONObject();
			JSONObject request2 = new JSONObject();
			String instructor_id1;
			String instructor_id2;
			String course_id1;
    		String course_id2;
    		String status1;
    		String status2;

    		
    		
            for (int i = 0; i < requestList.size(); i++) {
            	request1 = (JSONObject) requestList.get(i);
            	course_id1 = (String) request1.get("course_id");
            	for (int j = i; j < requestList.size(); j++) {
            		request2 = (JSONObject) requestList.get(j);
            		course_id2 = (String) request2.get("course_id");
                	if (course_id1.equals(course_id2) && (i != j)) {
                		instructor_id1 = (String) request1.get("instructor");
                		instructor_id2 = (String) request2.get("instructor");
                		status1 = (String) request1.get("status");
                		status2 = (String) request2.get("status");
                		if (instructor_id1.equals(instructor_id2) && status1.equals(status2)) {
                			objects_to_delelete.add(request2);
                		}
                	}
            	}
            }
            
            for (JSONObject o : objects_to_delelete) {
            	requestList.remove(o);
            }
            FileWriter file = new FileWriter("requesting.json");
            
            file.write(requestList.toJSONString());
            file.flush();
            
            
            
            
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
	
	
	
	
	public static void main(String[] args) {
		Instructor aInstructor = new Instructor("7643541004");
		//aInstructor.remove_duplicated_request();
		aInstructor.run();
		//Instructor.inititialize();
	}
	
	public static void inititialize() {
		
		
		JSONObject aRequest = new JSONObject();
        aRequest.put("instructor", "7643541004");
        aRequest.put("course_id", "91903");
        aRequest.put("status", "waiting");
         

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
