import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import org.mocha.actor.AnimatedSprite;
import org.mocha.actor.Box;
import org.mocha.animation.Animation;
import org.mocha.animation.AnimationManager;
import org.mocha.animation.SpriteSheet;
import org.mocha.annotations.ShowHitbox;
import org.mocha.inputs.InputManager;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ShowHitbox
public class Player extends Box {
    protected AnimatedSprite sprite;
    private InputManager input;

    private int speed;

    public Player(double x, double y, InputManager input) {
        super(x, y, 32, 32);
        this.input = input;
        speed = 500;
        debugColor = new Color(255, 0, 0, 50);

        SpriteSheet spriteSheet = new SpriteSheet("sprites/bosta.png", 32, 32);

        BufferedImage[] image = null; 
        try {
            image = new BufferedImage[]{spriteSheet.getSprite(0, 0)};
        } catch (Exception e) {
            e.printStackTrace();
        }
        Animation animation = new Animation(image, 1);
        HashMap<String, Animation> animations = new HashMap<>();
        animations.put("unique", animation);

        this.sprite = new AnimatedSprite(getX(), getY(), new AnimationManager("unique", animations));

        addChildren(sprite);
    }

    @Override
    public void update(double deltaTime) {
        sprite.innerUpdate(deltaTime);

        velocity.set(
            input.getInputStatus("right") - input.getInputStatus("left"),
            input.getInputStatus("down") - input.getInputStatus("up")
        );
        velocity.fastNormalize();
        velocity.multiply(speed);

        // rotate(Math.toRadians(2));
    }

    @Override
    public void draw(Graphics2D g2) {
        sprite.innerDraw(g2);
    }
}
