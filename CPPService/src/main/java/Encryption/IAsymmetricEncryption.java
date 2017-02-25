package Encryption;

import CPPModel.KeyPair;

public interface IAsymmetricEncryption {
	public void SetKey(KeyPair pair) throws Exception;
	public KeyPair GenerateKeyPair() throws Exception;
	public String Encrypt(String text) throws Exception;
	public String Decrypt(String encryptedText) throws Exception;
}
