package org.mocha;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

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
        var futures = new ArrayList<Future<Boolean>>();

        actors.forEach((actor) -> {
            var f = ThreadMan.submit(() -> {
                actor.innerUpdate(deltaTime);
                return true;
            });
            futures.add(f);            
        });

        while (futures.size() > 0) {
            for (int i = futures.size() - 1; i >= 0; i--) {
                var f = futures.get(i);

                if (f.isDone()) {
                    futures.remove(i);
                    break;
                }
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
