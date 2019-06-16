package seng300;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.core.type.TypeReference;

// Session for head department user
public class HeadDepartment extends User
{
	private Scanner sc = new Scanner(System.in);
	private String option = "";
	private SessionManager sm = new SessionManager();

	private ArrayList<Course> clist;
	private ArrayList<User> ulist;

	private ArrayList<String> instructors; // list of instructors that are available to teach a course. (<5 teachings at the moment)
	
	public HeadDepartment(String id) 
	{
		run(id);
	}
	
	// for user creation
	public HeadDepartment (String id, String password, String privilege, String name)
	{
		this.id = id;
		this.password = password;
		this.privilege = privilege;
		this.name = name;
	}	
	
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
			System.out.println("5. show requests");
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
				case "5":
					show_request();
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

			while (session.equals(""))
			{
				System.out.println("session cannot be left blank");
				session = sc.nextLine();
			}	

			System.out.println("enter section");
			section = sc.nextLine();

			while (session.equals(""))
			{
				System.out.println("section cannot be left blank");
				session = sc.nextLine();
			}	
	
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
						u.getTeaching().add(c);
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
		ArrayList<Course> rm_teaching = new ArrayList<Course>();

		try 
		{
			String rm_course = "";

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
					for (Course cs : u.getTeaching())
					{
						if (cs.getCoursename().equals(rm_course))
						{
							rm_teaching.add(cs);
							break;
						}	
					}
					u.getTeaching().removeAll(rm_teaching);
					break;
				}
			}
			
			// this takes too long, will need to be optimized
			for (User u : ulist)
			{
				if (u.getPrivilege().equals("3"))
				{
					for (Course c : u.getEnrolled())
					{
						if (c.getCoursename().equals(rm_course))
						{
							rm_teaching.add(c);
						}	
					}
					u.getEnrolled().removeAll(rm_teaching);
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
	public void assign_instructor() 
	{
		try 
		{
			String course_to_update = "";
			String instructor_name = "";		
						
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
						{
							u.getTeaching().add(c);
						}
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

	// removes instructor from course
	public void remove_instructor() 
	{
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
					ArrayList<Course> t = u.getTeaching();
					ArrayList<Course> t_rm = new ArrayList<Course> ();
					for (Course c : t)
					{
						if (c.getCoursename().equals(temp))
						{
							t_rm.add(c);
						}	
					}
					t.removeAll(t_rm);
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
					if (u.getTeaching().size() < 5) 
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
	
	public void show_request()
	{
		try 
		{
			Object obj = new JSONParser().parse(new FileReader("requesting.json"));
			JSONArray ja = (JSONArray) obj;
			
			Object courses = new JSONParser().parse(new FileReader("courselist.json"));
			JSONArray cs = (JSONArray) courses;
			
			Object users = new JSONParser().parse(new FileReader("userlist.json"));
			JSONArray us = (JSONArray) users;
			
			for (int i = 0; i < ja.size(); i++)
			{
				System.out.println("request " + i + ":");
				JSONObject req = (JSONObject) ja.get(i);
				System.out.println(req);
				String choice = ""; 
				while (!(choice.equals("1") | choice.equals("2")))
				{
					System.out.println("accept request " + i + ": 1");
					System.out.println("decline request " + i + ": 2");
					choice = sc.nextLine();
				}	
				
				if (choice.equals("1"))
				{
					String cid = (String) req.get("course_id");
					String iid = (String) req.get("instructor");
					String status = (String) req.get("status");
					for (Course c : clist)
					{
						if (c.getCourse_id().equals(cid)) 
						{
							for (User u : ulist)
							{
								ArrayList<Course> ucs = u.getTeaching();
								if (u.getId().equals(iid))
								{
									c.setInstructor(u.getName());
									ucs.add(c);
									break;
								}
								ucs = null;
							}
						}
					}
					sm.updateDB(clist, ulist);
					req.replace("status", "accepted");
				}
				else 
				{
					req.replace("status", "declined");
				}				
			}				
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}				
	}
}