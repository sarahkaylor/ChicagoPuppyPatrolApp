package CPPService;

import java.util.*;

import CPPDataAccess.*;
import CPPModel.*;

public class MemberDataAccessStub implements IMemberDataAccess {

	private final Map<UUID, MemberObject> _members;
	
	public MemberDataAccessStub() {
		_members = new HashMap<UUID, MemberObject>();
	}
	
	@Override
	public Collection<MemberObject> LoadMember(UUID id) {
		Collection<MemberObject> members = new ArrayList<MemberObject>();
		if(_members.containsKey(id)) {
			members.add(_members.get(id));
		}
		return members;
	}

	@Override
	public Collection<MemberObject> LoadAllMembers() {
		return _members.values();
	}

	@Override
	public void SaveOrUpdateMember(MemberObject member) {
		_members.put(member.Id, member);
	}

}
