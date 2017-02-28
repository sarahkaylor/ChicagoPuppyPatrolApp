package CPPService;

import CPPModel.*;
import CPPDataAccess.*;
import Encryption.*;
import java.util.*;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class AuthenticationServiceTest {
	private IMemberDataAccess _memberDao;
	private IAuthenticationDataAccess _dao;
	private IEncryptionService _enc;
	private AuthenticationService _service;
	
	@Before
	public void Setup(){
		_memberDao = new MemberDataAccessStub();
		_enc = new EncryptionService();
		_dao = new AuthenticationDataAccessStub();
		_service = new AuthenticationService(_dao, _memberDao, _enc);
	}
	
	@After 
	public void TearDown() {
		
	}
	
	@Test
	public void TestCreateKeys() throws Exception {
		assertEquals(0, _dao.LoadServerKeyPairs().size());
		
		_service.GenerateKeyPairIfItDoesnNotExist();
		
		Collection<KeyPair> keys = _dao.LoadServerKeyPairs();
		
		assertEquals(1, keys.size());
		
		KeyPair key = keys.iterator().next();
		
		assertFalse(key.PrivateKey.length() == 0);
		assertFalse(key.PublicKey.length() == 0);
	}
}
