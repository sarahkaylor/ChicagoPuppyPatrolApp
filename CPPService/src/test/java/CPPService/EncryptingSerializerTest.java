package CPPService;

import java.util.*;

import org.junit.Test;
import CPPModel.*;
import CPPDataAccess.*;
import Encryption.*;
import static org.junit.Assert.*;

public class EncryptingSerializerTest {

	@Test
	public void TestRoundTrip() throws Exception {
		AesEncryption aes = new AesEncryption();
		KeyPair pair = aes.GenerateKeyPair();
		IAuthenticationDataAccess dao = new AuthenticationDataAccessStub();
		UUID userId = UUID.randomUUID();
		dao.SaveAesSecretKey(userId, pair);
		
		IObjectSerializer serializer = new EncryptingSerializer(userId, dao);
		
		@SuppressWarnings("deprecation")
		Date dob = new Date(2000, 1, 1);
		
		MemberObject obj = new MemberObject("name", dob, userId, "pass", "key");
		
		String encJson = serializer.ToJson(obj);
		
		MemberObject loaded = serializer.FromJson(encJson, MemberObject.class);
		
		assertEquals(obj.SceneName, loaded.SceneName);
		assertEquals(obj.DateOfBirth, loaded.DateOfBirth);
		assertEquals(obj.EncryptedPassword, loaded.EncryptedPassword);
		assertEquals(obj.Id, loaded.Id);
		assertEquals(obj.ClientPublicKey, loaded.ClientPublicKey);
	}
}
