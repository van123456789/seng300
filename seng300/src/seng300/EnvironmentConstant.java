package seng300;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class EnvironmentConstant {
	
	
	public static String getSession() {
		String aLine = "Error in getSession";
		FileReader environment = null;
		try {
			environment = new FileReader("environment.data");
			BufferedReader environment_reader = new BufferedReader(environment);
			aLine = environment_reader.readLine();
			environment_reader.close();
			environment.close();
		} 
		catch (FileNotFoundException e) {
			System.out.println("Cannot find file environment.data");
		} 
		catch (IOException e) {
			System.out.println("Cannot find file environment.data");
		}
		
		return aLine;
	}
	public static void setYear(String aSession) {
		try {
			PrintWriter pw = new PrintWriter("environment.data");
			pw.print(aSession);
			pw.flush();
			pw.close();
		} 
		catch (FileNotFoundException e) {
			System.out.println("Cannot find file environment.data");
		}
		
	}
}
