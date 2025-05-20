package org.mocha.animation;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimationManager {
    private Animation actualAnimation;
    private HashMap<String, Animation> animations;

    public AnimationManager(String initialAnimation, HashMap<String, Animation> animations) {
        this.animations = animations;
        this.actualAnimation = animations.get(initialAnimation);
    }

    public void setActualAnimation(String newAnimation) {
        actualAnimation.stop();
        actualAnimation = animations.get(newAnimation);
        actualAnimation.start();
    }

    public void resetActualAnimation() {
        actualAnimation.reset();
    }

    public void restartActualAnimation() {
        actualAnimation.restart();
    }

    public void updateActualAnimation() {
        actualAnimation.update();
    }

    public BufferedImage getActualSprite() {
        return actualAnimation.getSprite();
    }
}
