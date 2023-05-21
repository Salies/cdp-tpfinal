package salies.server.hashing;

public interface HashFunction {
    public String hash(String input);
    public Boolean check(String input, String hash);
}
