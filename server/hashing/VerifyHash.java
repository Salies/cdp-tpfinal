package server.hashing;

import compute.Task;

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
        return HashFunction.check(algorithm, input, hash);
    }

    public Boolean execute() {
        return check();
    }
}
