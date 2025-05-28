package org.mocha.animation;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.mocha.util.platform.Resources;

public class SpriteSheet {
    private BufferedImage spriteSheet;
    private int xTile;
    private int yTile;

    public SpriteSheet(String path, int xTile, int yTile) throws NullPointerException, IOException {
        this.spriteSheet = loadSprite(path);
        this.xTile = xTile;
        this.yTile = yTile;
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
        int parsedW = getWidth() / xTile, parsedH = getHeight() / yTile;

        BufferedImage[] image = new BufferedImage[parsedW * parsedH]; 

        try {
            int k = 0;
            for (int i = 0; i < parsedW; i++) {
                for (int j = 0; j < parsedH; j++) {
                    image[k++] = getSprite(i, j);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    public BufferedImage getSprite(int xGrid, int yGrid) throws Exception {

        return this.spriteSheet.getSubimage(xGrid * xTile, yGrid * yTile, xTile, yTile);
    }

    public int getWidth() {
        return spriteSheet.getWidth();
    }

    public int getHeight() {
        return spriteSheet.getHeight();
    }
}