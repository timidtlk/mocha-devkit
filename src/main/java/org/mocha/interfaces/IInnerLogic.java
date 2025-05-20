package org.mocha.interfaces;

import java.awt.Graphics2D;

public interface IInnerLogic extends ILogic {
    /**
     * This method must be final. Override {@link #start()} instead.
     */
    public default void innerStart() {
        start();
    };
    /**
     * This method must be final. Override {@link #update()} instead.
     */
    public default void innerUpdate(double deltaTime) {
        update(deltaTime);
    };
    /**
     * This method must be final. Override {@link #draw()} instead.
     */
    public default void innerDraw(Graphics2D g2) {
        draw(g2);
    };
}
