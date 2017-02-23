package CPPModel;

import java.util.Date;
import java.util.UUID;

public class AuthenticationTokenObject {
	public final String Token;
	public final UUID UserId;
	public final Date ExpirationTime;
	
	public AuthenticationTokenObject(String token, UUID userId, Date expirationTime) {
		Token = token;
		UserId = userId;
		ExpirationTime = expirationTime;
	}

	public boolean Matches(UUID id, String tokenString) {
		Date now = new Date();
		if(now.after(ExpirationTime)) {
			return false;
		}
		return (id.compareTo(UserId) == 0) &&
			(Token.compareTo(tokenString) == 0);
	}
}
