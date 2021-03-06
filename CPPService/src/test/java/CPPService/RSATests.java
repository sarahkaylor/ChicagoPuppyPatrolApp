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
	
	@Test
	public void TestLargeMessage() throws Exception {

		KeyPair pair = new RsaEncryption().GenerateKeyPair();
		
		RsaEncryption enc = new RsaEncryption();
		enc.SetKey(pair);
		
		StringBuilder buffer = new StringBuilder();
		
		int min = (int)' ';
		int max = (int)'z';
		
		for(int i = 0; i < 1024*10; i++) {
			char chr = (char)((i % (max-min)) + min);
			buffer.append(chr);
		}
		
		String originalText = buffer.toString();
		
		String encryptedText = enc.Encrypt(originalText);
		
		String decryptedText = enc.Decrypt(encryptedText);
		
		assertEquals(originalText, decryptedText);
	}
}
