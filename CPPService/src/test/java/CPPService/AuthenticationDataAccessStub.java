package CPPService;

import java.util.*;

import CPPDataAccess.*;
import CPPModel.*;

public class AuthenticationDataAccessStub implements IAuthenticationDataAccess {

	private final Collection<KeyPair> _serverKeys;
	private final Map<UUID, AuthenticationTokenObject> _tokens;
	
	public AuthenticationDataAccessStub() {
		_serverKeys = new ArrayList<KeyPair>();
		_tokens = new HashMap<UUID, AuthenticationTokenObject>();
	}
	
	@Override
	public Collection<String> LoadServerPrivateKey() {
		Collection<String> privateKeys = new ArrayList<String>();
		for(KeyPair key : _serverKeys) {
			privateKeys.add(key.PrivateKey);
		}
		return privateKeys;
	}

	@Override
	public void SaveCurrentToken(AuthenticationTokenObject token) {
		_tokens.put(token.UserId, token);
	}

	@Override
	public Collection<AuthenticationTokenObject> LoadTokensForUser(UUID userId) {
		Collection<AuthenticationTokenObject> tokens = new ArrayList<AuthenticationTokenObject>();
		if(_tokens.containsKey(userId)) {
			tokens.add(_tokens.get(userId));
		}
		return tokens;
	}

	@Override
	public Collection<KeyPair> LoadServerKeyPairs() {
		return _serverKeys;
	}

	@Override
	public void SaveServerKeyPair(KeyPair pair) {
		_serverKeys.clear();
		_serverKeys.add(pair);
	}

}
