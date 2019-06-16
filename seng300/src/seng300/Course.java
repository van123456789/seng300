package seng300;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Course 
{
	protected String course_id = "";
	protected String coursename = "";
	protected String instructor = "";
	protected String session = "";
	protected String section = "";
	protected ArrayList<String> enrolled_student;
		
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
		this.enrolled_student = new ArrayList<String>();
	}
	
	public Course(String id) 
	{
		course_id = id;
	}
	
	public Course(String course_name, String aSession) 
	{
		coursename = course_name;
		session = aSession;
	}
	
	public void fillout1() 
	{
		JSONParser jsonParser = new JSONParser();
        
        try (FileReader reader = new FileReader("courselist.json"))
        {
            Object obj = jsonParser.parse(reader);
            JSONArray courseList = (JSONArray) obj;
            
			JSONObject courseDetails=new JSONObject();
            for (int i = 0; i < courseList.size(); i++) 
            {
            	courseDetails = (JSONObject) courseList.get(i);
            	
            	String course_name = (String) courseDetails.get("coursename");
            	String aSession = (String) courseDetails.get("session");
            	if (coursename.equals(course_name) && session.equals(aSession)) 
            	{
            		String course_id = (String) courseDetails.get("course_id");
            		String section = (String) courseDetails.get("section");
            		String session = (String) courseDetails.get("session");
            		String instructor = (String) courseDetails.get("instructor");
            		ArrayList<String> enrolled_student = arrayConvert((String) courseDetails.get("enrolled_student"));
            		this.enrolled_student = enrolled_student;
            		this.course_id = course_id;
            		this.session = session;
            		this.section = section;
            		this.instructor = instructor;
            	}            	                        	
            } 
        } 
        catch (IOException | org.json.simple.parser.ParseException e) 
        {
            e.printStackTrace();
        } 
	}
	
	
	public void fillout2() 
	{
		JSONParser jsonParser = new JSONParser();
        
        try (FileReader reader = new FileReader("courselist.json"))
        {
            Object obj = jsonParser.parse(reader);
            JSONArray courseList = (JSONArray) obj;
            
			JSONObject courseDetails=new JSONObject();
            for (int i = 0; i < courseList.size(); i++) 
            {
            	courseDetails = (JSONObject) courseList.get(i);
            	
            	String id = (String) courseDetails.get("course_id");
            	
            	if (course_id.equals(id)) 
            	{
            		String course_name = (String) courseDetails.get("coursename");
            		String section = (String) courseDetails.get("section");
            		String session = (String) courseDetails.get("session");
            		String instructor = (String) courseDetails.get("instructor");
            		ArrayList<String> enrolled_student = arrayConvert((String) courseDetails.get("enrolled_student"));
            		this.enrolled_student = enrolled_student;
            		this.coursename = course_name;
            		this.session = session;
            		this.section = section;
            		this.instructor = instructor;
            	}            	
            } 
        } 
        catch (IOException | org.json.simple.parser.ParseException e) 
        {
            e.printStackTrace();
        } 
	}
	
	
	private static void parseCourse(JSONObject course)
    {
        
        JSONObject courseObject = (JSONObject) course.get("course");
                 
        String course_id = (String) courseObject.get("course_id");   
        System.out.println(course_id);
                 
        String coursename = (String) courseObject.get("coursename"); 
        System.out.println(coursename);
                 
        String instructor = (String) courseObject.get("instructor");   
        System.out.println(instructor);
        
        String session = (String) courseObject.get("session");   
        System.out.println(session);
        
        String section = (String) courseObject.get("section");   
        System.out.println(section);
        
        String enrolled_student = (String) courseObject.get("enrolled_student");   
        System.out.println(enrolled_student);      
    }
	
	
	public static ArrayList<String> arrayConvert(String aList)
	{
		ArrayList<String> result = new ArrayList<>();
		String newList = aList.substring(1, aList.length()-1);
		String[] student_ids = newList.split(",");
		for (String student_id : student_ids) 
			result.add(student_id.trim());

		return result;
	}
		
	public void print_details() 
	{
		System.out.println(this.getCourse_id());
		System.out.println(this.getCoursename());
		System.out.println(this.getInstructor());
		System.out.println(this.getSection());
		System.out.println(this.getSession());
		System.out.println(this.getEnrolled_student());
	}
	
	public void add_student(String id) 
	{
		JSONParser jsonParser = new JSONParser();
		ArrayList<String> result = new ArrayList<>();
        
        try (FileReader reader = new FileReader("courselist.json")) 
        {
            Object obj = jsonParser.parse(reader);
            JSONArray courseList = (JSONArray) obj;
            
			JSONObject courseDetails = new JSONObject();
            for (int i = 0; i < courseList.size(); i++) 
            {
            	courseDetails = (JSONObject) courseList.get(i);
            	
            	String course_id = (String) courseDetails.get("course_id");
            	
            	if (course_id.equals(this.getCourse_id())) 
            	{
            		ArrayList<String> enrolled_students = arrayConvert((String) courseDetails.get("enrolled_student"));
            		if (enrolled_students.get(0).length() == 0) 
            			enrolled_students.remove(0);

            		enrolled_students.add(id);
            		courseDetails.put("enrolled_student", enrolled_students.toString());            		
            	}	
            }
            
            FileWriter file = new FileWriter("courselist.json");
            file.write(courseList.toJSONString());
            file.flush();
        }
        catch (Exception e) 
        {
        	e.printStackTrace();
        }

	}
	
	private JSONArray arraytojson(ArrayList<String> list) 
	{
		JSONArray result = new JSONArray();
		for (String s : list) 
			result.add(s);
		return result;
	}
	
	private ArrayList<String> getEnrolled_from_database() 
	{
		JSONParser jsonParser = new JSONParser();
		ArrayList<String> result = new ArrayList<>();
        
        try (FileReader reader = new FileReader("courselist.json"))
        {
            Object obj = jsonParser.parse(reader);
            JSONArray courseList = (JSONArray) obj;
            
			JSONObject courseDetails=new JSONObject();
            for (int i = 0; i < courseList.size(); i++) 
            {
            	courseDetails = (JSONObject) courseList.get(i);
            	
            	String course_id = (String) courseDetails.get("course_id");
            	
            	if (course_id.equals(this.getCourse_id())) 
            	{
            		ArrayList<String> enrolled_student = arrayConvert((String) courseDetails.get("enrolled_student"));
            		result = enrolled_student;
            	}            	            	
            }
        }
        catch (Exception e) 
        {
        	System.out.println("Error in getenrolled_from_database");
        }
        return result;
	}
        

	
	public static boolean isValidSession(String aSession) 
	{
		JSONParser jsonParser = new JSONParser();
		boolean result = false;
        
        try (FileReader reader = new FileReader("courselist.json"))
        {
            Object obj = jsonParser.parse(reader);
            JSONArray courseList = (JSONArray) obj;
            
			JSONObject courseDetails=new JSONObject();
            for (int i = 0; i < courseList.size(); i++) 
            {	
            	courseDetails = (JSONObject) courseList.get(i);            	
            	String Session = (String) courseDetails.get("session");
            	if (aSession.equals(Session)) 
            	{
            		result = true;
            		break;
            	}
            } 
        } 
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        } 
        catch (org.json.simple.parser.ParseException e) 
        {
			e.printStackTrace();
		}
        return result;
	}

	public void remove_from_database(String id) 
	{
		JSONParser jsonParser = new JSONParser();
		ArrayList<String> result = new ArrayList<>();
        
        try (FileReader reader = new FileReader("courselist.json")) 
        {
            Object obj = jsonParser.parse(reader);
            JSONArray courseList = (JSONArray) obj;
            
			JSONObject courseDetails = new JSONObject();
            for (int i = 0; i < courseList.size(); i++) {
            	courseDetails = (JSONObject) courseList.get(i);            	
            	
            	String course_id = (String) courseDetails.get("course_id");
            	
            	if (course_id.equals(this.getCourse_id())) 
            	{
            		ArrayList<String> enrolled_students = arrayConvert((String) courseDetails.get("enrolled_student"));
            		enrolled_students.remove(id);
            		courseDetails.put("enrolled_student", enrolled_students.toString());            		
            	}	
            }
            
            FileWriter file = new FileWriter("courselist.json");
            file.write(courseList.toJSONString());
            file.flush();
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
	}
		
	public static void reform() 
	{
		JSONParser jsonParser = new JSONParser();
        
        try (FileReader reader = new FileReader("test.json")) 
        {
            Object obj = jsonParser.parse(reader);
            JSONArray courseList = (JSONArray) obj;
            JSONArray newcourseList = new JSONArray();
            
			JSONObject course = new JSONObject();
            for (int i = 0; i < courseList.size(); i++) 
            {
            	course = (JSONObject) courseList.get(i);
            	JSONObject courseDetails = (JSONObject) course.get("course");
            	JSONObject newcourse = new JSONObject();
            	
                String course_id = (String) courseDetails.get("course_id");
                String coursename = (String) courseDetails.get("coursename");
                String instructor = (String) courseDetails.get("instructor");
                String session = (String) courseDetails.get("session");
                String section = (String) courseDetails.get("section");
                String enrolled_student = (String) courseDetails.get("enrolled_student");
                
            	newcourse.put("course_id", course_id);
            	newcourse.put("coursename", coursename);
            	newcourse.put("instructor", instructor);
            	newcourse.put("session", session);
            	newcourse.put("section", section);
            	newcourse.put("enrolled_student", enrolled_student);
            	
            	newcourseList.add(newcourse);
            }
            
            FileWriter file = new FileWriter("courselist.json");
            file.write(newcourseList.toJSONString());
            file.flush();
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
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
	public ArrayList<String> getEnrolled_student() {
		return enrolled_student;
	}
	public void setEnrolled_student(ArrayList<String> enrolled_student) {
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
	public String toString() {
		return this.coursename;
	}
}