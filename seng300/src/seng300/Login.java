package seng300;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Login 
{	
	Scanner sc = new Scanner(System.in);
	ObjectMapper objmapper = new ObjectMapper();
	ArrayList<User> userlist = new ArrayList<User>();

	public Login() 
	{
		run();
	}

	void run ()
	{
		// get the userlist for authentication
		try 
		{
			File temp = new File("userlist.json");
			if (temp.createNewFile()) 
				System.out.println("no user exists in the system yet");
			else 
				userlist = objmapper.readValue(temp,  new TypeReference<ArrayList<User>>() {});

			// simple authentication using username
			// TODO implement password authentication
			System.out.print("username: ");
			String id = sc.nextLine();			
			for (User u : userlist)
			{
//				System.out.println(u.getId().equals(id));
				if (u.getId().equals(id))
				{
					if (u.getPrivilege().equals("1"))
						new HeadDepartment(id);	// create a new session for the department head level user
					
					if (u.getPrivilege().equals("2"))
						//new Instructor(id);		// create a new session for the instructor level user
					
					if (u.getPrivilege().equals("3"))
						new Student(id);			// create a new session for the student level user
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}				
	}
	
}
