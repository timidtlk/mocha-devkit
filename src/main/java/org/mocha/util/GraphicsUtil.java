package org.mocha.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class GraphicsUtil {
    public static void drawRotatedImage(BufferedImage image, double x, double y, double rotation, Graphics2D g2) {
        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();

        // Save the original transform
        var oldTransform = g2.getTransform();

        // Create a transform for rotating around the image center
        g2.translate(x, y);
        g2.rotate(rotation);
        g2.translate(-imgWidth / 2.0, -imgHeight / 2.0);

        g2.drawImage(image, 0, 0, null);

        // Restore the original transform
        g2.setTransform(oldTransform);
    }

    public static void drawRotatedRect(double x, double y, int width, int height, double rotation, Graphics2D g2) {
        // Save the original transform
        var oldTransform = g2.getTransform();

        // Create a transform for rotating around the image center
        g2.translate(x, y);
        g2.rotate(rotation);
        g2.translate(-width / 2.0, -height / 2.0);

        g2.fillRect(0, 0, width, height);

        // Restore the original transform
        g2.setTransform(oldTransform);
    }
}
