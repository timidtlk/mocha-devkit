package demo;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import org.mocha.Application;
import org.mocha.actor.Sprite;
import org.mocha.annotations.Window;

@Window(width = 640, height = 480, title = "Test", defaultFps = 60)
public class Main extends Application {
    Player player;

    public Main() {
        input.addAction("up", KeyEvent.VK_UP);
        input.addAction("down", KeyEvent.VK_DOWN);
        input.addAction("left", KeyEvent.VK_LEFT);
        input.addAction("right", KeyEvent.VK_RIGHT);
        
        player = new Player(16, 16, input);
        scene.addActor(player);
        Sprite spr = null;
        try {
            BufferedImage teste = ImageIO.read(getClass().getClassLoader().getResource("sprites/bosta.png"));
            spr = new Sprite(50, 50, teste);
        } catch (Exception e) {
            e.printStackTrace();
        }
        scene.addActor(spr);
        
        init();
    }

    public static void main(String[] args) {
        new Main();
    }
}
