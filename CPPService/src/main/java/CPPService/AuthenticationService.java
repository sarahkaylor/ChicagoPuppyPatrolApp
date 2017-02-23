package CPPService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import CPPDataAccess.*;
import CPPModel.*;
import Encryption.*;
import java.util.*;

@Path("Authentication")
public class AuthenticationService {

	private final IAuthenticationDataAccess _dao;
	private final IMemberDataAccess _memberDao;
	private final IEncryptionService _enc;
	
	public AuthenticationService(IAuthenticationDataAccess dao, IMemberDataAccess memberDao, IEncryptionService enc) {
		_dao = dao;
		_memberDao = memberDao;
		_enc = enc;
	}
	
	public AuthenticationService() {
		this(new AuthenticationDataAccess(), new MemberDataAccess(), new EncryptionService());
	}
	
	public void GenerateKeyPairIfItDoesnNotExist() {
		Collection<KeyPair> pairs = _dao.LoadServerKeyPairs();
		if(pairs.size() == 0) {
			KeyPair pair = _enc.GenerateKeyPair();
			_dao.SaveServerKeyPair(pair);
		}
	}
	
	@GET()
	@Path("/publicKey")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetServerPublicKey() {
		KeyExchangeResponse response;
		try {
			Collection<KeyPair> keys = _dao.LoadServerKeyPairs();
			if(keys.size() == 0) {
				response = KeyExchangeResponse.Error("Server keys are not generated");
			} else {
				KeyPair key = keys.iterator().next();
				KeyExchangeObject loadedKey = new KeyExchangeObject(key.PublicKey);
				response = KeyExchangeResponse.Success(new KeyExchangeObject[]{loadedKey});
			}
		} catch(Exception e) {
			response = KeyExchangeResponse.Error(e);
		}
		return response.ToJson();
	}
	
	@POST()
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetToken(@PathParam("id") String idStr, String encryptedPassword) {
		AuthenticationTokenResponse response;
		try {
			UUID id = UUID.fromString(idStr);
			Collection<String> privateKeys = _dao.LoadServerPrivateKey();
			Collection<MemberObject> members = _memberDao.LoadMember(id);
			if(privateKeys.size() == 0){
				response = AuthenticationTokenResponse.Error("Service could not find its private key");
			} else if(members.size() == 0) {
				response = AuthenticationTokenResponse.Error("Could not find user name");
			} else {
				String privateKey = privateKeys.iterator().next();
				MemberObject member = members.iterator().next();
				if(_enc.MemberPasswordMatches(privateKey, member, encryptedPassword)) {
					AuthenticationTokenObject token = _enc.GenerateToken(privateKey, member, encryptedPassword);
					_dao.SaveCurrentToken(token);
					AuthenticationTokenObject[] tokens = new AuthenticationTokenObject[]{token};
					response = AuthenticationTokenResponse.Success(tokens);
				} else {
					response = AuthenticationTokenResponse.Error("Password did not match");
				}
			}
		} catch(Exception e) {
			response = AuthenticationTokenResponse.Error(e);
		}
		return response.ToJson();
	}
	
	public boolean TokenPasses(UUID userId, String token) throws Exception {
		Collection<AuthenticationTokenObject> tokens = _dao.LoadTokensForUser(userId);
		for(AuthenticationTokenObject item : tokens) {
			if(item.Matches(userId, token)) {
				return true;
			}
		}
		return false;
	}
}




