package CPPModel;

import com.google.gson.Gson;

public class AuthenticationTokenResponse extends SimpleResponse {

	public final AuthenticationTokenObject[] Tokens;
	
	private AuthenticationTokenResponse(){
		super();
		Tokens = new AuthenticationTokenObject[0];
	}
	
	private AuthenticationTokenResponse(String message) {
		super(false, message, "", "");
		Tokens = new AuthenticationTokenObject[0];
	}
	
	private AuthenticationTokenResponse(Exception e) {
		super(e);
		Tokens = new AuthenticationTokenObject[0];
	}
	
	private AuthenticationTokenResponse(AuthenticationTokenObject[] keys){
		super(true, "", "", "");
		Tokens = keys;
	}
	
	public static AuthenticationTokenResponse Error(String message) {
		return new AuthenticationTokenResponse(message);
	}
	
	public static AuthenticationTokenResponse Success(AuthenticationTokenObject[] keys) {
		return new AuthenticationTokenResponse(keys);
	}
	
	public static AuthenticationTokenResponse Error(Exception e) {
		return new AuthenticationTokenResponse(e);
	}
	
	public static AuthenticationTokenResponse FromJson(String json) {
		return new Gson().fromJson(json, AuthenticationTokenResponse.class);
	}
}
