package backend.tokens;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {

    public static String generateTokenValue() {
        var randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    private TokenGenerator() { }

}
