package seng300;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Registration 
{
	Scanner sc = new Scanner(System.in);
	
	public Registration()
	{
		run();
	}

	void run() 
	{
		String option = "";

		Main: while (true)
		{
            System.out.println("**** registration ****");
            System.out.println("1. register as dept head");
            System.out.println("2. register as instructor");
            System.out.println("3. register as student");
            System.out.println("q. quit");
            System.out.println("**********************");

            option = sc.nextLine();
            switch(option)
            {
                case "1":
                	create_user(option);
                    break;

                case "2":
                	create_user(option);
                	break;

                case "3":
                	create_user(option);
                	break;
                	
                case "q":
                	break Main;
            }
		}	
	}
	private void create_user (String privilege) 
	{
		// get userlist.json
		ObjectMapper objmapper = new ObjectMapper();
		ArrayList<User> ulist = new ArrayList<User>();
		
		String id = "";
//		String fn = "";
//		String ln = "";
		String name = "";

		// file generation
		try 
		{
			File temp = new File("userlist.json");
			if (temp.createNewFile()) 
				System.out.println("no users exists in the system yet");
			else 
				ulist = objmapper.readValue(temp,  new TypeReference<ArrayList<User>>() {});
			
/*			
			// prompt user to create an id
			System.out.println("id: ");
			id = sc.nextLine();
			System.out.println("firstname: ");
			fn = sc.nextLine();
			System.out.println("lastname: ");
			ln = sc.nextLine();
*/
			// prompt user to create an id
			System.out.println("id: ");
			id = sc.nextLine();
			System.out.println("name: ");
			name = sc.nextLine();
			
			if (privilege.equals("1"))
			{
				HeadDepartment h = new HeadDepartment(id, privilege, name);
				ulist.add((User) h);
				objmapper.writerWithDefaultPrettyPrinter().writeValue(temp, ulist);
			}	
			if (privilege.equals("2"))
			{
				Instructor i = new Instructor(id, privilege, name);
				ulist.add((User) i);
				objmapper.writerWithDefaultPrettyPrinter().writeValue(temp, ulist);
				
			}	
			if (privilege.equals("3"))
			{
				Student s = new Student(id, privilege, name);
				ulist.add((User) s);
				objmapper.writerWithDefaultPrettyPrinter().writeValue(temp, ulist);				
			}	
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}		
	}
}
