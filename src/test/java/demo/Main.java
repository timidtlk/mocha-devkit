package demo;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import org.mocha.Application;
import org.mocha.actor.Sprite;
import org.mocha.annotations.Multithreading;
import org.mocha.annotations.Window;
import org.mocha.util.platform.Resources;

@Window(width = 640, height = 480, title = "Test")
@Multithreading
public class Main extends Application {
    Player player;

    public Main() {
        input.addAction("up", KeyEvent.VK_UP);
        input.addAction("down", KeyEvent.VK_DOWN);
        input.addAction("left", KeyEvent.VK_LEFT);
        input.addAction("right", KeyEvent.VK_RIGHT);
        input.addAction("mouse3", MouseEvent.BUTTON3);
        
        player = new Player(16, 16, input);
        scene.addActor(player);
        Sprite spr = null;
        try {
            BufferedImage teste = Resources.getImage("sprites/bosta.png");
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
