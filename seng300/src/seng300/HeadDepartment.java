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

public class HeadDepartment extends User{
	static Scanner sc;
	static String option = "";
	
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
			option = sc.nextLine();
			
			switch(option) 
			{
				case "0":
					show_courses();
					break;
				case "1":
					add_course();
					break;
				case "2":
					break;
				case "3":
					System.exit(0);
					break;
				default:
					System.out.println("choose a valid option");
					break;
			}
		}	
	}

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

		ObjectMapper objmapper = new ObjectMapper();
		ArrayList<Course> course_list = new ArrayList<Course>();

		// check for courselist.json
		File temp = new File("courselist.json");
		try 
		{			
			if (temp.createNewFile())
			{
				System.out.println("file is created");
				System.out.println("adding course..");
				course_list.add(c);
				objmapper.writerWithDefaultPrettyPrinter().writeValue(temp, course_list);
			}	
			else 
			{
				// courselist.json is already present in the system
				// create the course_list
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
