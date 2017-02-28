package CPPDataAccess;

import java.util.Collection;
import java.util.UUID;

import CPPModel.AuthenticationTokenObject;
import CPPModel.KeyPair;

public class AuthenticationDataAccess implements IAuthenticationDataAccess {

	@Override
	public Collection<String> LoadServerPrivateKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void SaveCurrentToken(AuthenticationTokenObject token) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<AuthenticationTokenObject> LoadTokensForUser(UUID userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<KeyPair> LoadServerKeyPairs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void SaveServerKeyPair(KeyPair pair) {
		// TODO Auto-generated method stub
		
	}

}
