import java.util.regex.Pattern;

public class EmailVerificationHelper 
{
	public static boolean verifyEmail(String email) throws Exception
	{
		try
		{
			
			String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                    "[a-zA-Z0-9_+&*-]+)*@" + 
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                    "A-Z]{2,7}$"; 
			Pattern pattern = Pattern.compile(emailRegex);
			return pattern.matcher(email).matches();
			
			
		}
		catch(Exception ex)
		{
			throw new Exception("Unable to verify an Email : " + ex.getMessage());
		}
	}

}