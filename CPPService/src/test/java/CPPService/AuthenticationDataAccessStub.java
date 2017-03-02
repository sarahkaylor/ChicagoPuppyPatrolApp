package CPPService;

import java.util.*;

import CPPDataAccess.*;
import CPPModel.*;

public class AuthenticationDataAccessStub implements IAuthenticationDataAccess {

	private final Collection<KeyPair> _serverKeys;
	private final Map<UUID, AuthenticationTokenObject> _tokens;
	private final Map<UUID, KeyPair> _aesKeys;
	
	public AuthenticationDataAccessStub() {
		_serverKeys = new ArrayList<KeyPair>();
		_tokens = new HashMap<UUID, AuthenticationTokenObject>();
		_aesKeys = new HashMap<UUID, KeyPair>();
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

	@Override
	public KeyPair LoadAesSecretKey(UUID userId) throws Exception {
		KeyPair pair;
		if(_aesKeys.containsKey(userId)) {
			pair = _aesKeys.get(userId);
		} else {
			throw new Exception("Could not find key");
		}
		return pair;
	}

	@Override
	public void SaveAesSecretKey(UUID userId, KeyPair key) throws Exception {
		_aesKeys.put(userId, key);
	}

}
