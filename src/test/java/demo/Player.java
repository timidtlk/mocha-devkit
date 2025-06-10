package demo;

import java.awt.Color;

import org.mocha.actor.AnimatedSprite;
import org.mocha.actor.Box;
import org.mocha.animation.Animation;
import org.mocha.animation.AnimationManager;
import org.mocha.animation.SpriteSheet;
import org.mocha.annotations.ShowHitbox;
import org.mocha.enums.AnchorPoint;
import org.mocha.inputs.InputManager;
import org.mocha.sound.SoundManager;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ShowHitbox
public class Player extends Box {
    protected AnimatedSprite sprite;
    private InputManager input;
    private SoundManager sound = new SoundManager();

    private int speed;

    public Player(double x, double y, InputManager input) {
        super(x, y, 32, 32);
        this.input = input;
        speed = 30;
        debugColor = new Color(255, 0, 0, 50);
        anchor = AnchorPoint.MIDDLE_CENTER;

        SpriteSheet spriteSheet = null;
        try {
            spriteSheet = new SpriteSheet("sprites/bosta.png", 1, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        var animation = new Animation(spriteSheet, 1);

        sprite = new AnimatedSprite(getX(), getY(), AnimationManager.singleAnimationManager(animation));
        addChildren(sprite);
    }

    @Override
    public void start() {
        sound.setVolumeLinear(0.1f);
        sound.load("sounds/blip.wav");
    }

    @Override
    public void update(double deltaTime) {
        if (input.getInputStatus("mouse3") == 1) {
            sound.play("sounds/blip.wav");
        }// else if (input.getInputStatus("left") == 1) {
         //   sound.pause("sounds/blip.wav");
        //}

        velocity.set(
            input.getInputStatus("right") - input.getInputStatus("left"),
            input.getInputStatus("down") - input.getInputStatus("up")
        );
        velocity.fastNormalize();
        velocity.multiply(speed);

        rotate(Math.toRadians(20 * deltaTime));
    }
}
