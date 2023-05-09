package vito.test;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

public class OAuthUtils {

    public static String generateCodeVerifier() {
        byte[] randomBytes = new byte[32];
        new Random().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    public static String generateCodeChallengeS256(String codeVerifier) throws NoSuchAlgorithmException {
        byte[] codeVerifierBytes = codeVerifier.getBytes(StandardCharsets.US_ASCII);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(codeVerifierBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hashBytes);
    }
}
