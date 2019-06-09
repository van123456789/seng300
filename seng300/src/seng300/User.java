package seng300;

import java.util.ArrayList;

public class User 
{
	protected String id;
	protected String privilege;
	protected ArrayList<Course> teaching;
	protected ArrayList<Course> enrolled;

	// trying to make json file lighter
	protected ArrayList<String> teaching2;
	protected ArrayList<String> enrolled2;
	protected String name;

	public User()
	{
		
	}
	
	public User (String id, String privilege, String name)
	{
		this.id = id;
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

	public void setName(String n)
	{
		this.name = n;
	}
	
	public String getName() 
	{
		return name;
	}

	
	// get/setters

	// get privilege
	public String getPrivilege() {
		return privilege;
	}
	// set privilege
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

	public void setEnrolled(ArrayList<Course> enrolled) {
		this.enrolled = enrolled;
	}	

	
	// to make json file lighter
	public ArrayList<String> getTeaching2() {
		return teaching2;
	}

	public void setTeaching2(ArrayList<String> teaching2) {
		this.teaching2 = teaching2;
	}

	public ArrayList<String> getEnrolled2() {
		return enrolled2;
	}

	public void setEnrolled2(ArrayList<String> enrolled2) {
		this.enrolled2 = enrolled2;
	}

	
}
