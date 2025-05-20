package org.mocha.actor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.mocha.annotations.ShowHitbox;
import org.mocha.util.GraphicsUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Box extends Actor {
    protected int width;
    protected int height;
    protected Rectangle hitbox;
    protected Color debugColor;

    public Box(double x, double y, int width, int height) {
        super(x, y);
        this.width = width;
        this.height = height;
        debugColor = Color.RED;
    }

    @Override
    public final void innerDraw(Graphics2D g2) {
        if (getClass().isAnnotationPresent(ShowHitbox.class)) {
            g2.setColor(debugColor);
            GraphicsUtil.drawRotatedRect(getX(), getY(), width, height, rotation, g2);
        }
        draw(g2);
    }

    public boolean checkCollision(Box box) {
        return hitbox.intersects(box.getHitbox());
    }
}
