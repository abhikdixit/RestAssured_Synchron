package pk_MyNotes;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseClass {

	static String outh_token;

	@Test(priority = 0)
	public static void deleteAllNotes() throws IOException, ParseException {
		RestAssured.baseURI = "https://practice.expandtesting.com";
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.header("x-auth-token", outh_token);

		// Fetch existing notes
		Response response = request.request(Method.GET, "/notes/api/notes");
		response.prettyPrint();
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);

		// Get the list of notes
		JsonPath jsonPathEvaluator = response.getBody().jsonPath();
		List<String> notesIds = jsonPathEvaluator.getList("data.id");

		// Loop through each note ID and delete it
		for (String noteId : notesIds) {
			Response deleteResponse = request.request(Method.DELETE, "/notes/api/notes/" + noteId);
			deleteResponse.prettyPrint();
			int deleteStatusCode = deleteResponse.getStatusCode();
			Assert.assertEquals(deleteStatusCode, 200); // Assuming 200 is the success status for deletion
		}
	}

	public static String LOGIN(String uname, String pass) {
		// System.out.println("LOGINBase");
		RestAssured.baseURI = "https://practice.expandtesting.com";
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject();
		requestParams.put("email", uname);
		requestParams.put("password", pass);
		// Add a header stating the Request body is a JSON
		request.header("Content-Type", "application/json");
		request.body(requestParams.toJSONString());
		// POST the Response
		Response response = request.request(Method.POST, "/notes/api/users/login");
		// Response response = request.request(Method.POST,"/spree_oauth/token");
		response.prettyPrint();
		int statusCode = response.getStatusCode();
		// System.out.println(response.asString());
		Assert.assertEquals(statusCode, 200);
		// To get the Token from JSON Response
		JsonPath jsonPathEvaluator = response.getBody().jsonPath();
		outh_token = jsonPathEvaluator.get("data.token").toString();
		System.out.println("oAuth Token is =>  " + outh_token);
		return outh_token;

	}
}
