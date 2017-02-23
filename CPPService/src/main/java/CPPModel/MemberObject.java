package CPPModel;

import java.util.*;

import com.google.gson.Gson;

public class MemberObject {
	public final String SceneName;
	public final String EncryptedPassword;
	public final Date DateOfBirth;
	public final UUID Id;
	public final String ClientPublicKey;
	
	public MemberObject(String sceneName, Date dateOfBirth, UUID id, String encryptedPassword, String clientPublicKey) {
		SceneName = sceneName;
		DateOfBirth = dateOfBirth;
		Id = id;
		EncryptedPassword = encryptedPassword;
		ClientPublicKey = clientPublicKey;
	}
	
	public String ToJson() {
		return new Gson().toJson(this);
	}
	
	public static MemberObject FromJson(String json) {
		return new Gson().fromJson(json, MemberObject.class);
	}
}
