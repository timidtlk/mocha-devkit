package org.mocha.gui;

import java.awt.Graphics2D;

import org.mocha.actor.Actor;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class GUIActor extends Actor {
    private CanvasLayer canvasLayer;
    private boolean visible = true;

    /**
     * Overriding this method is discouraged and reserved to the engine. Override {@link #drawGUI(Graphics2D)} instead.
     */
    @Override
    public final void draw(Graphics2D g2) {
        if (getCanvasLayer() == null) return;
        if (!isVisible()) return;

        drawGUI(g2);
    }

    /**
     * This method is intended to be overrided and only called by {@link GUIActor#draw(Graphics2D)}.
     */
    public abstract void drawGUI(Graphics2D g2);
}
