package org.mocha.interfaces;

import java.awt.Graphics2D;

public interface ILogic {
    public void start();
    public void update(double deltaTime);
    public void draw(Graphics2D g2);
}
