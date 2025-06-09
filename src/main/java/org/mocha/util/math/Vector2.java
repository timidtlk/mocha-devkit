package org.mocha.util.math;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vector2 implements Cloneable {
    private double x = 0;
    private double y = 0;

    public Vector2 clone() {
        try {
            return (Vector2) super.clone();
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen
            e.printStackTrace();
            return null;
        }
    }

    public Vector2 sum(Vector2 vec) {
        this.x += vec.x;
        this.y += vec.y;

        return this;
    }

    public Vector2 sum(double x, double y) {
        this.x += x;
        this.y += y;

        return this;
    }

    public Vector2 translate(Vector2 vec) {
        return sum(vec);
    }

    public Vector2 translate(double x, double y) {
        return sum(x, y);
    }

    public Vector2 subtract(Vector2 vec) {
        this.x -= vec.x;
        this.y -= vec.y;

        return this;
    }

    public Vector2 subtract(double x, double y) {
        this.x -= x;
        this.y -= y;

        return this;
    }

    public Vector2 multiply(double n) {
        x *= n;
        y *= n;

        return this;
    }

    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Fast version of {@link #getLength()} using Quake's fast square root.
     */
    public double getLengthFast() {
        double d = x * x + y * y;
        double sqrt = Double.longBitsToDouble( ( ( Double.doubleToLongBits( d )-(1l<<52) )>>1 ) + ( 1l<<61 ) );
        double better = (sqrt + d/sqrt)/2.0;
        double evenbetter = (better + d/better)/2.0;
        return evenbetter;
    }

    /**
     * @return A normalized new instance.
     */
    public Vector2 normalized() {
        double length = getLength();
        return new Vector2(x/length, y/length);
    }

    /**
     * Normalizes this vector.
     * @return This instance.
     */
    public Vector2 normalize() {
        double length = getLength();
        this.set(x/length, y/length);
        return this;
    }

    /**
     * Fast version of {@link #normalize()} using Quake's fast square root.
     * @return A normalized new instance.
     */
    public Vector2 fastNormalized() {
        double length = getLengthFast();
        return new Vector2(x/length, y/length);
    }

    /**
     * Fast version of {@link #normalize()} using Quake's fast square root.
     * @return This instance.
     */
    public Vector2 fastNormalize() {
        double length = getLengthFast();
        this.set(x/length, y/length);
        return this;
    }

    public double dotProduct(Vector2 v) {
        return this.x * v.x + this.y * v.y;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector2 vec) {
        this.x = vec.getX();
        this.y = vec.getY();
    }

    public int getRoundX() {
        return (int) Math.round(getX());
    }

    public int getRoundY() {
        return (int) Math.round(getY());
    }

    public int getFloorX() {
        return (int) Math.floor(getX());
    }

    public int getFloorY() {
        return (int) Math.floor(getY());
    }

    public int getCeilX() {
        return (int) Math.ceil(getX());
    }

    public int getCeilY() {
        return (int) Math.ceil(getY());
    }
}
