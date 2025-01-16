package stepDefinition;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import io.cucumber.java.en.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import resources.Util;

public class StepDef extends Util{
	
	RequestSpecification requestSpec;
	
	static String token;
	static String userId;
	static String value;
	Response loginResponse;
	String orderID;
	
	@Given("Payload of LoginAPI")
	public void payload_of_login_api() throws IOException {
	    // Write code here that turns the phrase above into concrete actions
		requestSpec = given().spec(req_Spec()).contentType(ContentType.JSON).body(loginPayload());
	}
	
	@When("Call the {string} with {string} http method")
	public void call_the_with_http_method(String resource, String method) {
	    // Write code here that turns the phrase above into concrete actions
		if(resource.equalsIgnoreCase("LoginAPI")&& method.equalsIgnoreCase("post"))
			loginResponse = requestSpec.when().post("api/ecom/auth/login");
		else if(resource.equalsIgnoreCase("AddProductAPI") && method.equalsIgnoreCase("post"))
			loginResponse = requestSpec.when().post("api/ecom/product/add-product");	
		else if(resource.equalsIgnoreCase("CreateProductAPI") && method.equalsIgnoreCase("post"))
			loginResponse = requestSpec.when().post("api/ecom/order/create-order");
	}	
		
	@Then("the api call is success with status code {int}")
	public void the_api_call_is_success_with_status_code(Integer code) {
	    // Write code here that turns the phrase above into concrete actions
		int c = code;
	  assertEquals(c,loginResponse.getStatusCode()); 
	}
	
	@Then("{string} in the response body is {string}")
	public void in_the_response_body_is(String string, String ExpectedVal) {
	    // Write code here that turns the phrase above into concrete actions
		assertEquals(ExpectedVal,getJsonPath(loginResponse,string)); 
	}
	
	@Then("Store the value of {string} and  {string}")
	public void store_the_value_of_and(String userID, String Token) {
	    // Write code here that turns the phrase above into concrete actions
		
		userId = getJsonPath(loginResponse,userID);	
		token = getJsonPath(loginResponse,Token);	
	}
	
	@Given("AddProductAPI payload with {string}")
	public void add_product_api_payload_with(String imagePath) throws IOException {
	    // Write code here that turns the phrase above into concrete actions
		
		requestSpec = given().spec(req_Spec()).contentType(ContentType.MULTIPART).
		header("Authorization",token).param("productName", "Oneplus Nord 5G").
		param("productAddedBy",userId).param("productCategory","Electronics").
		param("productSubCategory", "Mobile").param("productPrice", 25000).
		param("productDescription","Oneplus mobile phone").param("productFor", "General").
		multiPart("productImage",new File(imagePath) );				
	}
	
	@Then("Store the value of {string}")
	public void store_the_value_of(String key) {
	    // Write code here that turns the phrase above into concrete actions
		value = getJsonPath(loginResponse,key);	
		//System.out.println(token);
}
	
	@Given("CreateProdAPI payload")
	public void create_prod_api_payload() throws IOException {
	    // Write code here that turns the phrase above into concrete actions
		requestSpec = given().spec(req_Spec()).contentType(ContentType.JSON).
				header("Authorization",token).body(orderPayload(value));
		call_the_with_http_method("CreateProductAPI","POST");
		orderID = getJsonPath(loginResponse,"orders[0]");	
		System.out.println(orderID);
		
	}

}

