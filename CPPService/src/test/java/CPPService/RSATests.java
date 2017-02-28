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
	
	private String Print(byte[] bytes) {
		StringBuilder buff = new StringBuilder();
		for(int i = 0; i < bytes.length; i++) {
			if(i > 0) {
				buff.append(' ');
			}
			buff.append(bytes[i]);
		}
		return buff.toString();
	}
}
