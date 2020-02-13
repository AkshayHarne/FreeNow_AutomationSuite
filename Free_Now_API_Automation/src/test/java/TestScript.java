
import java.util.ArrayList;
import java.util.List;

import org.testng.*;
import org.testng.annotations.*;

import com.aventstack.extentreports.*;

import io.restassured.path.json.JsonPath;

public class TestScript extends APIBase 
{
	
	@Test
	//TestCase1 : Validate if the emails in the comment section are in the proper format for the user 'Samantha'
	public void TestCase1()
	{
		test = null;
		try
		{
			test = extent.createTest("TestCase 1", "Validate if the emails in the comment section are in the proper format for the user 'Samantha'");
			
			
			//Send a GET Request to Users API to get the User details of a User 'Samantha'
			stepNo++;
            stepName = "Send a GET Request to Users API to get the User details of a User 'Samantha'";
			response = APIHelper.get(prop.getProperty("usersAPI_baseURI"), prop.getProperty("usersAPI_endPoint") + "Samantha");
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			//Verify that the Status Code of Users API received is 200
			stepNo++;
            stepName = "Verify that the Status Code of Users API recieved is 200";
			statusCode = response.getStatusCode();
			Assert.assertEquals(true, statusCode == 200, "Test Failed : Invalid Status Code of Users API");
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			
			//Get the Id of the User 'Samantha'
			stepNo++;
            stepName = "Get the Id of the User 'Samantha'";
			JsonPath jsonPath = response.jsonPath();
			userId = jsonPath.get("[0].id");
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			//Send a GET Request to the Posts API using the User Id of 'Samantha' and fetch the Posts details for the user
			stepNo++;
            stepName = "Send a GET Request to the Posts API using the User Id of 'Samantha' and fetch the Posts details for the user";
			response = APIHelper.get(prop.getProperty("postsAPI_baseURI"), prop.getProperty("postsAPI_endPoint") + userId);
			String responsebody = response.getBody().asString();
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			//Verify that the Status Code of Posts API recieved is 200
			stepNo++;
            stepName = "Verify that the Status Code of Posts API recieved is 200";
			statusCode = response.getStatusCode();
			Assert.assertEquals(true, statusCode == 200, "Test Failed : Invalid Status Code");
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			//Get the Size of Json array from the response of POSTS API
			stepNo++;
            stepName = "Get the Size of Json array from the response of POSTS API";
			jsonPath = response.jsonPath();
			size_post = jsonPath.getList("id").size();
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			//Get a List of PostIds from the response of POSTS API
			stepNo++;
            stepName = "Get a List of PostIds from the response of POSTS API";
			List<Integer> posts_Ids=new ArrayList<Integer>();
			List<String> emails=new ArrayList<String>();
			for(int i = 0; i<size_post ; i++)
			{
				posts_Id = jsonPath.get("[" + i + "].id");
				posts_Ids.add(posts_Id);
			}
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			//Send a GET request to Comments API for each PostsId corresponding to the user 'Samantha' and get the emails for all the comments
			stepNo++;
            stepName = "Send a GET request to Comments API for each PostsId corresponding to the user 'Samantha' and get the emails for all the comments";
			for(int j = 0; j<posts_Ids.size(); j++)
			{
				response = APIHelper.get(prop.getProperty("commentsAPI_baseURI"), prop.getProperty("commentsAPI_endPoint") + posts_Ids.get(j));
				jsonPath = response.jsonPath();
				size_comments = jsonPath.getList("id").size();
				for(int k = 0; k<size_comments; k++)
				{
					email = jsonPath.get("[" + k + "].email");
					emails.add(email);
				}
			}
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			//Validate if the emails in the comment section are in the proper format.
			stepNo++;
            stepName = "Validate if the emails in the comment section are in the proper format.";
			for(int a =0; a<emails.size(); a++)
			{
				Assert.assertEquals(true, EmailVerificationHelper.verifyEmail(emails.get(a)) ,  "Test Failed : Invalid Email");
			}
			test.log(Status.PASS, stepNo + " : " + stepName);
			
		}
		catch (Exception e) 
		{
			test.log(Status.FAIL, stepNo + " : " + stepName + e.getMessage());
			Assert.assertEquals(true, false, "Test Failed." + e.getMessage());
		}
	}
	
	@Test
	//TestCase2 : Verify that is user unable to get the Id of a User from Users API if the user name is Incorrect
	public void TestCase2()
	{
		test = null;
		try
		{
			test = extent.createTest("TestCase 2", "Verify that user is unable to get the Id of a User from Users API if the user name is Incorrect");
			
			
			//Send a GET Request to Users API to get the User details of a Username 'Samant'
			stepNo++;
            stepName = "Send a GET Request to Users API to get the User details of a Username 'Samant'";
			response = APIHelper.get(prop.getProperty("usersAPI_baseURI"), prop.getProperty("usersAPI_endPoint") + "Samant");
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			//Verify that the Status Code of Users API received is 200
			stepNo++;
            stepName = "Verify that the Status Code of Users API recieved is 200";
			statusCode = response.getStatusCode();
			Assert.assertEquals(true, statusCode == 200, "Test Failed : Invalid Status Code of Users API");
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			
			//Try to get the Id of the User with user name as 'Samant' and verify that JsonPath throws NullPointerException
			stepNo++;
            stepName = "Try to get the Id of the User with user name as 'Samant' and verify that JsonPath throws NullPointerException";
			JsonPath jsonPath = response.jsonPath();
			try
			{
				userId = jsonPath.get("[0].id");
			}
			catch(NullPointerException ex)
			{
				String s = ex.toString();
				Assert.assertEquals(true, ex.toString().contains("java.lang.NullPointerException") , "Test Failed : Invalid Exception");
			}
			test.log(Status.PASS, stepNo + " : " + stepName);
		}
		catch (Exception e) 
		{
			test.log(Status.FAIL, stepNo + " : " + stepName + e.getMessage());
			Assert.assertEquals(true, false, "Test Failed." + e.getMessage());
		}
	}
	
	@Test
	//TestCase3 : Verify that is user unable to get the Id of a User from Users API if the user name is provided is null
	public void TestCase3()
	{
		test = null;
		try
		{
			test = extent.createTest("TestCase 3", "Verify that is user unable to get the Id of a User from Users API if the user name is provided is null");
			
			
			//Send a GET Request to Users API to get the User details of a Username = null
			stepNo++;
            stepName = "Send a GET Request to Users API to get the User details of a Username = null";
			response = APIHelper.get(prop.getProperty("usersAPI_baseURI"), prop.getProperty("usersAPI_endPoint") + null);
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			//Verify that the Status Code of Users API received is 200
			stepNo++;
            stepName = "Verify that the Status Code of Users API recieved is 200";
			statusCode = response.getStatusCode();
			Assert.assertEquals(true, statusCode == 200, "Test Failed : Invalid Status Code of Users API");
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			
			//Try to get the Id of the User with user name as null and verify that JsonPath throws NullPointerException
			stepNo++;
            stepName = "Try to get the Id of the User with user name as null and verify that JsonPath throws NullPointerException";
			JsonPath jsonPath = response.jsonPath();
			try
			{
				userId = jsonPath.get("[0].id");
			}
			catch(NullPointerException ex)
			{
				Assert.assertEquals(true, ex.toString().contains("java.lang.NullPointerException") , "Test Failed : Invalid Exception");
			}
			test.log(Status.PASS, stepNo + " : " + stepName);
		}
		catch (Exception e) 
		{
			test.log(Status.FAIL, stepNo + " : " + stepName + e.getMessage());
			Assert.assertEquals(true, false, "Test Failed." + e.getMessage());
		}
	}
	
	@Test
	//TestCase4 : Verify that is user unable to get the Id of a User from Users API if the user name is provided is Empty String
	public void TestCase4()
	{
		test = null;
		try
		{
			test = extent.createTest("TestCase 4", "Verify that is user unable to get the Id of a User from Users API if the user name is provided is Empty String");
			
			
			//Send a GET Request to Users API without providing a user name
			stepNo++;
            stepName = "Send a GET Request to Users API without providing a user name";
			response = APIHelper.get(prop.getProperty("usersAPI_baseURI"), prop.getProperty("usersAPI_endPoint"));
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			//Verify that the Status Code of Users API received is 200
			stepNo++;
            stepName = "Verify that the Status Code of Users API recieved is 200";
			statusCode = response.getStatusCode();
			Assert.assertEquals(true, statusCode == 200, "Test Failed : Invalid Status Code of Users API");
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			
			//Try to get the Id of the User with user name as null and verify that JsonPath throws NullPointerException
			stepNo++;
            stepName = "Try to get the Id of the User with user name as null and verify that JsonPath throws NullPointerException";
			JsonPath jsonPath = response.jsonPath();
			try
			{
				userId = jsonPath.get("[0].id");
			}
			catch(NullPointerException ex)
			{
				Assert.assertEquals(true, ex.toString().contains("java.lang.NullPointerException") , "Test Failed : Invalid Exception");
			}
			test.log(Status.PASS, stepNo + " : " + stepName);
		}
		catch (Exception e) 
		{
			test.log(Status.FAIL, stepNo + " : " + stepName + e.getMessage());
			Assert.assertEquals(true, false, "Test Failed." + e.getMessage());
		}
	}
	
	@Test
	//TestCase5 : Verify that is user unable to get the Id of a Posts from Posts API if incorrect UserId is provdided to the posts API
	public void TestCase5()
	{
		test = null;
		try
		{
			test = extent.createTest("TestCase 5", "Verify that is user unable to get the Id of a Posts from Posts API if incorrect UserId is provdided to the posts API");
			
			
			//Send a GET Request to Users API to get the User details of a User 'Samantha'
			stepNo++;
            stepName = "Send a GET Request to Users API to get the User details of a User 'Samantha'";
			response = APIHelper.get(prop.getProperty("usersAPI_baseURI"), prop.getProperty("usersAPI_endPoint") + "Samantha");
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			//Verify that the Status Code of Users API received is 200
			stepNo++;
            stepName = "Verify that the Status Code of Users API recieved is 200";
			statusCode = response.getStatusCode();
			Assert.assertEquals(true, statusCode == 200, "Test Failed : Invalid Status Code of Users API");
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			//Get the Id of the User 'Samantha'
			stepNo++;
            stepName = "Get the Id of the User 'Samantha'";
			JsonPath jsonPath = response.jsonPath();
			userId = jsonPath.get("[0].id");
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			//Send a GET Request to the Posts API by providing Incorrect Userid
			stepNo++;
            stepName = "Send a GET Request to the Posts API by providing Incorrect Userid";
			response = APIHelper.get(prop.getProperty("postsAPI_baseURI"), prop.getProperty("postsAPI_endPoint") + 300);
			String responsebody = response.getBody().asString();
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			//Verify that the Status Code of Posts API recieved is 200
			stepNo++;
            stepName = "Verify that the Status Code of Posts API recieved is 200";
			statusCode = response.getStatusCode();
			Assert.assertEquals(true, statusCode == 200, "Test Failed : Invalid Status Code");
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			//Try to get the size of Json array and verify that JsonPath throws NullPointerException
			stepNo++;
            stepName = "Try to get the size of Json array and verify that JsonPath throws NullPointerException";
			jsonPath = response.jsonPath();
			try
			{
				size_post = jsonPath.getList("id").size();
			}
			catch(NullPointerException ex)
			{
				Assert.assertEquals(true, ex.toString().contains("java.lang.NullPointerException") , "Test Failed : Invalid Exception");
			}
			test.log(Status.PASS, stepNo + " : " + stepName);
			
		}
		catch (Exception e) 
		{
			test.log(Status.FAIL, stepNo + " : " + stepName + e.getMessage());
			Assert.assertEquals(true, false, "Test Failed." + e.getMessage());
		}
	}
	
	@Test
	//TestCase6 : Verify that is user unable to get the Id of a Posts from Posts API if  UserId is not provided to the posts API
	public void TestCase6()
	{
		test = null;
		try
		{
			test = extent.createTest("TestCase 6", "Verify that is user unable to get the Id of a Posts from Posts API if  UserId is not provided to the posts API");
			
			//Send a GET Request to Users API to get the User details of a User 'Samantha'
			stepNo++;
            stepName = "Send a GET Request to Users API to get the User details of a User 'Samantha'";
			response = APIHelper.get(prop.getProperty("usersAPI_baseURI"), prop.getProperty("usersAPI_endPoint") + "Samantha");
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			//Verify that the Status Code of Users API received is 200
			stepNo++;
            stepName = "Verify that the Status Code of Users API recieved is 200";
			statusCode = response.getStatusCode();
			Assert.assertEquals(true, statusCode == 200, "Test Failed : Invalid Status Code of Users API");
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			//Get the Id of the User 'Samantha'
			stepNo++;
            stepName = "Get the Id of the User 'Samantha'";
			JsonPath jsonPath = response.jsonPath();
			userId = jsonPath.get("[0].id");
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			//Send a GET Request to the Posts API without providing UserId
			stepNo++;
            stepName = "Send a GET Request to the Posts API without providing UserId";
			response = APIHelper.get(prop.getProperty("postsAPI_baseURI"), prop.getProperty("postsAPI_endPoint"));
			String responsebody = response.getBody().asString();
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			//Verify that the Status Code of Posts API received is 200
			stepNo++;
            stepName = "Verify that the Status Code of Posts API received is 200";
			statusCode = response.getStatusCode();
			Assert.assertEquals(true, statusCode == 200, "Test Failed : Invalid Status Code");
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			//Try to get the size of Json array and verify that JsonPath throws NullPointerException
			stepNo++;
            stepName = "Try to get the size of Json array and verify that JsonPath throws NullPointerException";
			jsonPath = response.jsonPath();
			try
			{
				size_post = jsonPath.getList("id").size();
			}
			catch(NullPointerException ex)
			{
				Assert.assertEquals(true, ex.toString().contains("java.lang.NullPointerException") , "Test Failed : Invalid Exception");
			}
			test.log(Status.PASS, stepNo + " : " + stepName);
			
		}
		catch (Exception e) 
		{
			test.log(Status.FAIL, stepNo + " : " + stepName + e.getMessage());
			Assert.assertEquals(true, false, "Test Failed." + e.getMessage());
		}
	}
	
	@Test
	//TestCase7 : Verify that the USERS API returns '404 Not Found' when the End point is incorrect
	public void TestCase7()
	{
		test = null;
		try
		{
			test = extent.createTest("TestCase 7", "Verify that the USERS API returns '404 Not Found' when the End point is incorrect");
			
			
			//Send a GET Request to Users API with incorrect endpoint
			stepNo++;
            stepName = "Send a GET Request to Users API with incorrect endpoint";
			response = APIHelper.get(prop.getProperty("usersAPI_baseURI"), prop.getProperty("usersAPI_endPointIncorrect") + "Samantha");
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			//Verify that the Status Code of Users API received is 404
			stepNo++;
            stepName = "Verify that the Status Code of Users API recieved is 404";
			statusCode = response.getStatusCode();
			Assert.assertEquals(true, statusCode == 404, "Test Failed : Invalid Status Code of Users API");
			test.log(Status.PASS, stepNo + " : " + stepName);
			
		}
		catch (Exception e) 
		{
			test.log(Status.FAIL, stepNo + " : " + stepName + e.getMessage());
			Assert.assertEquals(true, false, "Test Failed." + e.getMessage());
		}
	}
	
	@Test
	//TestCase8 : Verify that the USERS API returns 'UnknownHostException' when the End point is incorrect
	public void TestCase8()
	{
		test = null;
		try
		{
			test = extent.createTest("TestCase 8", "Verify that the USERS API returns 'UnknownHostException' when the End point is incorrect");
			
			
			//Send a GET Request to Users API with incorrect Base URI
			stepNo++;
            stepName = "Send a GET Request to Users API with incorrect Base URI";
            try
            {
            	response = APIHelper.get(prop.getProperty("usersAPI_baseURIIncorrect"), prop.getProperty("usersAPI_endPoint") + "Samantha");
            }
			catch(Exception ex)
            {
				Assert.assertEquals(true, ex.toString().contains("java.net.UnknownHostException"), "Test Failed : Incorrect Exception displayed.");
            }
			test.log(Status.PASS, stepNo + " : " + stepName);
			
		}
		catch (Exception e) 
		{
			test.log(Status.FAIL, stepNo + " : " + stepName + e.getMessage());
			Assert.assertEquals(true, false, "Test Failed." + e.getMessage());
		}
	}
	
	@Test
	//TestCase9 : Verify that the POSTS API returns '404 Not Found' when the End point is incorrect
	public void TestCase9()
	{
		test = null;
		try
		{
			test = extent.createTest("TestCase 9", "Verify that the POSTS API returns '404 Not Found' when the End point is incorrect");
			
			//Send a GET Request to Users API to get the User details of a User 'Samantha'
			stepNo++;
            stepName = "Send a GET Request to Users API to get the User details of a User 'Samantha'";
			response = APIHelper.get(prop.getProperty("usersAPI_baseURI"), prop.getProperty("usersAPI_endPoint") + "Samantha");
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			//Verify that the Status Code of Users API received is 200
			stepNo++;
            stepName = "Verify that the Status Code of Users API recieved is 200";
			statusCode = response.getStatusCode();
			Assert.assertEquals(true, statusCode == 200, "Test Failed : Invalid Status Code of Users API");
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			
			//Get the Id of the User 'Samantha'
			stepNo++;
            stepName = "Get the Id of the User 'Samantha'";
			JsonPath jsonPath = response.jsonPath();
			userId = jsonPath.get("[0].id");
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			//Send a GET Request to Posts API with incorrect endpoint
			stepNo++;
            stepName = "Send a GET Request to Posts API with incorrect endpoint";
			response = APIHelper.get(prop.getProperty("postsAPI_baseURI"), prop.getProperty("postsAPI_endPointIncorrect") + userId);
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			//Verify that the Status Code of Posts API received is 404
			stepNo++;
            stepName = "Verify that the Status Code of Posts API received is 404";
			statusCode = response.getStatusCode();
			Assert.assertEquals(true, statusCode == 404, "Test Failed : Invalid Status Code");
			test.log(Status.PASS, stepNo + " : " + stepName);
			
		}
		catch (Exception e) 
		{
			test.log(Status.FAIL, stepNo + " : " + stepName + e.getMessage());
			Assert.assertEquals(true, false, "Test Failed." + e.getMessage());
		}
	}
	
	@Test
	//TestCase10 : Verify that the POSTS API returns 'UnknownHostException' when the End point is incorrect
	public void TestCase10()
	{
		test = null;
		try
		{
			test = extent.createTest("TestCase 10", "Verify that the POSTS API returns 'UnknownHostException' when the End point is incorrect");
			
			//Send a GET Request to Users API to get the User details of a User 'Samantha'
			stepNo++;
            stepName = "Send a GET Request to Users API to get the User details of a User 'Samantha'";
			response = APIHelper.get(prop.getProperty("usersAPI_baseURI"), prop.getProperty("usersAPI_endPoint") + "Samantha");
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			//Verify that the Status Code of Users API received is 200
			stepNo++;
            stepName = "Verify that the Status Code of Users API recieved is 200";
			statusCode = response.getStatusCode();
			Assert.assertEquals(true, statusCode == 200, "Test Failed : Invalid Status Code of Users API");
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			
			//Get the Id of the User 'Samantha'
			stepNo++;
            stepName = "Get the Id of the User 'Samantha'";
			JsonPath jsonPath = response.jsonPath();
			userId = jsonPath.get("[0].id");
			test.log(Status.PASS, stepNo + " : " + stepName);
			
			//Send a GET Request to POSTS API with incorrect Base URI
			stepNo++;
            stepName = "Send a GET Request to POSTS API with incorrect Base URI";
			try 
			{
				response = APIHelper.get(prop.getProperty("postsAPI_baseURIIncorrect"), prop.getProperty("postsAPI_endPointIncorrect") + userId);	
			} 
			catch (Exception ex) 
			{
				Assert.assertEquals(true, ex.toString().contains("java.net.UnknownHostException"), "Test Failed : Incorrect Exception displayed.");
			}
            test.log(Status.PASS, stepNo + " : " + stepName);
			
		}
		catch (Exception e) 
		{
			test.log(Status.FAIL, stepNo + " : " + stepName + e.getMessage());
			Assert.assertEquals(true, false, "Test Failed." + e.getMessage());
		}
	}
}