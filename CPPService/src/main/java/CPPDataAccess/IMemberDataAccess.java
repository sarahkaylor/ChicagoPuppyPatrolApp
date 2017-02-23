package CPPDataAccess;

import java.util.Collection;
import java.util.UUID;

import CPPModel.MemberObject;

public interface IMemberDataAccess {
	public Collection<MemberObject> LoadMember(UUID id);
	public Collection<MemberObject> LoadAllMembers();
	public void SaveOrUpdateMember(MemberObject member);
}
