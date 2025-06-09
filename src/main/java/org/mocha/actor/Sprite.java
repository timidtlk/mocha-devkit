package org.mocha.actor;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.mocha.util.GraphicsUtil;
import org.mocha.util.platform.Resources;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class Sprite extends Actor {
    @NonNull
    private BufferedImage sprite;

    public Sprite(double x, double y, @NonNull BufferedImage sprite) {
        super(x, y);
        this.sprite = sprite;
    }

    public Sprite(@NonNull String path, double x, double y) {
        super(x, y);
        try {
            this.sprite = Resources.getImage(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        GraphicsUtil.drawRotatedImage(sprite, getX(), getY(), rotation, scale, anchor, g2);
    }
}
