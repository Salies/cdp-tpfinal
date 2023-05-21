package salies.server;

import salies.server.plotter.Cubic;
import salies.server.plotter.Sin;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
        Sin s = new Sin(0, 360, 1, "sin");
        Image res = s.execute();
        // save image
        ImageIO.write((BufferedImage) res, "png", new java.io.File("line.png"));
    }
}