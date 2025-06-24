package demo;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.mocha.Application;
import org.mocha.actor.Sprite;
import org.mocha.annotations.Multithreading;
import org.mocha.annotations.Window;
import org.mocha.gui.CanvasLayer;
import org.mocha.gui.Label;

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

        Sprite spr = null;
        Sprite spr2 = null;
        try {
            spr = new Sprite("sprites/background-night.png", getScreenWidth(), 0) {
                @Override
                public void update(double deltaTime) {
                    this.velocity.setX(-5);
                }
            };
            spr2 = new Sprite("sprites/background-night.png", getScreenWidth(), 0) {
                @Override
                public void start() {
                    position.sum(getWidth(), 0);
                }

                @Override
                public void update(double deltaTime) {
                    this.velocity.setX(-5);
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        spr.setZ(1);
        spr2.setZ(1);

        var cl = new CanvasLayer(getScreenWidth(), getScreenHeight());
        cl.setZ(-1);
        var label = new Label("ola");
        label.autoSize();
        label.setLocalPosition(100, 100);

        cl.addChild(label);

        scene.addActors(player, spr, spr2, cl);
        
        init();
    }

    public static void main(String[] args) {
        new Main();
    }
}
