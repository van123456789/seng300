package seng300;

import java.util.ArrayList;

public class User 
{
	protected String id;
	protected String password;
	protected String privilege;
	protected String name;
	
	protected ArrayList<Course> teaching;
	protected ArrayList<Course> enrolled;

	public User()
	{
		
	}
	
	public User (String id, String password, String privilege, String name)
	{
		this.id = id;
		this.password = password;
		this.privilege = privilege;
		this.name = name;
	}

	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getId() 
	{
		return id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String n)
	{
		this.name = n;
	}
	
	public String getName() 
	{
		return name;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	
	public ArrayList<Course> getTeaching() {
		return teaching;
	}

	public void setTeaching(ArrayList<Course> teaching) {
		this.teaching = teaching;
	}

	public ArrayList<Course> getEnrolled() {
		return enrolled;
	}

	public void setEnrolled2(ArrayList<Course> enrolled) {
		this.enrolled = enrolled;
	}

	
}
