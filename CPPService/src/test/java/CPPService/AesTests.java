package CPPService;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import CPPModel.KeyPair;
import Encryption.AesEncryption;

public class AesTests {

	@Before
	public void Setup() {
		
	}
	
	@After
	public void TearDown() {
		
	}

	@Test
	public void TestRoundTrip() throws Exception {
		KeyPair pair = new AesEncryption().GenerateKeyPair();
		
		AesEncryption enc = new AesEncryption();
		enc.SetKey(pair);
		
		final String originalText = "hello world";
		
		final String encryptedText = enc.Encrypt(originalText);
		
		final String decryptedText = enc.Decrypt(encryptedText);
		
		assertEquals(originalText, decryptedText);
	}
	
	@Test
	public void TestLargeMessage() throws Exception {

		KeyPair pair = new AesEncryption().GenerateKeyPair();
		
		AesEncryption enc = new AesEncryption();
		enc.SetKey(pair);
		
		StringBuilder buffer = new StringBuilder();
		for(int i = 0; i < 1024*1024; i++) {
			char chr = (char)(i % 255);
			buffer.append(chr);
		}
		
		String originalText = buffer.toString();
		
		String encryptedText = enc.Encrypt(originalText);
		
		String decryptedText = enc.Decrypt(encryptedText);
		
		assertEquals(originalText, decryptedText);
	}
}
