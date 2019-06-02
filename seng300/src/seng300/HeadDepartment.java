package seng300;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class HeadDepartment extends User{
	static Scanner sc;
	static int option;
	
	public HeadDepartment(String id) {
		super(id);
		run();
	}

	public static void run () 
	{
		sc = new Scanner(System.in);

		System.out.println("0. show courses");
		System.out.println("1. add course");
		System.out.println("2. assign instructor");
		System.out.println("3. quit");

		while (true)
		{
			option = sc.nextInt();
			
			switch(option) 
			{
				case 0:
					show_courses();
					break;
				case 1:
					add_course();
					break;
				case 2:
					break;
				case 3:
					System.exit(0);
					break;
			}
		}	
	}

	public static void show_courses()
	{
		
	}
	public static void add_course()
	{
		String course_code = "";
		String course_name = ""; 
		String instructor = ""; 
		String session = ""; 
		String section = "";
		
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
				
		Course c = new Course(course_code, course_name, instructor, session, section);

		// check if courselist file is present
		File temp = new File("courselist.json");
		try 
		{
			ObjectMapper objmapper = new ObjectMapper();
			SimpleModule module = new SimpleModule();
			
			ArrayList<Course> course_list = new ArrayList<Course>();
			
			if (temp.createNewFile())
			{
				System.out.println("file is created");
				System.out.println("adding course..");
				course_list.add(c);
				objmapper.writerWithDefaultPrettyPrinter().writeValue(temp, course_list);
			}	
			else 
			{
				// get the json file, and parse it, show what courses are already in the system
				course_list = objmapper.readValue(temp, ArrayList.class);
				course_list.add(c);
				objmapper.writerWithDefaultPrettyPrinter().writeValue(temp, course_list);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}		
	}
	
	public static void main(String [] args) 
	{
		run();
	}
}
