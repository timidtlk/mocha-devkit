package org.mocha.enums;

import org.mocha.util.math.Vector2;

import lombok.Getter;

public enum AnchorPoint {
    TOP_LEFT(0, 0),
    TOP_CENTER(.5, 0),
    TOP_RIGHT(1, 0),
    MIDDLE_LEFT(0, .5),
    MIDDLE_CENTER(.5, .5),
    MIDDLE_RIGHT(1, .5),
    BOTTOM_LEFT(0, 1),
    BOTTOM_CENTER(.5, 1),
    BOTTOM_RIGHT(1, 1);

    @Getter
    private final Vector2 pos;

    private AnchorPoint(double x, double y) {
        pos = new Vector2(x, y);
    }

    public double getX() {
        return pos.getX();
    }

    public double getY() {
        return pos.getY();
    }
}
