package Encryption;

import java.security.*;
import java.security.spec.*;
import javax.crypto.*;
import java.io.*;

public class RsaEncryption implements IEncryptionStrategy {
	private final static int KeySize = 4096;
	private final static String Algorithm = "RSA";
	private final static int BlockSize = 128;
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
		StringInputStream input = new StringInputStream(text);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		DataOutputStream data = new DataOutputStream(output);
		byte[] inputBuffer = new byte[BlockSize];
		int len;
		while((len = input.read(inputBuffer, 0, BlockSize)) > 0) {
			ByteArrayOutputStream tmp = new ByteArrayOutputStream();
			CipherOutputStream cipher = new CipherOutputStream(tmp, _enc);
			cipher.write(inputBuffer, 0, len);
			cipher.flush();
			cipher.close();
			byte[] enc = tmp.toByteArray();
			int encSize = enc.length;
			if(encSize == 0) {
				throw new Exception("failed to encrypt data");
			}
			data.writeInt(encSize);
			data.write(enc);
		}
		data.writeInt(-1);
		data.flush();
		input.close();
		return Base64Encode.encode(output.toByteArray());
	}

	@Override
	public String Decrypt(String encryptedText) throws Exception {
		StringOutputStream output = new StringOutputStream();
		ByteArrayInputStream input = new ByteArrayInputStream(Base64Encode.decode(encryptedText));
		DataInputStream data = new DataInputStream(input);
		int chunkSize;
		byte[] decryptionBuffer = new byte[BlockSize];
		while((chunkSize = data.readInt()) > 0) {
			byte[] encryptionChunk = new byte[chunkSize];
			data.read(encryptionChunk);
			ByteArrayInputStream tmp = new ByteArrayInputStream(encryptionChunk);
			CipherInputStream cipher = new CipherInputStream(tmp, _dec);
			int readLen = cipher.read(decryptionBuffer);
			cipher.close();
			tmp.close();
			output.write(decryptionBuffer, 0, readLen);
		}
		output.flush();
		output.close();
		return output.toString();
	}

	@Override
	public CPPModel.KeyPair GenerateKeyPair() throws Exception {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance(Algorithm);
		keyGen.initialize(KeySize);
		KeyPair key = keyGen.generateKeyPair();

		X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(key.getPublic().getEncoded());
		PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(key.getPrivate().getEncoded());

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
