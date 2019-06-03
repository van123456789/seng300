package seng300;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.event.ListSelectionEvent;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class HeadDepartment extends User
{
	static Scanner sc;
	static String option = "";
	
	public HeadDepartment() 
	{
		// TODO
		// get userbase, instantiate it
		
	}
	public HeadDepartment (String id, String privilege, String fn, String ln)
	{
		this.id = id;
		this.privilege = privilege;
		this.firstname = fn;
		this.lastname = ln;
	}
	
	public static void run () 
	{
		sc = new Scanner(System.in);

		System.out.println("0. show courses");
		System.out.println("1. add course");
		System.out.println("2. assign instructor");
		System.out.println("q. quit");

		while (true)
		{
			option = sc.nextLine();
			
			switch(option) 
			{
				case "q":
					System.exit(0);
					break;
				case "0":
					show_courses();
					break;
				case "1":
					add_course();
					break;
				case "2":
					assign_instructor();
					break;
				case "3":
					break;
				default:
					System.out.println("choose a valid option");
					break;
			}
		}	
	}
	
	// shows courses already present in the system
	public static void show_courses()
	{
		try 
		{
			ObjectMapper objmapper = new ObjectMapper();
			File temp = new File("courselist.json");
			if (temp.createNewFile()) 
				System.out.println("no courses exists in the system yet");
			else 
			{
				ArrayList<Course> clist = objmapper.readValue(temp,  new TypeReference<ArrayList<Course>>() {});
				System.out.println(clist);
				for (Course c : clist)
				{
					System.out.println(c.getCoursename());
				}	
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
	
	// to add user specified course to the system, does some basic checkings
	public static void add_course()
	{
		// ObjectMapper is for serializing/deserializing json objects
		ObjectMapper objmapper = new ObjectMapper();
		ArrayList<Course> course_list = new ArrayList<Course>();

		String course_code = "";
		String course_name = ""; 
		String instructor = ""; 
		String session = ""; 
		String section = "";
		Course c;

		// check for courselist.json
		try 
		{	
			File temp = new File("courselist.json");
			
			if (temp.createNewFile())
			{
				System.out.println("file is created");
				System.out.println("adding course..");
			}	
			else 
			{
				// courselist.json is already present in the system
				// create the course_list
				course_list = objmapper.readValue(temp,  new TypeReference<ArrayList<Course>>() {});
			}

			// ask head department for specification of the course
			System.out.println("enter course code");
			course_code = sc.nextLine();
			
			System.out.println("enter course name");
			course_name = sc.nextLine();

			System.out.println("enter instructor");
			instructor = sc.nextLine();

			System.out.println("enter session");
			session = sc.nextLine();

			System.out.println("enter section");
			section = sc.nextLine();

			// check if user specified course already exists in the system
			c = new Course(course_code, course_name, instructor, session, section);
			if (!course_exists(c, course_list)) 
				course_list.add(c);

			// add course to database
			objmapper.writerWithDefaultPrettyPrinter().writeValue(temp, course_list);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}		
	}

	// compares and checks if given course already exists in given course_list (in the db)
	public static boolean course_exists(Course c, ArrayList<Course> course_list) 
	{
		boolean course_exists = false;

		for (Course course : course_list) 
		{
			if (course.getCourse_id().equals(c.getCourse_id())) 
			{
				System.out.println("course is already in the system");
				course_exists = true;
				break;				
			}
			if (course.getCoursename().equals(c.getCoursename()))
			{
				System.out.println("course is already in the system");
				course_exists = true;
				break;
			}	
		}
		return course_exists;
	}
	
	// add instructor to the course
	// WIP, need to restructure instructor class first i think
	public static void assign_instructor() 
	{	// TODO assign instructor to a course
		ObjectMapper objmapper = new ObjectMapper();
		ArrayList<Course> course_list = new ArrayList<Course>();
		try 
		{
			File temp = new File("courselist.json");
			
			// show which course needs an instructor
			if (temp.createNewFile()) 
				System.out.println("no courses exists in the system yet");
			else 
			{
				System.out.println("currently, these course(s) needs an instructor: ");
				course_list = objmapper.readValue(temp,  new TypeReference<ArrayList<Course>>() {});
			}			
			// display which courses needs attnetion
			instructor_needed(course_list);
			
			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void instructor_needed(ArrayList<Course> cs) 
	{
		String temp_str = "";
		for (Course c : cs)
		{
			if (c.getInstructor().equals(""))
				temp_str = temp_str + " " + c.getCoursename();
		}	
		System.out.println(temp_str);		
	}
	
	public static void main(String [] args) 
	{
		run();
	}
}