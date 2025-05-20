package org.mocha.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vector2 {
    private double x;
    private double y;

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }

    public void multiply(double n) {
        x *= n;
        y *= n;
    }

    public double getLengthFast() {
        double d = x * x + y * y;
        double sqrt = Double.longBitsToDouble( ( ( Double.doubleToLongBits( d )-(1l<<52) )>>1 ) + ( 1l<<61 ) );
        double better = (sqrt + d/sqrt)/2.0;
        double evenbetter = (better + d/better)/2.0;
        return evenbetter;
    }

    public Vector2 normalized() {
        double length = getLength();
        return new Vector2(x/length, y/length);
    }

    public Vector2 normalize() {
        double length = getLength();
        this.set(x/length, y/length);

        return this;
    }

    public Vector2 fastNormalized() {
        double length = getLengthFast();
        return new Vector2(x/length, y/length);
    }

    public Vector2 fastNormalize() {
        double length = getLengthFast();
        this.set(x/length, y/length);

        return this;
    }

    public double dotProduct(Vector2 v) {
        return this.x * v.x + this.y * v.y;
    }
}
