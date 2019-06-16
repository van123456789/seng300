package seng300;

import java.io.File;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SessionManager 
{
	private ObjectMapper mapper;

	public SessionManager()
	{
		this.mapper = new ObjectMapper();
	}
	
	public ArrayList<Course> getCourseList(String src) 
	{
		ArrayList<Course> clist = new ArrayList<Course>();		
		try 
		{
			File temp = new File(src);
			if (temp.createNewFile()) 
				System.out.println("no courses exists in the system yet");
			else 
				clist = mapper.readValue(temp,  new TypeReference<ArrayList<Course>>() {});
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return clist;
	}
	
	public ArrayList<User> getUserList(String src) 
	{
		ArrayList<User> ulist = new ArrayList<User>();
		try 
		{
			File temp = new File(src);
			if (temp.createNewFile()) 
				System.out.println("no courses exists in the system yet");
			else 
				ulist = mapper.readValue(temp,  new TypeReference<ArrayList<User>>() {});
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}		
		return ulist;
	}
		
	/* for next three methods, 
	 * can add check privilege of the user that is calling this function, 
	 * however these calls are tied to the session
	 * hence there is no need for it at the moment
	 */ 
	public void updateCourseDB (ArrayList<Course> cl)
	{
		try 
		{
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File("courselist.json"), cl);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void updateUserDB(ArrayList<User> ul)
	{		
		try 
		{
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File("userlist.json"), ul);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void updateDB(ArrayList<Course> c, ArrayList<User> u)
	{
		updateCourseDB(c);
		updateUserDB(u);
	}

}
