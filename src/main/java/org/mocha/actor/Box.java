package org.mocha.actor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.mocha.annotations.ShowHitbox;
import org.mocha.util.GraphicsUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Box extends Actor {
    protected int width = 0;
    protected int height = 0;
    protected Rectangle hitbox = new Rectangle();
    protected Color debugColor = Color.RED;

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
            GraphicsUtil.drawRotatedRect(getX(), getY(), (int) Math.round(width * scale.getX()), (int) Math.round(height * scale.getY()), rotation, anchor, g2);
        }
        draw(g2);
    }

    public boolean checkCollision(Box box) {
        return getHitbox().intersects(box.getHitbox());
    }

    public Rectangle getHitbox() {
        hitbox.setBounds(position.getFloorX(), position.getFloorY(), getWidth(), getHeight());
        return hitbox;
    }
}
