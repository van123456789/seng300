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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class HeadDepartment extends User
{
	static Scanner sc;
	static String option = "";
	
	public HeadDepartment(String id) 
	{
		run(id);
	}
	
	public HeadDepartment (String id, String privilege, String fn, String ln)
	{
		this.id = id;
		this.privilege = privilege;
		this.firstname = fn;
		this.lastname = ln;
	}
	
	public static void run (String id) 
	{
		sc = new Scanner(System.in);

		System.out.println("0. show courses");
		System.out.println("1. add course");
		System.out.println("2. assign instructor");
		System.out.println("3. remove course");
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
					remove_course();
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

	// prompts user to remove a course
	public static void remove_course() 
	{
		ObjectMapper objmapper = new ObjectMapper();
		ArrayList<Course> clist = new ArrayList<Course>();
		ArrayList<Course> to_remove = new ArrayList<Course>();
		String rm_course = "";
		Scanner sc = new Scanner(System.in);

		try 
		{
			File temp = new File("courselist.json");
			if (temp.createNewFile()) 
				System.out.println("no courses exists in the system yet");
			else 
				clist = objmapper.readValue(temp,  new TypeReference<ArrayList<Course>>() {});

			show_courses();
			// actual deletion			
			System.out.println("enter the name of the course you would like to remove");
			rm_course = sc.nextLine();

			for (Course c : clist)
			{
				if (c.getCoursename().equals(rm_course))
					to_remove.add(c);
			}
			clist.removeAll(to_remove);
			
			objmapper.writerWithDefaultPrettyPrinter().writeValue(temp, clist);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		
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
}