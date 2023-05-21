package salies.server.hashing;

import salies.server.Task;

import java.io.Serializable;

public class VerifyHash implements Task<Boolean>, Serializable {
    private String algorithm;
    private String input;
    private String hash;

    public VerifyHash(String algorithm, String input, String hash) {
        this.algorithm = algorithm;
        this.input = input;
        this.hash = hash;
    }

    private Boolean check() {
        switch (algorithm) {
            case "md5":
                return new HashMD5().check(input, hash);
            case "sha1":
                return new HashSHA1().check(input, hash);
            default:
                return false;
        }
    }

    public Boolean execute() {
        return check();
    }
}
