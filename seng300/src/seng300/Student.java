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
import org.json.simple.parser.ParseException;



public class Student extends User
{
	Scanner sc = new Scanner(System.in);
	
	String id;
	
	// probably add string id as parameter, so i can get all the information i need when i start the class
	public Student (String id) 
	{
		this.id = id;
		run(id);
	}
	
	
	public Student (String id, String password, String privilege, String name)
	{
		this.id = id;
		this.password = password;
		this.privilege = privilege;
		this.name = name;
		this.enrolled = new ArrayList<Course>();
	}

	public ArrayList<Course> getEnrolled() 
	{
		return enrolled;
	}


	public void setEnrolled(ArrayList<Course> enrolled) 
	{
		this.enrolled = enrolled;
	}


	public void run(String id) 
	{
		boolean stillOn = true;
		while (stillOn) 
		{
			System.out.println("Please select your option:");
			System.out.println("1 - to register courses for this session");
			System.out.println("2 - to register courses for future session");
			System.out.println("3 - to drop courses for this session");
			System.out.println("4 - to drop courses for future session");
			System.out.println("5 - to exit the system");

			Scanner sc = new Scanner(System.in);
			String user_choice = sc.nextLine();
			
			switch(user_choice) 
			{
				case "1":
					register_courses_for_term(EnvironmentConstant.getSession());
					break;

				case "2":
					register_future_term();
					break;
					
				case "3":
					drop_term(EnvironmentConstant.getSession());
					break;

				case "4":
					drop_past_term();
					break;

				case "5":
					stillOn = false;
					break;
					
				default:
					System.out.println("select a valid response");
					break;
			}		
		}
			
	}
	
	public void register_courses_for_term(String aSession) 
	{

		List<Course> courses_to_enrol = new ArrayList<>();
		System.out.println("These are courses you signed up: " );
		print_course_load(aSession);
		System.out.println("Theses are courses available for this term: ");
		print_available_courses(aSession);
		register_courses(courses_to_enrol, aSession);

	}
	
	public void print_available_courses(String aSession) 
	{
		JSONParser jsonParser = new JSONParser();
		ArrayList <String> courseload = new ArrayList<>();
        
        try (FileReader reader = new FileReader("test1.json")) 
        {	
            Object obj = jsonParser.parse(reader);
            JSONArray courseList = (JSONArray) obj;
            
			JSONObject courseDetails=new JSONObject();
            for (int i = 0; i < courseList.size(); i++) 
            {
            	courseDetails = (JSONObject) courseList.get(i);
            	String session = (String) courseDetails.get("session");
            	if (session.equals(aSession))
            		courseload.add((String) courseDetails.get("coursename"));            		
            }
        }
        catch (Exception e) 
        {
        	System.out.println("Error in print_course_load");
        }        
        System.out.println(courseload);
	}
	
	public void register_courses(List<Course> courses_to_enrol, String aSession) 
	{
		boolean notFinishedAdding = true;
		int state = 0;
		while (notFinishedAdding) 
		{
			if (state == 0) 
			{
				try 
				{
					System.out.println("Please enter the course you want to register:");
					System.out.println("Type exit to stop adding courses");
					String input = sc.nextLine();
					
					if (input.equals("exit")) 
						state = 1;
					else 
					{
						Course newCourse = new Course(input, aSession);
						newCourse.fillout1();
						if (newCourse.getCoursename()!="") 
						{
							courses_to_enrol.add(newCourse);
							System.out.println("Current courses to register:");
							System.out.println(courses_to_enrol);
						}
						else 
						{
							System.out.println("Course is invalid or not offered this session");
						}
					}					
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}				
			}
			else 
			{
				System.out.println("These are courses you are registering:");
				System.out.println(courses_to_enrol);
				System.out.println("Type ok to confirm");
				System.out.println("Type remove to remove specific course");
				String input = sc.nextLine();
				if (input.equals("ok")) 
				{
					add_to_database(courses_to_enrol);
					notFinishedAdding = false;
					break;
				}
				if (input.equals("remove")) 
				{
					System.out.println("Please enter the index for course you want to remove");
					try 
					{
						String index = sc.nextLine();
						if (0 <= Integer.parseInt(index) && Integer.parseInt(index) < courses_to_enrol.size()) 
						{
							courses_to_enrol.remove(Integer.parseInt(index));
							System.out.println("Course removed");
							state = 0;
						}
						else 
						{
							System.out.println("Out of boundary");
						}
					}
					catch (Exception e) 
					{
						System.out.println("Invalid input");
					}
				}
			}			
		}		
	}
	
	public void register_future_term() 
	{
		System.out.println("Please enter term you want to register in:");
		String aSession = sc.nextLine();
		if (Course.isValidSession(aSession)) 
			register_courses_for_term(aSession);
	}
	
	public void drop_term(String aSession) 
	{
		List<Course> courses_to_drop = new ArrayList<>();
		System.out.println("These are courses you signed up: ");
		print_course_load(aSession);
		drop_courses(courses_to_drop, aSession);
		System.out.println("Theses are courses you signed up: ");
		print_course_load(aSession);		
	}
	
	public void drop_courses(List<Course> courses_to_drop, String aSession) 
	{
		boolean notFinishedDropping = true;
		int state = 0;
		while (notFinishedDropping) 
		{
			if (state == 0) 
			{
				try 
				{
					System.out.println("Please enter the course you want to drop:");
					System.out.println("Type exit to stop dropping courses");
					String input = sc.nextLine();
					
					if (input.equals("exit")) 
						state = 1;
					else 
					{
						Course newCourse = new Course(input, aSession);
						newCourse.fillout1();
						if (newCourse.getCoursename()!="") 
						{
							courses_to_drop.add(newCourse);
							System.out.println("Current courses to drop:");
							System.out.println(courses_to_drop);
						}
						else 
						{
							System.out.println("Course is invalid or not offered this session");
						}
					}					
				}
				catch (Exception e)
				{
					System.out.println("Should not see me");
				}
				
			}
			else 
			{
				System.out.println("These are courses you are dropping:");
				System.out.println(courses_to_drop);
				System.out.println("Type ok to confirm");
				System.out.println("Type remove to remove specific course");
				String input = sc.nextLine();
				if (input.equals("ok")) 
				{
					remove_from_database(courses_to_drop);
					notFinishedDropping = false;
				}
				if (input.equals("remove")) 
				{
					System.out.println("Please enter the index for course you want to remove");
					try 
					{
						String index = sc.nextLine();
						if (0 <= Integer.parseInt(index) && Integer.parseInt(index) < courses_to_drop.size()) 
						{
							courses_to_drop.remove(Integer.parseInt(index));
							System.out.println("Course removed");
							state = 0;
						}
						else 
							System.out.println("Out of boundary");
					}
					catch (Exception e) 
					{
						System.out.println("Invalid input");
					}
				}
			}			
		}
	}
	
	
	public void remove_from_database(List<Course> courses_to_drop) 
	{
		for (Course c : courses_to_drop) 
		{
			c.remove_from_database(id);
		}
		System.out.println("removed successfully");
	}
	
	public void drop_past_term() 
	{
		System.out.println("Enter term you want to drop:");
		Scanner myScanner = new Scanner(System.in);
		boolean isValid = false;
		while (!isValid) 
		{
			String aSession = myScanner.nextLine();
			if (Course.isValidSession(aSession))
			{
				drop_term(aSession);
				isValid = true;
			}
			else 
			{
				System.out.println("Error input");
			}
		}
	}
	
	
	public void add_to_database(List<Course> courses_to_enrol) 
	{
		for (Course c : courses_to_enrol) 
		{
			c.add_student(id);
		}
		System.out.println("Added successfully");			
	}
	
	public void print_course_load(String aSession) 
	{	
		JSONParser jsonParser = new JSONParser();
		ArrayList <String> courseload = new ArrayList<>();
        
        try (FileReader reader = new FileReader("test1.json")) 
        {        	
            Object obj = jsonParser.parse(reader);
            JSONArray courseList = (JSONArray) obj;
            
			JSONObject courseDetails=new JSONObject();
            for (int i = 0; i < courseList.size(); i++) 
            {
            	courseDetails = (JSONObject) courseList.get(i);
            	ArrayList<String> enrolled_student = Course.arrayConvert((String) courseDetails.get("enrolled_student"));
            	String session = (String) courseDetails.get("session");
            	if (enrolled_student.contains(this.id) && session.equals(aSession)) 
            		courseload.add((String) courseDetails.get("coursename"));            		
            }
        }
        catch (Exception e) 
        {
        	System.out.println("Error in print_course_load");
        }        
        System.out.println(courseload);
	}


	
	
	public static void main(String[] args) 
	{
		Student aStudent = new Student("1351891440");		
	}
}