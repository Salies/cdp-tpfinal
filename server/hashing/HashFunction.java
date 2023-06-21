package server.hashing;

import java.security.MessageDigest;

// Classe que implementa as funções de hash
public class HashFunction {
    // Faz o hash da string input usando o algoritmo algorithm
    public static String hash(String algorithm, String input) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }

    // Checa se o hash "hash" é o hash da string "input" usando o algoritmo "algorithm"
    public static Boolean check(String algorithm, String input, String hash) {
        return hash(algorithm, input).equals(hash);
    }
}
