package org.mocha.animation;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.mocha.util.platform.Resources;

public class SpriteSheet {
    private BufferedImage spriteSheet;
    private int xTileSize;
    private int yTileSize;
    private int xTileCount;
    private int yTileCount;

    public SpriteSheet(int xTileSize, int yTileSize, String path) throws NullPointerException, IOException {
        this.spriteSheet = loadSprite(path);
        this.xTileSize = xTileSize;
        this.yTileSize = yTileSize;

        this.xTileCount = spriteSheet.getWidth() / xTileSize;
        this.yTileCount = spriteSheet.getHeight() / yTileSize;
    }

    public SpriteSheet(String path, int xTileCount, int yTileCount) throws NullPointerException, IOException {
        this.spriteSheet = loadSprite(path);
        this.xTileCount = xTileCount;
        this.yTileCount = yTileCount;

        this.xTileSize = spriteSheet.getWidth() / xTileCount;
        this.yTileSize = spriteSheet.getHeight() / yTileCount;
    }

    public BufferedImage loadSprite(String file) throws IOException, NullPointerException {

        BufferedImage sprite = null;

        sprite = Resources.getImage(file);

        if (sprite == null) {
            throw new NullPointerException("Unable to create sprite, spriteSheet is null.");
        }

        return sprite;
    }

    public BufferedImage[] getSplittedSpriteSheet() {
        BufferedImage[] image = new BufferedImage[xTileCount * yTileCount]; 

        try {
            int k = 0;
            for (int i = 0; i < xTileCount; i++) {
                for (int j = 0; j < yTileCount; j++) {
                    image[k++] = getSprite(i, j);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    public BufferedImage getSprite(int xGrid, int yGrid) throws Exception {
        return this.spriteSheet.getSubimage(xGrid * xTileSize, yGrid * yTileSize, xTileSize, yTileSize);
    }

    public int getWidth() {
        return spriteSheet.getWidth();
    }

    public int getHeight() {
        return spriteSheet.getHeight();
    }
}