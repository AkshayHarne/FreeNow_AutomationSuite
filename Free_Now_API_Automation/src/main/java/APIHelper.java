import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APIHelper {

	public static RequestSpecification httprequest ;
	public static Response response;

	
	public static Response get(String baseURI, String endPoint) throws Exception
	{
		try
		{
			RestAssured.baseURI = baseURI;
			httprequest = RestAssured.given();
			response = httprequest.request(Method.GET, endPoint);
			return response;
		}
		catch(Exception ex)
		{
			throw new Exception("Unable to send a GET Request" + ex.getMessage() + ex.toString());
		}
	}
}