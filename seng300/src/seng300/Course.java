package seng300;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Course {
	protected String course_id = "";
	protected String coursename = "";
	protected String instructor = "";
	protected String session = "";
	protected String section = "";
	protected ArrayList<Student> enrolled_student;
	protected ArrayList<String> s_name;	

	public Course() 
	{
		
	}
	
	public Course(String course_id, String coursename, String instructor, String session, String section) 
	{
		this.course_id = course_id;
		this.coursename = coursename;
		this.instructor = instructor;
		this.session = session;
		this.section = section;
		this.enrolled_student = new ArrayList<Student>();
		this.s_name = new ArrayList<String>();
	}
	
	// getter, setters
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	public String getCoursename() {
		return coursename;
	}
	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}
	public ArrayList<Student> getEnrolled_student() {
		return enrolled_student;
	}
	public void setEnrolled_student(ArrayList<Student> enrolled_student) {
		this.enrolled_student = enrolled_student;
	}
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getInstructor() {
		return this.instructor;
	}
	public String getSession() {
		return this.session;
	}
	public String getSection() {
		return this.section;
	}	
	
	public ArrayList<String> getS_name() {
		return s_name;
	}

	public void setS_name(ArrayList<String> s_name) {
		this.s_name = s_name;
	}


}