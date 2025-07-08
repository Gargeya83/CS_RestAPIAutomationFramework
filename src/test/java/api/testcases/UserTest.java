package api.testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import api.endpoints.userEndPoints;
import api.payload.user;
import io.restassured.response.Response;
import org.apache.logging.log4j.EventLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserTest {
	//Generate test data
	Faker faker;
	user userPayload;//Object is created
	public static Logger logger;
	
	@BeforeClass//We have to generate data first and then execute test method
	//Below method is execute first before all method of this class
	public void generateTestData()
	{
		faker= new Faker();// create payload using fake data
		userPayload= new user();
		
		//Set fake data
		userPayload.setId(faker.idNumber().hashCode());//To generate unique id
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5,10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//Obtain logger
		logger = LogManager.getLogger("RestAssuredAutomationFramework1");
	}
	
    @Test(priority=1)
	public void testCreateUser()
	{
    	Response response= userEndPoints.createUser(userPayload);
    	System.out.println("Create user data");
    	//Log response
    	response.then().log().all();
    	
    	//Validation
    	Assert.assertEquals(response.getStatusCode(),200);
    	
    	//log
    	logger.info("Create User executed.");
	}
    
    @Test(priority=2)
	public void testGetUseData() throws InterruptedException
	{
    	Thread.sleep(2000); 
    	Response response= userEndPoints.GetUser(this.userPayload.getUsername());
    	System.out.println("Read user data");
    	//Log response
    	response.then().log().all();
    	
    	//Validation
    	Assert.assertEquals(response.getStatusCode(),200);
    	
    	//log
    	logger.info("Get User executed.");
	}

    @Test(priority=3)
	public void testUpdateUser()
	{
    	userPayload.setFirstName(faker.name().firstName());
    	Response response= userEndPoints.UpdateUser(this.userPayload.getUsername(),userPayload);
    	//System.out.println("Update user data");
    	//Log response
    	response.then().log().all();
    	
    	//Validation
    	Assert.assertEquals(response.getStatusCode(),200);
    	
    	//log
    	logger.info("Update User executed.");
    	
    	//Read User data to check if first name is updated or not
    	Response responsePostUpdate = userEndPoints.GetUser(this.userPayload.getUsername());
	    System.out.println("After Update User Data");
    	responsePostUpdate.then().log().all();
	}

    @Test(priority=4)
	public void testDeleteUser() throws InterruptedException
	{
    	Thread.sleep(1000);
    	Response response= userEndPoints.DeleteUser(this.userPayload.getUsername());
    	System.out.println("Delete user data");
    	//Log response
    	response.then().log().all();
    	
    	//Validation
    	Assert.assertEquals(response.getStatusCode(),200);
    	
    	//log
    	logger.info("Delete User executed.");
	}

}
