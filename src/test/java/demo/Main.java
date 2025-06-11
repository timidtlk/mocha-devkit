package demo;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.mocha.Application;
import org.mocha.actor.Sprite;
import org.mocha.annotations.Multithreading;
import org.mocha.annotations.Window;
import org.mocha.util.platform.Resources;

@Window(width = 640, height = 480, title = "Test", fullScreen = true)
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
        Sprite spr2 = null;
        try {
            var teste = Resources.getImage("sprites/background-night.png");
            spr = new Sprite(getScreenWidth(), 0, teste) {
                @Override
                public void update(double deltaTime) {
                    this.velocity.setX(-5);
                }
            };
            spr2 = new Sprite(getScreenWidth()+teste.getWidth(), 0, teste) {
                @Override
                public void update(double deltaTime) {
                    this.velocity.setX(-5);
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        spr.setZ(1);
        scene.addActor(spr);
        scene.addActor(spr2);
        
        init();
    }

    public static void main(String[] args) {
        new Main();
    }
}
