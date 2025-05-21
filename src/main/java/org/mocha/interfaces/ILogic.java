package org.mocha.interfaces;

import java.awt.Graphics2D;

public interface ILogic {
    /**
     * This method is intended to be overrided and only called by {@link IInnerLogic#innerStart()}.
     */
    public void start();
    /**
     * This method is intended to be overrided and only called by {@link IInnerLogic#innerUpdate(double)}.
     */
    public void update(double deltaTime);
    /**
     * This method is intended to be overrided and only called by {@link IInnerLogic#innerDraw(Graphics2D)}.
     */
    public void draw(Graphics2D g2);
}
