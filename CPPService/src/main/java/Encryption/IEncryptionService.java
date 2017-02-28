package Encryption;

import CPPModel.AuthenticationTokenObject;
import CPPModel.KeyPair;
import CPPModel.MemberObject;

public interface IEncryptionService {
	public boolean MemberPasswordMatches(String privateKey, MemberObject member, String encryptedPassword);
	public AuthenticationTokenObject GenerateToken(String privateKey, MemberObject member, String encryptedPassword);
	public KeyPair GenerateKeyPair() throws Exception;
}
