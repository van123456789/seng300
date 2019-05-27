package seng300;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class id_checker {
	private String id;
	private int id_type = -1;
	public id_checker(String combination) {
		id = combination;
	}
	
	private void check() {
		boolean isStudent = checkStudent();
		boolean isInstructor = checkInstructor();
		boolean isHead = checkHead();
		
		if (isStudent) {
			id_type = 1;
		}
		if (isInstructor) {
			id_type = 2;
		}
		if (isHead) {
			id_type = 3;
		}
		
	}
	
	private boolean checkStudent() {
		boolean toReturn = false;
		try {
			FileReader headdep = new FileReader("headdepartment.data");
			BufferedReader headdep_reader = new BufferedReader(headdep);
			
			String temp;
			while ((temp = headdep_reader.readLine()) != null) {
				if (temp.equals(id)){
					toReturn = true;
				}
			}
			
			headdep_reader.close();
			headdep.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Head Department File not found");
		}
		catch (IOException e) {
			System.out.println("IO error");
		}
		return toReturn;
	}
	
	private boolean checkInstructor() {
		boolean toReturn = false;
		try {
			FileReader instructor = new FileReader("instructor.data");
			BufferedReader instructor_reader = new BufferedReader(instructor);
			
			String temp;
			while ((temp = instructor_reader.readLine()) != null) {
				if (temp.equals(id)){
					toReturn = true;
				}
			}
			
			instructor_reader.close();
			instructor.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Instructor File not found");
		}
		catch (IOException e) {
			System.out.println("IO error");
		}
		return toReturn;
	}
	
	private boolean checkHead() {
		boolean toReturn = false;
		try {
			FileReader student = new FileReader("student.data");
			BufferedReader student_reader = new BufferedReader(student);
			
			String temp;
			while ((temp = student_reader.readLine()) != null) {
				if (temp.equals(id)){
					toReturn = true;
				}
			}
			student_reader.close();
			student.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Student File not found");
		}
		catch (IOException e) {
			System.out.println("IO error");
		}
		return toReturn;
	}
	
	public int authenticate() {
		this.check();
		return id_type;
	}

}
