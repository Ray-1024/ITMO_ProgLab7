package ray1024.projects.collectioncontroller.server;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptoController {

    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);

    public static String getPasswordHash(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD2");
            byte[] bytes = messageDigest.digest(password.getBytes());
            byte[] hexChars = new byte[bytes.length * 2];
            for (int j = 0; j < bytes.length; j++) {
                int v = bytes[j] & 0xFF;
                hexChars[j * 2] = HEX_ARRAY[v >>> 4];
                hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
            }
            return new String(hexChars, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
