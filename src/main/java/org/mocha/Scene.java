package org.mocha;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.mocha.actor.Actor;
import org.mocha.interfaces.ILogic;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Scene implements ILogic {
    private String name;
    private boolean active;
    private List<Actor> actors;

    public Scene() {
        this.name = "Scene";
        active = false;
        actors = new ArrayList<>();
    }

    public Scene(String name) {
        this.name = name;
        active = false;
        actors = new ArrayList<>();
    }

    public void addActor(Actor actor) {
        actors.add(actor);
    }

    @Override
    public void start() {
        actors.forEach((actor) -> {
            actor.innerStart();
        });
    }

    @Override
    public void update(double deltaTime) {
        actors.forEach((actor) -> {
            actor.innerUpdate(deltaTime);
        });
    }

    @Override
    public void draw(Graphics2D g2) {
        actors.forEach((actor) -> {
            actor.innerDraw(g2);
        });
    }
}
