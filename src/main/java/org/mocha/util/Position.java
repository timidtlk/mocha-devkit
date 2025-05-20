package org.mocha.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Position {
    private double x;
    private double y;
    private Position relative;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
        relative = null;
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
