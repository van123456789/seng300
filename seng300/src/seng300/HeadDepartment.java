package seng300;

import java.io.File;
import java.io.IOException;
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
	private Scanner sc = new Scanner(System.in);
	private String option = "";
	private SessionManager sm = new SessionManager();
	private ArrayList<Course> clist;
	private ArrayList<User> ulist;
	private ObjectMapper objmapper;
	
	public HeadDepartment(String id) 
	{
		this.objmapper = new ObjectMapper();
		run(id);
	}
	
	// for user creation
	public HeadDepartment (String id, String privilege, String fn, String ln)
	{
		this.id = id;
		this.privilege = privilege;
		this.firstname = fn;
		this.lastname = ln;
	}
	
	public void run (String id) 
	{
		clist = sm.getCourseList("courselist.json");
		ulist = sm.getUserList("userlist.json");

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
	public void show_courses()
	{
		for (Course c : clist)
			System.out.println(c.getCoursename());
	}
	
	// to add user specified course to the system, does some basic checkings
	public void add_course()
	{
		try 
		{
			String course_code = "";
			String course_name = ""; 
			String instructor = ""; 
			String session = ""; 
			String section = "";
			Course c;
	
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
			if (!course_exists(c, clist)) 
				clist.add(c);
	
			// add course to database
			objmapper.writerWithDefaultPrettyPrinter().writeValue(new File("courselist.json"), clist);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	// compares and checks if given course already exists in given course_list (in the db)
	public boolean course_exists(Course c, ArrayList<Course> course_list) 
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
	public void remove_course() 
	{
		ArrayList<Course> to_remove = new ArrayList<Course>();
		String rm_course = "";
		try 
		{
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
			
			objmapper.writerWithDefaultPrettyPrinter().writeValue(new File("courselist.json"), clist);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}		
	}
	// add instructor to the course
	// WIP, need to restructure instructor class first i think
	public void assign_instructor() 
	{
		String course_to_update = "";
		String instructor_id = "";
		
		try 
		{
			System.out.println("currently, these course(s) needs an instructor: ");
			// display which courses needs attnetion
			instructor_needed(clist);
			System.out.println("these instructors are available to teach: ");
			show_instructors(ulist);
			
			System.out.println("choose a course to assign instructor");
			course_to_update = sc.nextLine();
			System.out.println("enter instructor id");
			instructor_id = sc.nextLine();

			for (Course c : clist)
			{
				if (c.getCoursename().equals(course_to_update)) 
				{
					c.setInstructor(instructor_id);
					
					for (User u : ulist)
					{
						if (u.getId().equals(instructor_id))
							u.getTeaching().add(c);
					}						
				}
			}
			
			objmapper.writerWithDefaultPrettyPrinter().writeValue(new File("courselist.json"), clist);
			objmapper.writerWithDefaultPrettyPrinter().writeValue(new File("userlist.json"), ulist);

		} 
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// show available instructors
	public void show_instructors(ArrayList<User> us) 
	{
		for (User u : us)
		{
			if (u.getPrivilege().equals("2"))
			{
				if (u.getTeaching().size() < 5)
					System.out.println(u.getFn() + " " + u.getLn());
			}					
		}	
	}
	
	public void instructor_needed(ArrayList<Course> cs) 
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