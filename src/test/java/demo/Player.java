package demo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;

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
    private SoundManager sound;

    private int speed;

    public Player(double x, double y, InputManager input, SoundManager sound) {
        super(x, y, 32, 32);
        this.input = input;
        this.sound = sound;
        speed = 30;
        debugColor = new Color(255, 0, 0, 50);
        anchor = AnchorPoint.MIDDLE_CENTER;

        SpriteSheet spriteSheet = null;
        try {
            spriteSheet = new SpriteSheet("sprites/bosta.png", 32, 32);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Animation animation = new Animation(spriteSheet, 1);
        HashMap<String, Animation> animations = new HashMap<>();
        animations.put("unique", animation);

        this.sprite = new AnimatedSprite(getX(), getY(), new AnimationManager("unique", animations));

        addChildren(sprite);
    }

    @Override
    public void update(double deltaTime) {
        if (input.getInputStatus("right") == 1) {
            sound.play("sounds/applause.wav");
        } else if (input.getInputStatus("left") == 1) {
            sound.pause("sounds/applause.wav");
        }

        sprite.innerUpdate(deltaTime);

        velocity.set(
            input.getInputStatus("right") - input.getInputStatus("left"),
            input.getInputStatus("down") - input.getInputStatus("up")
        );
        velocity.fastNormalize();
        velocity.multiply(speed);

        rotate(Math.toRadians(20 * deltaTime));
    }

    @Override
    public void draw(Graphics2D g2) {
        sprite.innerDraw(g2);
    }
}
