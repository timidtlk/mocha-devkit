package org.mocha.actor;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.mocha.util.GraphicsUtil;

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

    @Override
    public void draw(Graphics2D g2) {
        GraphicsUtil.drawRotatedImage(sprite, getX(), getY(), rotation, scale, anchor, g2);
    }
}
