package org.mocha;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.mocha.actor.Actor;
import org.mocha.interfaces.ILogic;
import org.mocha.util.Counter;

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

    private void addActor(Actor actor, boolean sort) {
        actors.add(actor);
        if (sort) actors.sort(null);
    }

    public void addActor(Actor actor) {
        addActor(actor, true);
    }

    public void addActors(Actor... actors) {
        for (int i = 0; i < actors.length; i++) {
            addActor(actors[i], false);
        }
        this.actors.sort(null);
    }

    @Override
    public void start() {
        for (int i = actors.size() - 1; i >= 0; i--) {
            actors.get(i).innerStart();
        }
    }

    @Override
    public void update(double deltaTime) {
        if (!Application.getMultithreading()) {
            for (int i = actors.size() - 1; i >= 0; i--) {
                actors.get(i).innerUpdate(deltaTime);
            }
            return;
        }

        final var count = new Counter(actors.size());
        final var oThread = Thread.currentThread();

        actors.forEach(actor -> {
            ThreadMan.execute(() -> {
                actor.innerUpdate(deltaTime);
                count.decrement();

                synchronized (oThread) {
                    oThread.notify();
                }
            });
        });

        synchronized (oThread) {
            while (count.get() > 0) {
                try {
                    oThread.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        for (int i = actors.size() - 1; i >= 0; i--) {
            actors.get(i).innerDraw(g2);
        }
    }
}
