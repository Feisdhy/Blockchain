package blockchain.util;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

@Slf4j
public class AccountUtils {
    private static byte[] generateRandomBytes() {
        byte[] randomBytes = new byte[32];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomBytes);
        return randomBytes;
    }

    public static byte[] generateAddress() {
        try {
            MessageDigest message = MessageDigest.getInstance("SHA3-256");
            return Arrays.copyOfRange(message.digest(generateRandomBytes()), 0, 20);
        } catch (NoSuchAlgorithmException exception) {
            log.info(exception.getMessage());
            return new byte[] {};
        }
    }
}
