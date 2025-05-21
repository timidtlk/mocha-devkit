package org.mocha.interfaces;

import java.awt.Graphics2D;

public interface IInnerLogic extends ILogic {
    /**
     * <p>Implementations of this method must call {@link #start()} or do a {@code super()} call and be {@code final}.</p>
     * Overriding this method is discouraged and reserved to the engine. Override {@link #start()} instead.
     */
    public default void innerStart() {
        start();
    };
    /**
     * <p>Implementations of this method must call {@link #update(double)} or do a {@code super()} call and be {@code final}.</p>
     * Overriding this method is discouraged and reserved to the engine. Override {@link #update(double)} instead.
     */
    public default void innerUpdate(double deltaTime) {
        update(deltaTime);
    };
    /**
     * <p>Implementations of this method must call {@link #draw(Graphics2D)} or do a {@code super()} call and be {@code final}.</p>
     * Overriding this method is discouraged and reserved to the engine. Override {@link #draw(Graphics2D)} instead.
     */
    public default void innerDraw(Graphics2D g2) {
        draw(g2);
    };
}
