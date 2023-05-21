package salies.server.hashing;

public abstract class HashFunction {
    abstract String hash(String input);
    protected Boolean check(String input, String hash) {
        return hash(input).equals(hash);
    }
}
