package org.mocha.actor;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.mocha.enums.AnchorPoint;
import org.mocha.interfaces.IInnerLogic;
import org.mocha.util.FutureHandler;
import org.mocha.util.math.Mathf;
import org.mocha.util.math.Vector2;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Actor implements IInnerLogic, Comparable<Actor> {
    protected Vector2 position;
    protected Vector2 localPosition;
    protected Vector2 velocity;
    protected int z;

    protected double rotation;
    protected Vector2 scale;
    protected AnchorPoint anchor;

    protected List<Actor> children;
    protected List<FutureHandler<?>> futures;
    protected Actor parent;

    public Actor() {
        this(0, 0);
    }
    
    public Actor(double x, double y) {
        position = new Vector2(x, y);
        localPosition = new Vector2();
        velocity = new Vector2();
        z = 0;
        rotation = 0;
        scale = new Vector2(1, 1);
        anchor = AnchorPoint.TOP_LEFT;
        children = new ArrayList<>();
        futures = new ArrayList<>();
        parent = null;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public void translate(double x, double y) {
        position.translate(x, y);
    }

    public void translate(Vector2 transform) {
        position.translate(transform);
    }

    public void setPosition(double x, double y) {
        position.setX(x);
        position.setY(y);
    }

    public void setLocalPosition(double x, double y) {
        localPosition.setX(x);
        localPosition.setY(y);
    }

    public void rotateTo(Vector2 position, double factor) {
        position.subtract(this.position);

        var n = position.fastNormalized();

        rotation = Mathf.lerpAngle(rotation, Math.atan2(n.getY(), n.getX()), factor);
    }

    public void rotate(double radians) {
        rotation += radians;
    }

    protected void addChild(Actor agent, boolean sort) {
        children.add(agent);
        agent.setParent(this);
        agent.setAnchor(anchor);
        if (sort) children.sort(null);
    }

    public void addChild(Actor agent) {
        addChild(agent, true);
    }

    public void addChildren(Actor... agents) {
        for (int i = 0; i < agents.length; i++) {
            addChild(agents[i], false);
        }
        children.sort(null);
    }

    public int compareTo(Actor b) {
        return this.getZ() - b.getZ();
    }

    public void removeChildren(Actor agent) {
        children.remove(agent);
        agent.setParent(null);
    }

    public void getChild(Actor agent) {
        children.get(children.indexOf(agent));
    }

    public void getChild(int index) {
        children.get(index);
    }

    public double getX() {
        return position.getX();
    }

    public double getY() {
        return position.getY();
    }

    public void setX(double x) {
        position.setX(x);
    }

    public void setY(double y) {
        position.setY(y);
    }

    public void setZ(int z) {
        this.z = z;

        if (hasParent()) {
            parent.children.sort(null);
        }
    }

    @Override
    public final void innerStart() {
        start();

        for (int i = children.size() - 1; i >= 0; i--) {
            children.get(i).innerStart();
        }
    }

    @Override
    public void start() {}

    @Override
    public final void innerUpdate(double deltaTime) {
        position.sum(velocity.getX() * deltaTime, velocity.getY() * deltaTime);
        velocity.setX(0);
        velocity.setY(0);
        
        if (hasParent()) {
            this.position.set(parent.position);
            this.position.sum(localPosition);

            this.rotation = parent.rotation;
        }

        update(deltaTime);

        for (int i = children.size() - 1; i >= 0; i--) {
            children.get(i).innerUpdate(deltaTime);
        }

        for (int i = futures.size() - 1; i >= 0; i++) {
            futures.get(i).call();
            futures.remove(i);
        }
    }

    @Override
    public void update(double deltaTime) {}

    /**
     * @param callable A function to be executed after the update call.
     * @return {@link FutureHandler} A future where you can bind a consumer to be executed with the result of this call.
     */
    public <T> FutureHandler<T> afterUpdate(Callable<T> callable) {
        var fh = new FutureHandler<T>(callable);
        futures.add(fh);
        return fh;
    }

    @Override
    public final void innerDraw(Graphics2D g2) {
        var drawn = false;

        for (int i = children.size() - 1; i >= 0; i--) {
            var actual = children.get(i);

            if (!drawn && getZ() >= actual.getZ()) {
                draw(g2);
                drawn = true;
            }

            actual.innerDraw(g2);
        }

        if (!drawn) draw(g2);
    }

    @Override
    public void draw(Graphics2D g2) {}
}
