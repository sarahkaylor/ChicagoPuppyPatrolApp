package CPPDataAccess;

import java.util.Collection;
import java.util.UUID;

import CPPModel.AuthenticationTokenObject;
import CPPModel.KeyPair;

public interface IAuthenticationDataAccess {

	public Collection<String> LoadServerPrivateKey();

	public void SaveCurrentToken(AuthenticationTokenObject token);

	public Collection<AuthenticationTokenObject> LoadTokensForUser(UUID userId);

	public Collection<KeyPair> LoadServerKeyPairs();

	public void SaveServerKeyPair(KeyPair pair);
}
