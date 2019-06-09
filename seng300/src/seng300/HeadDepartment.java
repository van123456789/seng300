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


// Session for head department user
public class HeadDepartment extends User
{
	private Scanner sc = new Scanner(System.in);
	private String option = "";
	private SessionManager sm = new SessionManager();

	private ArrayList<Course> clist;
	private ArrayList<User> ulist;

	private ArrayList<String> instructors; // list of instructors that are available to teach a course. (<5 teachings at the moment)
	private ArrayList<String> teaching;
	private ArrayList<String> enrolled;

	
	public HeadDepartment(String id) 
	{
		run(id);
	}
	
/*	// for user creation
	public HeadDepartment (String id, String privilege, String fn, String ln)
	{
		this.id = id;
		this.privilege = privilege;
		this.firstname = fn;
		this.lastname = ln;
	}
*/

	// for user creation
	public HeadDepartment (String id, String privilege, String name)
	{
		this.id = id;
		this.privilege = privilege;
		this.name = name;
	}

	/* funtions to implement
	 * TODO
	 * 0. show course (ok)
	 * 1. add course (ok)
	 * 2. remove course (ok)
	 * 3. assign instructor (ok)
	 * 4. remove instructor ()
	 */
	
	public void run (String id) 
	{
		clist = sm.getCourseList("courselist.json");
		ulist = sm.getUserList("userlist.json");
		
		while (true)
		{
			System.out.println("0. show courses");
			System.out.println("1. add course");
			System.out.println("2. remove course");
			System.out.println("3. assign instructor to course");
			System.out.println("4. remove instructor from course");
			System.out.println("q. quit");

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
					remove_course();
					break;
				case "3":
					assign_instructor();
					break;
				case "4":
					remove_instructor();
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
	// to add user specified course to the system, does some basic checks
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
	
			// course creation
			System.out.println("enter course name");
			course_name = sc.nextLine();

			System.out.println("enter course code");
			course_code = sc.nextLine();
				
			System.out.println("instructor list:");
			show_instructors(ulist);			
			System.out.println("enter instructor, leave blank if no instructors available");
			instructor = sc.nextLine();

			// check if instructor exists in the system just in case
			if (!instructor_exists(instructor))
			{
				System.out.println("such instructor does not exists, leaving instructor TBD");
				instructor = "";
			}
			
			System.out.println("enter session");
			session = sc.nextLine();
	
			System.out.println("enter section");
			section = sc.nextLine();
	
			// check if user specified course already exists in the system
			c = new Course(course_code, course_name, instructor, session, section);
			if (!course_exists(c, clist)) 
				clist.add(c);
	
			// if instructor is there, update him as well.
			if (!instructor.equals("")) 
			{
				for (User u : ulist)
				{
					if (u.getName().equals(instructor) && u.getPrivilege().equals("2"))
						u.getTeaching2().add(course_name);
				}	
			}
			// update DB
			sm.updateDB(clist, ulist);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	public boolean instructor_exists (String i) 
	{
		boolean exists = false;
		for (String instructor : instructors)
		{
			if (instructor.equals(i)) 
			{
				exists = true;
				break;				
			}
		}			
		return exists;
	}
	// compares and checks if given course already exists in given course_list (in the db)
	public boolean course_exists(Course c, ArrayList<Course> course_list) 
	{
		/* TODO
		 * if course name is the same, check for session
		 */
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

	// prompts user to remove a course // seems to work for now
	public void remove_course() 
	{
		// remove course from the system
		// if an instructor is teaching it, also remove it from the teaching.
		ArrayList<Course> to_remove = new ArrayList<Course>();
		ArrayList<String> rm_teaching = new ArrayList<String>();

		try 
		{
			String rm_course = "";
			ArrayList<String> students = new ArrayList<String>();

			show_courses();
			// actual deletion			
			System.out.println("enter the name of the course you would like to remove");
			rm_course = sc.nextLine();

			for (Course c : clist)
			{
				if (c.getCoursename().equals(rm_course)) {
					to_remove.add(c);
					break;
				}
			}

			clist.removeAll(to_remove);

			for (User u : ulist)
			{
				if (u.getPrivilege().equals("2"))
				{
					for (String cs : u.getTeaching2())
					{
						if (cs.equals(rm_course))
						{
							rm_teaching.add(cs);
							break;
						}	
					}
					u.getTeaching2().removeAll(rm_teaching);
					break;
				}
			}
			
			// this takes too long, will need to be optimized
			for (User u : ulist)
			{
				if (u.getPrivilege().equals("3"))
				{
					for (String c : u.getEnrolled2())
					{
						if (c.equals(rm_course))
						{
							rm_teaching.add(c);
						}	
					}
					u.getEnrolled2().removeAll(rm_teaching);
				}
			}
			// update db
			sm.updateDB(clist, ulist);			
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
		try 
		{
			String course_to_update = "";
			String instructor_name = "";		

			System.out.println("currently, these course(s) needs an instructor: ");
			instructor_needed(clist);
			System.out.println("these instructors are available to teach: ");
			show_instructors(ulist);
			
			System.out.println("choose a course to assign instructor");
			course_to_update = sc.nextLine();
			System.out.println("enter instructor name");
			instructor_name = sc.nextLine();
			
			for (Course c : clist)
			{
				if (c.getCoursename().equals(course_to_update)) 
				{
					c.setInstructor(instructor_name);
					
					for (User u : ulist)
					{
						if (u.getName().equals(instructor_name) && u.getPrivilege().equals("2"))
							u.getTeaching2().add(c.getCoursename());
					}						
				}
			}			
			sm.updateDB(clist, ulist);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void remove_instructor() 
	{
		// this function only removes an instructor
		try 
		{
			// getting courses with instructor
			String temp = "";
			String uname = "";
			
			for (Course c: clist)
			{
				if (!c.getInstructor().equals("")) 
					System.out.println(c.getCoursename() + ": " + c.getInstructor());											
			}
	
			System.out.println("choose the course you want to remove instructor from");
			temp = sc.nextLine();

			// update course
			for (Course c: clist)
			{
				if (temp.equals(c.getCoursename()))
				{				
					uname = c.getInstructor();
					c.setInstructor("");				
				}	
			}
			
			// update instructor
			for (User u: ulist)
			{
				if (u.getName().equals(uname) && u.getPrivilege().equals("2"))
				{
					ArrayList<String> t = u.getTeaching2();
					ArrayList<String> t_rm = new ArrayList<String> ();
					for (String c : t)
					{
						if (c.equals(temp))
						{
							t_rm.add(c);
							break;
						}	
					}
					t.remove(t_rm);
				}	
			}			
			sm.updateDB(clist, ulist);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	// show available instructors
	public void show_instructors(ArrayList<User> us) 
	{
		if (us.size() == 0)
		{
			System.out.println("there are currently no instructors available");
			return;
		}	
		else 
		{
			instructors = new ArrayList<String>();
			for (User u : us)
			{
				if (u.getPrivilege().equals("2"))
				{
					if (u.getTeaching2().size() < 5) 
					{
						System.out.println(u.getName());
						instructors.add(u.getName());
					} 
				}
			}
			if (instructors.size() == 0)
				System.out.println("there are currently no instructors available");
		}
	}
	
	// this function is to show which course has "" as instructor, and is in needs of an instructor
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