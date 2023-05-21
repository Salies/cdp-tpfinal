package salies.server;

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

        ProfileRenderer pr = new ProfileRenderer("Purah", "purah", "all thins sheikah <3", "Hyrule", 1, 333, 2);
        String res = pr.execute();
        // Save string to test.html
        java.io.File file = new java.io.File("test.html");
        java.io.FileWriter writer = new java.io.FileWriter(file);
        writer.write(res);
        writer.close();
    }
}