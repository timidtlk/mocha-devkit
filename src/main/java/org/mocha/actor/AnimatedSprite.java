package org.mocha.actor;

import java.awt.Graphics2D;

import org.mocha.animation.AnimationManager;
import org.mocha.util.GraphicsUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class AnimatedSprite extends Actor {
    @NonNull
    private AnimationManager animationManager;

    public AnimatedSprite(double x, double y, @NonNull AnimationManager animationManager) {
        super(x, y);
        this.animationManager = animationManager;
    }

    @Override
    public void update(double deltaTime) {
        animationManager.updateActualAnimation();
    }    

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);

        var image = animationManager.getActualSprite();
        GraphicsUtil.drawRotatedImage(image, getX(), getY(), rotation, g2);
    }
}
