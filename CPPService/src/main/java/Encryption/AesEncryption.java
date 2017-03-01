package Encryption;

import java.io.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class AesEncryption implements IEncryptionStrategy {
	final String algorithm = "AES";
	final int KeySize = 128;
	private Cipher _enc;
	private Cipher _dec;
	
	@Override
	public CPPModel.KeyPair GenerateKeyPair() throws Exception {
		KeyGenerator generator = KeyGenerator.getInstance(algorithm);
		generator.init(KeySize);
		SecretKey key = generator.generateKey();
		byte[] encodedKey = key.getEncoded();
		String secretKey = Base64Encode.encode(encodedKey);
		return new CPPModel.KeyPair("", secretKey);
	}

	@Override
	public void SetKey(CPPModel.KeyPair pair) throws Exception {
		byte[] decodedKey = Base64Encode.decode(pair.PrivateKey);
		byte[] paddedKey = new byte[KeySize/8];
		System.arraycopy(decodedKey, 0, paddedKey, 0, decodedKey.length);
		
		
		SecretKeySpec secretKey = new SecretKeySpec(paddedKey, algorithm);
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		_enc = cipher;
		cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		_dec = cipher;
	}

	@Override
	public String Encrypt(String text) throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, _enc);
		cipherOutputStream.write(text.getBytes());
		cipherOutputStream.flush();
		cipherOutputStream.close();
		byte[] encryptedBytes = outputStream.toByteArray();
		int encryptedSize = encryptedBytes.length;
		if(encryptedSize == 0 && text.length() > 0){
			throw new Exception("no data encrypted");
		}
		return Base64Encode.encode(encryptedBytes);
	}

	@Override
	public String Decrypt(String text) throws Exception {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Encode.decode(text));
		CipherInputStream cipherInputStream = new CipherInputStream(inputStream, _dec);
		byte[] buffer = new byte[512];
		int len;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		while((len = cipherInputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, len);
		}
		outputStream.flush();
		outputStream.close();
		cipherInputStream.close();
		inputStream.close();
		byte[] decryptedData = outputStream.toByteArray();
		String decryptedString = new String(decryptedData);
		return decryptedString;
	}
}
