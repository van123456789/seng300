package seng300;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Course {
	private String course_id = "";
	private String coursename = "";
	private String instructor = "";
	private String session = "";
	private String section = "";
	private BufferedReader course_reader;
	
	public Course(String course_id, String coursename, String instructor, String session, String section) {
		this.course_id = course_id;
		this.coursename = coursename;
		this.instructor = instructor;
		this.session = session;
		this.section = section;
		
	}
	
	public Course(String course_id) throws Exception {
		this.course_id = course_id;
		String aLine;
		FileReader course = new FileReader("courselist.data");
		course_reader = new BufferedReader(course);
		while ((aLine = course_reader.readLine()) != null) {
			String[] temp = aLine.split("-");
			if (temp[0].equals(course_id)) {
				this.coursename = temp[1];
				this.instructor = temp[2];
				this.session = temp[3];
				this.section = temp[4];
			}
		}
		course_reader.close();
		course.close();
	}
	
	public Course(String coursename, String session) throws Exception {
		String aLine;
		FileReader course = new FileReader("courselist.data");
		course_reader = new BufferedReader(course);
		while ((aLine = course_reader.readLine()) != null) {
			String[] temp = aLine.split("-");
			if (temp[1].equals(coursename) && temp[3].equals(session)) {
				this.course_id = temp[0];
				this.coursename = temp[1];
				this.instructor = temp[2];
				this.session = temp[3];
				this.section = temp[4];
			}
		}
		course_reader.close();
		course.close();
	}
	
	public String toString() {
		return this.coursename;
	}
	
	public String getCourseName() {
		return this.coursename;
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
	public String getCourseID() {
		return this.course_id;
	}
	
	public static void main(String[] args) throws Exception {
		
	}
	
}
