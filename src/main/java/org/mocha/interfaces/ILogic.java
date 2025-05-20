package org.mocha.interfaces;

import java.awt.Graphics2D;

public interface ILogic {
    /**
     * You should call {@link #innerStart()} if there is it.
     */
    public void start();
    /**
     * You should call {@link #innerUpdate())} if there is it.
     */
    public void update(double deltaTime);
    /**
     * You should call {@link #innerDraw()} if there is it.
     */
    public void draw(Graphics2D g2);
}
