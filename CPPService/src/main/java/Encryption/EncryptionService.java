package Encryption;

import CPPModel.AuthenticationTokenObject;
import CPPModel.KeyPair;
import CPPModel.MemberObject;

public class EncryptionService implements IEncryptionService {

	public boolean MemberPasswordMatches(String privateKey, MemberObject member, String encryptedPassword) {
		// TODO Auto-generated method stub
		return false;
	}

	public AuthenticationTokenObject GenerateToken(String privateKey, MemberObject member, String encryptedPassword) {
		// TODO Auto-generated method stub
		return null;
	}

	public KeyPair GenerateKeyPair() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
