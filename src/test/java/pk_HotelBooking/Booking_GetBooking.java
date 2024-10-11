package pk_HotelBooking;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Booking_GetBooking {
	@Test
	public void GetBookingIds() {

		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
		RequestSpecification httpRequest = RestAssured.given();
		// Response response = httpRequest.get();
		Response response = httpRequest.request(Method.GET, "/booking/1");

		// Now let us print the body of the message to see what response
		// we have recieved from the server
		String responseBody = response.getBody().prettyPrint();
		System.out.println("Response Body is =>  " + responseBody);
		// Status Code Validation
		int statusCode = response.getStatusCode();
		System.out.println("Status code is =>  " + statusCode);
		Assert.assertEquals(200, statusCode);

		// First get the JsonPath object instance from the Response interface
		Assert.assertEquals(responseBody.contains("Sally") /* Expected value */, true /* Actual Value */);

		JsonPath jsonPathEvaluator = response.getBody().jsonPath();
		String fname = jsonPathEvaluator.get("firstname").toString();
		System.out.println("First Name is =>  " + fname);
		Assert.assertEquals("Sally", fname);
	}
}
