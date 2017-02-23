package CPPService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import CPPDataAccess.IMemberDataAccess;
import CPPDataAccess.MemberDataAccess;
import CPPModel.*;

import java.util.*;

@Path("Members")
public class MemberService {

	private final IMemberDataAccess _dao;
	
	public MemberService(IMemberDataAccess dao) {
		_dao = dao;
	}
	
	public MemberService() {
		this(new MemberDataAccess());
	}
	
	@GET()
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetAllMembers() {
		MemberResponse response;
		try{
			Collection<MemberObject> members = _dao.LoadAllMembers();
			response = MemberResponse.Success(members);
		} catch(Exception e) {
			response = MemberResponse.Error(e);
		}
		return response.ToJson();
	}
	
	@GET()
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetMember(@PathParam("id") String idStr) {
		MemberResponse response;
		try{
			UUID id = UUID.fromString(idStr);
			Collection<MemberObject> members = _dao.LoadMember(id);
			if(members.size() == 0) {
				response = MemberResponse.Error("Not Found");
			} else {
				response = MemberResponse.Success(members);
			}
		} catch(Exception e) {
			response = MemberResponse.Error(e);
		}
		return response.ToJson();
	}
	
	@POST()
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String CreateMember(@PathParam("id") String idStr, String userProfile) {
		MemberResponse response;
		try{
			UUID id = UUID.fromString(idStr);
			MemberObject member = MemberObject.FromJson(userProfile);
			if(id.compareTo(member.Id) == 0) {
				_dao.SaveOrUpdateMember(member);
				response = MemberResponse.Success("Updated");
			} else {
				response = MemberResponse.Error("Inconsistent ID");
			}
		} catch(Exception e) {
			response = MemberResponse.Error(e);
		}
		return response.ToJson();
	}
}
