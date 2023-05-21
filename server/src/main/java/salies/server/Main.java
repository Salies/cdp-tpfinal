package salies.server;

import salies.server.hashing.Hash;
import salies.server.hashing.VerifyHash;
import salies.server.network.ProfileRenderer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
        /*
        Sin s = new Sin(0, 360, 1, "sin");
        Image res = s.execute();
        // save image
        ImageIO.write((BufferedImage) res, "png", new java.io.File("line.png"));
        */

        /*
        ProfileRenderer pr = new ProfileRenderer("Purah", "purah", "all thins sheikah <3", "Hyrule", 1, 333, 2);
        String res = pr.execute();
        // Save string to test.html
        java.io.File file = new java.io.File("test.html");
        java.io.FileWriter writer = new java.io.FileWriter(file);
        writer.write(res);
        writer.close();
         */

        String testString = "batata";
        Hash h = new Hash("MD5", testString);
        String res = h.execute();
        System.out.println(res);
        VerifyHash vh = new VerifyHash("MD5", testString, res);
        boolean res2 = vh.execute();
        System.out.println(res2);
        h = new Hash("SHA-1", testString);
        res = h.execute();
        System.out.println(res);
        vh = new VerifyHash("SHA-1", testString, res);
        res2 = vh.execute();
        System.out.println(res2);
        h = new Hash("SHA-256", testString);
        res = h.execute();
        System.out.println(res);
        vh = new VerifyHash("SHA-256", testString, res);
        res2 = vh.execute();
        System.out.println(res2);
    }
}