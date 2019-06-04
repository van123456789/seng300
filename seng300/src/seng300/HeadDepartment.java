package seng300;

import java.util.Scanner;

public class HeadDepartment extends User{

	public HeadDepartment(String id) {
		super(id);
	}
	
	public void run() {
		System.out.println("Please select your options");
		System.out.println("Type 1 to add course");
		System.out.println("Type 2 to edit course");
		System.out.println("Type 3 assign instructor");
		
		Scanner myScanner = new Scanner(System.in);
		
		String option = myScanner.nextLine();
		
		if (option.contentEquals("1")) {
			
		}
		
		if (option.contentEquals("2")) {
			
		}
		
		if (option.contentEquals("3")) {
			
		}
		
		else {
			
		}
	}
	
	public static void main(String[] args) {
		HeadDepartment head = new HeadDepartment("2796959577");
	}

}
