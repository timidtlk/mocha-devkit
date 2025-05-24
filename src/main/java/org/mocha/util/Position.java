package org.mocha.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Position {
    private double x = 0;
    private double y = 0;
    private Position relative = null;

    public static Position of(double x, double y) {
        return new Position(x, y);
    }

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void translate(Vector2 trans) {
        this.x += trans.getX();
        this.y += trans.getY();
    }

    public void translate(double x, double y) {
        this.x += x;
        this.y += y;
    }

    public Position sum(Position pos) {
        this.x += pos.x;
        this.y += pos.y;

        return this;
    }

    public Position sum(double x, double y) {
        this.x += x;
        this.y += y;

        return this;
    }

    public Position subtract(Position pos) {
        this.x -= pos.x;
        this.y -= pos.y;

        return this;
    }

    public Position subtract(double x, double y) {
        this.x -= x;
        this.y -= y;

        return this;
    }

    public Position multiply(Position pos) {
        this.x *= pos.x;
        this.y *= pos.y;

        return this;
    }

    public Position multiply(double x, double y) {
        this.x *= x;
        this.y *= y;

        return this;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
