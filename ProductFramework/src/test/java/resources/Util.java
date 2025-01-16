package resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.LoginRequest;


public class Util {
	static RequestSpecification reqs;
	 public RequestSpecification req_Spec() throws IOException {
		if(reqs == null) {
	
		PrintStream stream = new PrintStream(new FileOutputStream("logging.txt"));
		reqs = new RequestSpecBuilder().setBaseUri(globalvalues("baseURI")).
		addFilter(RequestLoggingFilter.logRequestTo(stream)).
		addFilter(ResponseLoggingFilter.logResponseTo(stream)).build();
		return reqs;
		}
		 return reqs;
	 }	
	 
		
	
	public String getJsonPath(Response r, String key) {
		String rs = r.asString();
		JsonPath js = new JsonPath(rs);
		String val = js.get(key);
		return val;
		
	}
	
	public LoginRequest loginPayload() {
		LoginRequest lr = new LoginRequest();
		lr.setUserEmail("vijayashivamurthy55@gmail.com");
		lr.setUserPassword("Anvi@2411");
		return lr;
	}
	
	public String orderPayload(String productId) {
		return 
				"{\n\"orders\": [\n{\"country\": \"India\", \n\"productOrderedId\": \""+productId+"\"\n}\n]\n}";
	}
	public String globalvalues(String key) throws IOException {
		Properties prop = new Properties();
		FileInputStream f = new FileInputStream("/Users/mac/eclipse-workspace/ProductFramework/src/test/java/resources/global.properties");
		prop.load(f);
		String value =prop.getProperty(key);
		return value;
}
}
