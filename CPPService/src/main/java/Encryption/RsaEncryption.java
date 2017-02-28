package Encryption;

import java.security.*;
import java.security.spec.*;
import javax.crypto.*;

public class RsaEncryption implements IAsymmetricEncryption {
	private final static int KeySize = 4096;
	private final static String Algorithm = "RSA";
	private Cipher _enc;
	private Cipher _dec;
	
	public RsaEncryption() {
		_enc = null;
		_dec = null;
	}

	@Override
	public void SetKey(CPPModel.KeyPair pair) throws Exception {
		PublicKey publicKey = ReadPublicKey(pair.PublicKey);
		PrivateKey privateKey = ReadPrivateKey(pair.PrivateKey);
		_enc = Cipher.getInstance(Algorithm);
		_dec = Cipher.getInstance(Algorithm);
		_enc.init(Cipher.ENCRYPT_MODE, publicKey);
		_dec.init(Cipher.DECRYPT_MODE, privateKey);
	}

	@Override
	public String Encrypt(String text) throws Exception {
		byte[] textBytes = text.getBytes();
		byte[] encryptedBytes = _enc.doFinal(textBytes);
		String encryptedText = Base64Encode.encode(encryptedBytes);
		return encryptedText;
	}

	@Override
	public String Decrypt(String encryptedText) throws Exception {
		byte[] textBytes = Base64Encode.decode(encryptedText);
		byte[] decryptedBytes = _dec.doFinal(textBytes);
		String decryptedText = new String(decryptedBytes);
		return decryptedText;
	}

	@Override
	public CPPModel.KeyPair GenerateKeyPair() throws Exception {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance(Algorithm);
		keyGen.initialize(KeySize);
		KeyPair key = keyGen.generateKeyPair();
		
		X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(
				key.getPublic().getEncoded());
		PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(
				key.getPrivate().getEncoded());
		
		byte[] encodedPublic = publicSpec.getEncoded();
		byte[] encodedPrivate = privateSpec.getEncoded();

		
		String publicKey = Base64Encode.encode(encodedPublic);
		String privateKey = Base64Encode.encode(encodedPrivate);
		
		CPPModel.KeyPair pair = new CPPModel.KeyPair(publicKey, privateKey);
		return pair;
	}
	
	private static PrivateKey ReadPrivateKey(String string) throws Exception {
		byte[] keyBytes = Base64Encode.decode(string);
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
	    KeyFactory kf = KeyFactory.getInstance(Algorithm);
	    return kf.generatePrivate(spec);
	}
	
	private static PublicKey ReadPublicKey(String string) throws Exception {
		byte[] keyBytes = Base64Encode.decode(string);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
	    KeyFactory kf = KeyFactory.getInstance(Algorithm);
	    return kf.generatePublic(spec);
	}
}
