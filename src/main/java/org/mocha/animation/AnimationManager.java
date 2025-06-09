package org.mocha.animation;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnimationManager {
    protected Animation actualAnimation;
    protected HashMap<String, Animation> animations;

    public AnimationManager(String initialAnimation, HashMap<String, Animation> animations) {
        this.animations = animations;

        actualAnimation = animations.get(initialAnimation);
        actualAnimation.start();
    }

    public AnimationManager(String animationName, Animation initialAnimation) {
        actualAnimation = initialAnimation;
        actualAnimation.start();

        animations = new HashMap<>();
        animations.put(animationName, initialAnimation);
    }

    /**
     * Initialize this Animation Manager with the given animation named as {@code "initial"}
     */
    public AnimationManager(Animation initialAnimation) {
        this("initial", initialAnimation);
    }

    public void setActualAnimation(String newAnimation) {
        if (actualAnimation != null) actualAnimation.stop();
        actualAnimation = animations.get(newAnimation);
        actualAnimation.start();
    }

    public void resetActualAnimation() {
        actualAnimation.reset();
    }

    public void restartActualAnimation() {
        actualAnimation.restart();
    }

    public void stopActualAnimation() {
        actualAnimation.stop();
    }

    public void startActualAnimation() {
        actualAnimation.start();
    }

    public void updateActualAnimation(double deltaTime) {
        actualAnimation.update(deltaTime);
    }

    public BufferedImage getActualSprite() {
        return actualAnimation.getSprite();
    }

    public static SingleAnimationManager singleAnimationManager(Animation single) {
        return new SingleAnimationManager(single);
    }

    protected static final class SingleAnimationManager extends AnimationManager {

        public SingleAnimationManager(Animation single) {
            actualAnimation = single;
            actualAnimation.start();
            animations = null;
        }

        public void setActualAnimation(Animation newAnimation) {
            actualAnimation = newAnimation;
        }

        @Override
        public void setActualAnimation(String newAnimation) throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setAnimations(HashMap<String, Animation> animations) throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }
    }
}
