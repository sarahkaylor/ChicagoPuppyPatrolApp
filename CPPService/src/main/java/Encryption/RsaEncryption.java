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
		byte[] data = _enc.doFinal(text.getBytes());
		String encryptedText = new String(data);
		return encryptedText;
	}

	@Override
	public String Decrypt(String encryptedText) throws Exception {
		byte[] data = _dec.doFinal(encryptedText.getBytes());
		String decryptedText = new String(data);
		return decryptedText;
	}

	@Override
	public CPPModel.KeyPair GenerateKeyPair() throws Exception {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance(Algorithm);
		keyGen.initialize(KeySize);
		KeyPair key = keyGen.generateKeyPair();
		String privateKey = key.getPrivate().getFormat();
		String publicKey = key.getPublic().getFormat();
		CPPModel.KeyPair pair = new CPPModel.KeyPair(publicKey, privateKey);
		return pair;
	}
	
	private static PrivateKey ReadPrivateKey(String string) throws Exception {
		byte[] keyBytes = string.getBytes(); 
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
	    KeyFactory kf = KeyFactory.getInstance(Algorithm);
	    return kf.generatePrivate(spec);
	}
	
	private static PublicKey ReadPublicKey(String string) throws Exception {
		byte[] keyBytes = string.getBytes();
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
	    KeyFactory kf = KeyFactory.getInstance(Algorithm);
	    return kf.generatePublic(spec);
	}
}
