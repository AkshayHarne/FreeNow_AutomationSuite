import java.io.FileInputStream;
import java.util.Properties;

import org.testng.ITestResult;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.restassured.response.Response;

public class APIBase {

	public static Response response = null;
	public static int statusCode = 0;
	public static int userId = 0;
	public static int size_post = 0;
	public static int posts_Id = 0;
	public static int size_comments = 0;
	public static String email = "";

	//Variables for generating Extent Report 
	public static ExtentReports extent;
    public static ExtentHtmlReporter htmlReporter;
    public static ExtentTest test;
    public static int stepNo;
    public static String stepName;
    public static Properties prop;
    public static String path;
    public static FileInputStream ip;
    
    //Method to perform the class level setup before the test cases are executed
    @BeforeClass
    public static void fixtureLevelSetup() throws Exception 
	{
    	try
    	{
    		htmlReporter = new ExtentHtmlReporter(System. getProperty("user.dir") + "/Reports/ExtentReports.html");
        	//htmlReporter.loadConfig(System. getProperty("user.dir") + "/Reports/extent-config.xml");
        	extent = new ExtentReports();
    		extent.attachReporter(APIBase.htmlReporter);
    		prop = new Properties();
    		path = System. getProperty("user.dir");
    		ip = new FileInputStream(path + "/config.properties");
    		prop.load(ip);
    	}
    	catch(Exception ex)
    	{
    		throw new Exception(ex.getMessage());
    	}
		
	}
	
    //Method to perform the class level setup after the test cases are executed
	@AfterClass
	public void FixtureLevelTearDown()
    {
        //This will flush the report data to file
        extent.flush();

    }

    
	//Method to perform test level setup before execution of each test
	@BeforeMethod
	public void TestLevelSetup() throws Exception
    {
        try
        {
        	
        	stepNo = 0;
            stepName = "";
            
        }
        catch(Exception ex)
        {
        	throw new Exception(ex.getMessage());
        }
        
    }
	
	//Method to perform test level setup after execution of each test
	@AfterMethod
	public void TestLevelTearDown(ITestResult result) throws Exception
    {
        try
        {
        	
        }
        catch(Exception ex)
        {
        	throw new Exception(ex.getMessage());
        }
        
    }

}