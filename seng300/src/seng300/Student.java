package seng300;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Student extends User{

	public Student(String id) {
		super(id);
	}
	
	public void run() {
		System.out.println("Please select your option:");
		System.out.println("1 - to view available courses for this session");
		System.out.println("2 - to view available courses for future session");
		System.out.println("3 - to drop courses for this session");
		System.out.println("4 - to drop courses for future session");
		
		Scanner myScanner = new Scanner(System.in);
		String user_choice = "";
		while (true) {
			user_choice = myScanner.nextLine();
			if (user_choice.equals("1")) {
				register_current_term();
				break;
			}
			if (user_choice.equals("2")) {
				register_future_term();
				break;
			}
			if (user_choice.equals("3")) {
				drop_current_term();
				break;
			}
			if (user_choice.equals("4")) {
				drop_past_term();
				break;
			}
			
			else {
				System.out.println("Incorrect response");
			}
			
		}
	}
	
	public void register_current_term() {
		List<Course> courses_to_enrol = new ArrayList<>();
		System.out.println("These are courses you signed up for the current term");
		print_current_load();
		System.out.println("Please enter the course you want to enrol in this semester");
		System.out.println("Type exit to finish enroling");
	
		register_courses(courses_to_enrol);
		System.out.println(courses_to_enrol.size());
		System.out.println(courses_to_enrol);
	}
	
	public  void register_courses(List<Course> courses_to_enrol) {
		Scanner myScanner = new Scanner(System.in);
		while (true) {
			String course = myScanner.nextLine();
			if (course.equals("exit")) {
				break;
			}
			else {
				try {
					Course newCourse = new Course(course, EnvironmentConstant.getSession());
					courses_to_enrol.add(newCourse);

					System.out.println("Courses to enroll:");
					System.out.println(courses_to_enrol);
					
				} 
				catch (Exception e) {
					System.out.println("Error in enroling");
				}
			}
			
		}
		
		
	}
	
	public void register_future_term() {
		
	}
	
	public void drop_current_term() {
		
	}
	
	public void drop_past_term() {
		
	}
	
	public void print_current_load() {
		String temp;
		try {
			FileReader courseload = new FileReader("student_courseload.data");
			BufferedReader courseload_reader = new BufferedReader(courseload);
			
			
			List<String> courses_in_code = new ArrayList<>();
			while ((temp = courseload_reader.readLine()) != null) {
				String[] splitedLine = temp.split("-");
				if (splitedLine[0].equals(super.getID())) {
					courses_in_code.add(splitedLine[1]);
				}
			}
			

			
			
			List<String> courses = new ArrayList<>();
			
			for (String course : courses_in_code) {
				Course aCourse = new Course(course);
				String coursename = aCourse.getCourseName();
				courses.add(coursename);
				
			}
			System.out.println(courses);
			
			courseload_reader.close();
			courseload.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("CourseLoad File not found");
		}
		catch (IOException e) {
			System.out.println("IO error");
		} catch (Exception e) {
			System.out.print("Error in Course class");
			
		}
	}
	
	
	public static void main(String[] args) {
		Student aStudent = new Student("1351891440");
		aStudent.run();
	}

}
