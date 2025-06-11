package org.mocha.inputs;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import org.mocha.Application;

public class ComponentHandler extends ComponentAdapter {
    private Application app;
    
    public ComponentHandler(Application app) {
        this.app = app;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        // Calculate the scale factor to maintain 4:3 aspect ratio
        var scaleX = (double) app.getWidth() / app.getScreenWidth();
        var scaleY = (double) app.getHeight() / app.getScreenHeight();
        app.setScale(Math.min(scaleX, scaleY)); // Use the smaller scale to fit within the window
    }
}
