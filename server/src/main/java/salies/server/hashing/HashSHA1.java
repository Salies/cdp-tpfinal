package salies.server.hashing;
import java.security.MessageDigest;

public class HashSHA1 extends HashFunction {
    public String hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", (b & 0xff)));
            }
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }
}
