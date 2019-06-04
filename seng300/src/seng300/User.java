package seng300;

import java.util.ArrayList;

public class User 
{
	protected String id;
	protected String privilege;
	protected String firstname;
	protected String lastname;
	protected ArrayList<Course> teaching;
	protected ArrayList<Course> enrolled;
	

	
	public ArrayList<Course> getTeaching() {
		return teaching;
	}

	public void setTeaching(ArrayList<Course> teaching) {
		this.teaching = teaching;
	}

	public ArrayList<Course> getEnrolled() {
		return enrolled;
	}

	public void setEnrolled(ArrayList<Course> enrolled) {
		this.enrolled = enrolled;
	}

	public User()
	{
		
	}
	
	public User (String id, String privilege, String fn, String ln)
	{
		this.id = id;
		this.privilege = privilege;
		this.firstname = fn;
		this.lastname = ln;
	}

	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getId() 
	{
		return id;
	}
	
	// get/setters
	// get first name
	public String getFn() 
	{
		return firstname;
	}

	// set first name
	public void setFn(String fn) 
	{
		this.firstname = fn;
	}

	// get last name
	public String getLn() 
	{
		return lastname;
	}

	// set last name
	public void setLn(String ln) 
	{
		this.lastname = ln;
	}

	// get privilege
	public String getPrivilege() {
		return privilege;
	}

	// set privilege
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	
	
	
}
