package CPPDataAccess;

import java.util.*;

import com.google.gson.Gson;

import CPPModel.*;
import Encryption.*;
import Util.*;

public class EncryptingSerializer implements IObjectSerializer {

	private final Gson _gson;
	private final ILazy<IEncryptionStrategy> _enc;
	
	public EncryptingSerializer(UUID userId, IAuthenticationDataAccess authDataAccess) {
		_gson = new Gson();
		_enc = new Lazy<IEncryptionStrategy>(new CreateEnc(userId, authDataAccess));
	}
	
	@Override
	public String ToJson(Object src) throws Exception {
		String json = _gson.toJson(src);
		String encrypted = _enc.Value().Encrypt(json);
		return encrypted;
	}

	@Override
	public <T> T FromJson(String encryptedJson, Class<T> classOfT) throws Exception {
		String json = _enc.Value().Decrypt(encryptedJson);
		T obj = _gson.fromJson(json, classOfT);
		return obj;
	}
	
	private class CreateEnc implements IEvaluationStrategy<IEncryptionStrategy> {
		private final IAuthenticationDataAccess _authDataAccess;
		private final UUID _userId;
		
		public CreateEnc(UUID userId, IAuthenticationDataAccess authDataAccess) {
			_authDataAccess = authDataAccess;
			_userId = userId;
		}
		
		@Override
		public IEncryptionStrategy Evaluate() throws Exception {
			IEncryptionStrategy enc = new AesEncryption();
			KeyPair key = _authDataAccess.LoadAesSecretKey(_userId);
			enc.SetKey(key);
			return enc;
		}
	}

}
