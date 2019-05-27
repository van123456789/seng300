package seng300;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Course {
	private String coursename;
	private String instructor;
	private String session;
	private String section;
	private BufferedReader course_reader;
	
	public Course(String coursename, String instructor, String session, String section) {
		this.coursename = coursename;
		this.instructor = instructor;
		this.session = session;
		this.section = section;
		
	}
	
	public Course(String course_id) {


		
		
		while (true) {
			try {
				String aLine;
				FileReader course = new FileReader("courselist.data");
				course_reader = new BufferedReader(course);
				aLine = course_reader.readLine();
				
				if (aLine == null) {
					System.out.println("Cannot find the course");
				}
				else {
					String[] temp = aLine.split(".");
					System.out.println(temp.length);
					if (temp[0].equals(course_id)) {
						this.coursename = temp[1];
						this.instructor = temp[2];
						this.session = temp[3];
						this.section = temp[4];
					}
				}
			} 
			catch (NullPointerException e) {
				System.out.println("aaa");
			} catch (IOException e) {
				System.out.println("bbb");
			}
			
		}

	}
	
	public String getCourseName() {
		return this.coursename;
	}
	
}