package server.hashing;

import compute.Task;

import java.io.Serializable;

public class Hash implements Task<String>, Serializable {
    private static final long serialVersionUID = 666L;

    private String algorithm;
    private String input;

    public Hash(String algorithm, String input) {
        this.algorithm = algorithm;
        this.input = input;
    }

    private String hash() {
        return HashFunction.hash(algorithm, input);
    }

    public String execute() {
        return hash();
    }
}
