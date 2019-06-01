package seng300;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
		System.out.println("1 - to register courses for this session");
		System.out.println("2 - to register courses for future session");
		System.out.println("3 - to drop courses for this session");
		System.out.println("4 - to drop courses for future session");
		
		Scanner myScanner = new Scanner(System.in);
		String user_choice = "";
		while (true) {
			user_choice = myScanner.nextLine();
			if (user_choice.equals("1")) {
				register_courses_for_term(EnvironmentConstant.getSession());
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
	
	public void register_courses_for_term(String aSession) {
		List<Course> courses_to_enrol = new ArrayList<>();
		System.out.println("These are courses you signed up" );
		print_course_load(aSession);
		register_courses(courses_to_enrol, aSession);
		print_course_load(aSession);
		
		
	}
	
	public  void register_courses(List<Course> courses_to_enrol, String aSession) {
		Scanner myScanner = new Scanner(System.in);
		boolean notFinishedAdding = true;
		int state = 0;
		while (notFinishedAdding) {
			if (state == 0) {
				try {
					System.out.println("Please enter the course you want to register:");
					System.out.println("Type exit to stop adding courses");
					String input = myScanner.nextLine();
					
					if (input.equals("exit")) {
						state = 1;
					}
					else {
						Course newCourse = new Course(input, aSession);
						if (newCourse.getCourseName()!="") {
							courses_to_enrol.add(newCourse);
							System.out.println("Current courses to register:");
							System.out.println(courses_to_enrol);
						}
						else {
							System.out.println("Course is invalid or not offered this session");
						}
					}
					
					
				}
				catch (Exception e){
					System.out.println("Should not see me");
				}
				
			}
			else {
				System.out.println("These are courses you are registering:");
				System.out.println(courses_to_enrol);
				System.out.println("Type ok to confirm");
				System.out.println("Type remove to remove specific course");
				String input = myScanner.nextLine();
				if (input.equals("ok")) {
					add_to_database(courses_to_enrol);
					notFinishedAdding = false;
				}
				if (input.equals("remove")) {
					System.out.println("Please enter the index for course you want to remove");
					try {
						String index = myScanner.nextLine();
						if (0 <= Integer.parseInt(index) && Integer.parseInt(index) < courses_to_enrol.size()) {
							courses_to_enrol.remove(Integer.parseInt(index));
							System.out.println("Course removed");
							state = 0;
						}
						else {
							System.out.println("Out of boundary");
						}
					}
					catch (Exception e) {
						System.out.println("Invalid input");
					}
				}
			}
			
		}

		
		
		
	}
	
	public void register_future_term() {
		Scanner myScanner = new Scanner(System.in);
		System.out.println("Please enter term you want to register in:");
		String aSession = myScanner.nextLine();
		if (Course.isValidSession(aSession)) {
			register_courses_for_term(aSession);
		}
	}
	
	public void drop_current_term() {
		
	}
	
	public void drop_past_term() {
		
	}
	
	public void print_course_load(String aSession) {
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
				if (aCourse.getSession().equals(aSession)) {
					courses.add(aCourse.getCourseName());
				}
				
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
			System.out.println("Error in Course class");
			
		}
	}
	
	
	public void add_to_database(List<Course> courses_to_enrol) {
		try {
			File file = new File("student_courseload.data");
			FileWriter fr = new FileWriter(file, true);
			BufferedWriter br = new BufferedWriter(fr);
			PrintWriter pr = new PrintWriter(br);
			for (Course c : courses_to_enrol) {
				pr.println(super.getID() + "-" + c.getCourseID());
			}
			pr.close();
			br.close();
			fr.close();
			System.out.println("Registered successfully");
		} 
		catch (FileNotFoundException e) {
			System.out.println("Cannot find file stuent_courseload.data");
		} 
		catch (IOException e) {
			System.out.println("IO error in add_to_database");
		}
	}
	
	
	
	public static void main(String[] args) {
		Student aStudent = new Student("1351891440");
		aStudent.run();
	}

}
