package org.mocha.gui;

import org.mocha.actor.Actor;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CanvasLayer extends Actor{
    private int width;
    private int height;

    public CanvasLayer(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    protected void addChild(Actor agent, boolean sort) {
        super.addChild(agent, sort);

        if (agent instanceof GUIActor) {
            var guiAgent = (GUIActor) agent;
            guiAgent.setCanvasLayer(this);
        }
    }

    @Override
    public void removeChild(Actor agent) {
        super.removeChild(agent);

        if (agent instanceof GUIActor) {
            var guiAgent = (GUIActor) agent;
            guiAgent.setCanvasLayer(null);
        }
    }
}
