package org.mocha;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.mocha.annotations.Multithreading;
import org.mocha.annotations.UnlimitFPS;
import org.mocha.annotations.Window;
import org.mocha.exceptions.WindowNotDefinedException;
import org.mocha.inputs.InputManager;
import org.mocha.inputs.KeyHandler;
import org.mocha.inputs.MouseHandler;
import org.mocha.interfaces.ILogic;
import org.mocha.util.platform.Resources;

import com.formdev.flatlaf.FlatDarculaLaf;

import lombok.Getter;

/**
 * The main class of your game. You should extend this class and start making your game.
 */
public abstract class Application extends JPanel implements Runnable, ILogic {
    private static boolean multithreading = false;

    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private Thread thread;
    private JFrame frame;

    @Getter
    private int screenWidth;
    @Getter
    private int screenHeight;

    private boolean blackBars;
    private boolean resizable;
    private String title;
    
    private final long NANOSECOND = 1000000000L;
    private float FRAMERATE;
    private boolean fullScreen;

    private int fps;
    private float frameTime;
    private float frameDuration;

    private boolean limited = true;

    protected InputManager input;
    protected SceneManager scenes;
    protected Scene scene;

    private KeyHandler keyH;
    private MouseHandler mouseH;

    public Application() {
        System.setProperty("sun.java2d.opengl", "True");
        FlatDarculaLaf.setup();

        scene = new Scene();
        input = new InputManager();
        scenes = new SceneManager(scene);
        keyH = new KeyHandler(input);
        mouseH = new MouseHandler(input);
        
        try {
            if (getClass().isAnnotationPresent(Window.class)) {
                Window window = getClass().getAnnotation(Window.class);
                
                screenHeight = window.height();
                screenWidth = window.width();
                blackBars = window.blackBars();
                resizable = window.resizable();
                title = window.title();
                FRAMERATE = window.defaultFps();
                fullScreen = window.fullScreen();
            } else {
                throw new WindowNotDefinedException();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        if (getClass().isAnnotationPresent(Multithreading.class)) {
            multithreading = true;
        }

        if (getClass().isAnnotationPresent(UnlimitFPS.class)) {
            if (getClass().getAnnotation(UnlimitFPS.class).showMessage()) {                
                JOptionPane.showMessageDialog(
                    null, 
                    "WARNING: This is a debug feature only. Using this anotation can result in unstable performance.", 
                    "Warning", JOptionPane.WARNING_MESSAGE
                );
            } 
            limited = false;
        }

        frameDuration = 1.0f / FRAMERATE;
    }

    /**
     * Game initializer method. You may want to call it in the end of your constructor.
     */
    public void init() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setBackground(Color.BLACK);
        this.addKeyListener(keyH);
        this.addMouseListener(mouseH);

        start();

        initializeFrame();
    }

    @Override
    public void run() {
        int frames = 0;
        long frameCounter = 0;
        long lastTime = System.nanoTime();
        double unprocessedTime = 0;
        
        long lastTimeRendered = 0;

        while (thread != null) {
            boolean render = false;
            long startTime = System.nanoTime();
            long deltaTime = startTime - lastTime;
            lastTime = startTime;

            unprocessedTime += deltaTime / (double) NANOSECOND;
            frameCounter += deltaTime;

            if (limited) {
                while (unprocessedTime > frameDuration) {
                    render = true;
                    unprocessedTime -= frameDuration;
    
                    if (frameCounter >= NANOSECOND) {
                        fps = frames;
                        frame.setTitle(String.format("%s | FPS: %d | Frametime (ms): %.2fms", title, fps, frameTime));
                        frames = 0;
                        frameCounter = 0;
                    }
                }
    
                if (render) {
                    update(((System.nanoTime() - lastTimeRendered) / (double) NANOSECOND) * 10);
                    repaint();
                    frameTime = (System.nanoTime() - lastTimeRendered) / 1000000f;
                    frames++;
                    lastTimeRendered = System.nanoTime();
                    toolkit.sync();
                }
            } else {
                update(((System.nanoTime() - lastTimeRendered) / (double) NANOSECOND) * 10);
                repaint();
                frameTime = (System.nanoTime() - lastTimeRendered) / 1000000f;
                frames++;
                lastTimeRendered = System.nanoTime();

                if (frameCounter >= NANOSECOND) {
                    fps = frames;
                    frame.setTitle(String.format("%s | FPS: %d | Frametime (ms): %.2fms", title, fps, frameTime));
                    frames = 0;
                    frameCounter = 0;
                }
                toolkit.sync();
            }
        }
    }

    private void initializeFrame() {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(resizable);

        if (fullScreen) {
            frame.setUndecorated(true);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }

        thread = new Thread(this);
        thread.start();

        frame.add(this);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void start() {
        scene.start();
    }

    @Override
    public void update(double deltaTime) {
        scene.update(deltaTime);
    }

    @Override
    public void draw(Graphics2D g2) {
        scene.draw(g2);
    }

    public void setIcon(String path) {
        try {
            frame.setIconImage(Resources.getImage(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Get the window dimensions
        int windowWidth = getWidth();
        int windowHeight = getHeight();

        // Calculate the scale factor to maintain 4:3 aspect ratio
        double scaleX = (double) windowWidth / screenWidth;
        double scaleY = (double) windowHeight / screenHeight;
        double scale = Math.min(scaleX, scaleY); // Use the smaller scale to fit within the window

        // Calculate the scaled resolution
        int scaledWidth = (int) (screenWidth * scale);
        int scaledHeight = (int) (screenHeight * scale);

        // Calculate offsets for centering the game content
        int offsetX = (windowWidth - scaledWidth) / 2;
        int offsetY = (windowHeight - scaledHeight) / 2;

        var trans = g2.getTransform();

        // Scale and translate the game content
        g2.translate(offsetX, offsetY);
        g2.scale(scale, scale);

        // Render the game content
        draw(g2);

        // Reset transformations
        g2.setTransform(trans);

        if (blackBars) {
            // Fill the black bars
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, windowWidth, offsetY); // Top bar
            g2.fillRect(0, offsetY + scaledHeight, windowWidth, windowHeight - offsetY - scaledHeight); // Bottom bar
            g2.fillRect(0, offsetY, offsetX, scaledHeight); // Left bar
            g2.fillRect(offsetX + scaledWidth, offsetY, windowWidth - offsetX - scaledWidth, scaledHeight); // Right bar
        }
        
        // Dispose of the graphics context
        g2.dispose();
    }

    public static boolean getMultithreading() {
        return multithreading;
    }
}
