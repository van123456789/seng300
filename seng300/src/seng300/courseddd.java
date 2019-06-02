package seng300;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class courseddd {

	public static void main(String[] args) 
	{
		try 
		{
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);

			Course[] course_list = Stream.of(
				new Course("54347", "MATH 271", "Thi Dinh", "Winter", "2019-01"),
				new Course("54585", "MATH 265", "Ryan Hamilton", "Spring", "2018-01"),
				new Course("891", "MATH 267", "", "", ""),
				new Course("31817", "CPSC 231", "", "", ""),
				new Course("54585", "CPSC 233", "", "", ""),
				new Course("54585", "CPSC 355", "", "", ""),
				new Course("54585", "CPSC 359", "", "", ""),
				new Course("54585", "CPSC 413", "", "", ""),
				new Course("54585", "CPSC 441", "", "", ""),
				new Course("54585", "CPSC 449", "", "", ""),
				new Course("54585", "CPSC 457", "", "", ""),
				new Course("54585", "CPSC 471", "", "", ""),
				new Course("54585", "CPSC 481", "", "", ""),
				new Course("54585", "CPSC 522", "", "", "")
				).toArray(Course[]::new);

			mapper.writerWithDefaultPrettyPrinter().writeValue(new File("courselist.json"), course_list);

		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
