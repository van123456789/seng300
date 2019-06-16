package seng300;

public class Request 
{
	private String name;
	private String coursename;

	public Request(User u)
	{
		this.name = "";
		this.coursename = "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCoursename() {
		return coursename;
	}

	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}	
}
