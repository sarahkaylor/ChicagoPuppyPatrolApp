package CPPService;
import Encryption.*;

import static org.junit.Assert.assertEquals;

import org.junit.*;

import CPPModel.KeyPair;

public class RSATests {

	@Before
	public void Setup() {
		
	}
	
	@After
	public void TearDown() {
		
	}
	
	@Test
	public void TestRoundTrip() throws Exception {
		KeyPair pair = new RsaEncryption().GenerateKeyPair();
		
		RsaEncryption enc = new RsaEncryption();
		enc.SetKey(pair);
		
		final String originalText = "hello world";
		
		final String encryptedText = enc.Encrypt(originalText);
		
		final String decryptedText = enc.Decrypt(encryptedText);
		
		assertEquals(originalText, decryptedText);
	}
}
