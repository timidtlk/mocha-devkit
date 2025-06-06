package org.mocha.actor;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.mocha.enums.AnchorPoint;
import org.mocha.interfaces.IInnerLogic;
import org.mocha.util.math.Mathf;
import org.mocha.util.math.Vector2;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Actor implements IInnerLogic {
    protected Vector2 position;
    protected Vector2 localPosition;
    protected Vector2 velocity;

    protected double rotation;
    protected Vector2 scale;
    protected AnchorPoint anchor;

    protected List<Actor> children;
    protected Actor parent;

    public Actor() {
        this(0, 0);
    }
    
    public Actor(double x, double y) {
        position = new Vector2(x, y);
        localPosition = new Vector2();
        velocity = new Vector2();
        rotation = 0;
        scale = new Vector2(1, 1);
        anchor = AnchorPoint.TOP_LEFT;
        children = new ArrayList<>();
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

    public void rotateTo(Vector2 position, double factor) {
        position.subtract(this.position);

        var n = position.fastNormalized();

        rotation = Mathf.lerpAngle(rotation, Math.atan2(n.getY(), n.getX()), factor);
    }

    public void rotate(double radians) {
        rotation += radians;
    }

    public void addChildren(Actor agent) {
        children.add(agent);
        agent.setParent(this);
        agent.setAnchor(anchor);
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

    @Override
    public void start() {}

    @Override
    public final void innerUpdate(double deltaTime) {
        position.sum(velocity.getX() * deltaTime, velocity.getY() * deltaTime);
        velocity.setX(0);
        velocity.setY(0);
        
        if (hasParent()) {
            this.position = parent.position.sum(localPosition);
            this.rotation = parent.rotation;
        }
        update(deltaTime);
    }

    @Override
    public void update(double deltaTime) {}

    @Override
    public void draw(Graphics2D g2) {}
}
