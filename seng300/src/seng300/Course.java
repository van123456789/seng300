package seng300;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Course {
	private String course_id = "";
	private String coursename = "";
	private String instructor = "";
	private String session = "";
	private String section = "";
	private List<Student> enrolled_student;
		
	public Course() {};
	
	public Course(String course_id, String coursename, String instructor, String session, String section) 
	{
		this.course_id = course_id;
		this.coursename = coursename;
		this.instructor = instructor;
		this.session = session;
		this.section = section;
		this.enrolled_student = null;
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
	public List<Student> getEnrolled_student() {
		return enrolled_student;
	}
	public void setEnrolled_student(List<Student> enrolled_student) {
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

	
	public static void main(String[] args) throws Exception {
	}
	
}