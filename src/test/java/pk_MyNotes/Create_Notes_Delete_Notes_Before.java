package pk_MyNotes;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Create_Notes_Delete_Notes_Before {

	static String Token;
	static String notesid;

	@BeforeTest
	public static void Login() throws IOException, ParseException {
		Token = BaseClass.LOGIN("abhinay.dixit@hotmail.com", "pass@1234");
		System.out.println("LOGIN");
		BaseClass.deleteAllNotes();
	}


	@Test
	public static void createNotes() throws IOException, ParseException {
		
		JSONParser jsonparser = new JSONParser();
		// Create object for FileReader class, which help to load and read JSON file
		FileReader reader = new FileReader(".\\TestData\\Create_Notes.json");
		// Returning/assigning to Java Object
		Object obj = jsonparser.parse(reader);
		// Convert Java Object to JSON Object, JSONObject is typecast here
		JSONObject prodjsonobj = (JSONObject) obj;
		System.out.println("PASS");

		RestAssured.baseURI = "https://practice.expandtesting.com";
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.header("x-auth-token", Token);
		request.body(prodjsonobj.toJSONString());
		// POST the Response
		Response response = request.request(Method.POST, "/notes/api/notes");
		// Response response = request.request(Method.POST,"/spree_oauth/token");
		response.prettyPrint();
		int statusCode = response.getStatusCode();
		// System.out.println(response.asString());
		Assert.assertEquals(statusCode, 200);
		// To get the Token from JSON Response
		//JsonPath jsonPathEvaluator = response.getBody().jsonPath();
		JsonPath jsonPathEvaluator = response.getBody().jsonPath();
		notesid = jsonPathEvaluator.get("data.id").toString();
		
		String fname = jsonPathEvaluator.get("data.title").toString();
		System.out.println("Title is =>  " + fname);
		Assert.assertEquals("Practice WebApp UI", fname);

	}
	
}
