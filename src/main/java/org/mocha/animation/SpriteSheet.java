package org.mocha.animation;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

    private BufferedImage spriteSheet;
    private int xTile;
    private int yTile;

    public SpriteSheet(String path, int xTile, int yTile) {
        this.spriteSheet = loadSprite(path);
        this.xTile = xTile;
        this.yTile = yTile;
    }

    public BufferedImage loadSprite(String file) {

        BufferedImage sprite = null;

        try {
            sprite = ImageIO.read(getClass().getClassLoader().getResource(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sprite;
    }

    public BufferedImage getSprite(int xGrid, int yGrid) throws Exception {

        if (this.spriteSheet == null) {
            throw new Exception("Unable to create sprite, spriteSheet equals null");
        }

        return this.spriteSheet.getSubimage(xGrid * xTile, yGrid * yTile, xTile, yTile);
    }

    public int getWidth() {
        return spriteSheet.getWidth();
    }
    public int getHeight() {
        return spriteSheet.getHeight();
    }

}