package CPPModel;

import java.util.Collection;

import com.google.gson.Gson;

public class MemberResponse extends SimpleResponse {

	public final MemberObject[] Members;
	
	private MemberResponse() {
		super();
		Members = new MemberObject[0];
	}
	
	private MemberResponse(Exception e) {
		super(e);
		Members = new MemberObject[0];
	}
	
	private MemberResponse(boolean success, String message) {
		super(success, message, "", "");
		Members = new MemberObject[0];
	}
	
	private MemberResponse(Collection<MemberObject> members) {
		super(true, "", "", "");
		Members = members.toArray(new MemberObject[0]);
	}
	
	public static MemberResponse Error(Exception e) {
		return new MemberResponse(e);
	}
	
	public static MemberResponse Error(String message) {
		return new MemberResponse(false, message);
	}
	
	public static MemberResponse Success(Collection<MemberObject> members) {
		return new MemberResponse(members);
	}
	
	public static MemberResponse FromJson(String json) {
		return new Gson().fromJson(json, MemberResponse.class);
	}

	public static MemberResponse Success(String message) {
		return new MemberResponse(true, message);
	}
}
