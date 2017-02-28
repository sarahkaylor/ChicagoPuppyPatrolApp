package Encryption;

import CPPModel.AuthenticationTokenObject;
import CPPModel.KeyPair;
import CPPModel.MemberObject;

public class EncryptionService implements IEncryptionService {

	
	public boolean MemberPasswordMatches(String privateKey, MemberObject member, String encryptedPassword) {
		return member.EncryptedPassword.compareTo(encryptedPassword) == 0;
	}

	public AuthenticationTokenObject GenerateToken(String privateKey, MemberObject member, String encryptedPassword) {
		// TODO Auto-generated method stub
		return null;
	}

	public KeyPair GenerateKeyPair() throws Exception {
		return new RsaEncryption().GenerateKeyPair();
	}
}
