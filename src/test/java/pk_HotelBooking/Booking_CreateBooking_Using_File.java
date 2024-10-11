package pk_HotelBooking;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Booking_CreateBooking_Using_File {
  @Test
  public void createBooking() throws IOException, ParseException 
  {
	  
	  //Create json object of JSONParser class to parse the JSON data
	  JSONParser jsonparser=new JSONParser();
	  //Create object for FileReader class, which help to load and read JSON file
	  FileReader reader = new FileReader(".\\TestData\\CreateBooking.json");
	  //Returning/assigning to Java Object
	  Object obj = jsonparser.parse(reader);
	  //Convert Java Object to JSON Object, JSONObject is typecast here
	  JSONObject prodjsonobj = (JSONObject)obj;
	  
	  RestAssured.baseURI = "https://restful-booker.herokuapp.com";
      RequestSpecification request = RestAssured.given();
	  request.header("Content-Type", "application/json");
      request.body(prodjsonobj.toJSONString());
      // POST the Response
      Response response = request.request(Method.POST,"/booking");
      //Response response = request.request(Method.POST,"/spree_oauth/token");
      response.prettyPrint();
      int statusCode = response.getStatusCode();
      // System.out.println(response.asString());
      Assert.assertEquals(statusCode, 200);
      // To get the Token from JSON Response
      JsonPath jsonPathEvaluator = response.getBody().jsonPath();
      String fname = jsonPathEvaluator.get("booking.firstname").toString();
      System.out.println("First Name is =>  " + fname);
      Assert.assertEquals("Abhi", fname);
      
      
      
      
  }
}
