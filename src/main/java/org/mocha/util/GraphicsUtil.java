package org.mocha.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.mocha.enums.AnchorPoint;
import org.mocha.util.math.Vector2;

public class GraphicsUtil {

    public static void drawRotatedImage(BufferedImage image, double x, double y, double rotation, Vector2 scale, AnchorPoint anchor, Graphics2D g2) {
        int imgWidth = (int) Math.round(image.getWidth() * scale.getX());
        int imgHeight = (int) Math.round(image.getHeight() * scale.getY());

        // Save the original transform
        var oldTransform = g2.getTransform();

        // Create a transform for rotating around the image center
        g2.translate(x, y);
        g2.rotate(rotation);
        g2.translate(-imgWidth * anchor.getX(), -imgHeight * anchor.getY());

        g2.drawImage(image, 0, 0, imgWidth, imgHeight, null);

        // Restore the original transform
        g2.setTransform(oldTransform);
    }

    public static void drawRotatedRect(double x, double y, int width, int height, double rotation, AnchorPoint anchor, Graphics2D g2) {
        // Save the original transform
        var oldTransform = g2.getTransform();

        // Create a transform for rotating around the image center
        g2.translate(x, y);
        g2.rotate(rotation);
        g2.translate(-width * anchor.getX(), -height * anchor.getY());

        g2.fillRect(0, 0, width, height);

        // Restore the original transform
        g2.setTransform(oldTransform);
    }
}
