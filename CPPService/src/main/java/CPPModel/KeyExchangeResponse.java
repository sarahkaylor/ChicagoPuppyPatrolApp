package CPPModel;

import java.util.*;

import com.google.gson.Gson;

public class KeyExchangeResponse extends SimpleResponse {

	public final KeyExchangeObject[] Keys;
	
	private KeyExchangeResponse(){
		super();
		Keys = new KeyExchangeObject[0];
	}
	
	private KeyExchangeResponse(Exception e) {
		super(e);
		Keys = new KeyExchangeObject[0];
	}
	
	private KeyExchangeResponse(KeyExchangeObject[] keys){
		super(true, "", "");
		Keys = keys;
	}
	
	private KeyExchangeResponse(String message) {
		super(false, message, "");
		Keys = new KeyExchangeObject[0];
	}
	
	public static KeyExchangeResponse Success(KeyExchangeObject[] keyExchangeObjects) {
		return new KeyExchangeResponse(keyExchangeObjects);
	}
	
	public static KeyExchangeResponse Error(String message) {
		return new KeyExchangeResponse(message);
	}
	
	public static KeyExchangeResponse Error(Exception e) {
		return new KeyExchangeResponse(e);
	}
	
	public static KeyExchangeResponse FromJson(String json) {
		return new Gson().fromJson(json, KeyExchangeResponse.class);
	}
}
