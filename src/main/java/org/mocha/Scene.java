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

        while (count.get() > 0) {
            try {
                synchronized (oThread) {
                    oThread.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        actors.forEach((actor) -> {
            actor.innerDraw(g2);
        });
    }
}
