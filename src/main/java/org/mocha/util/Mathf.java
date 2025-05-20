package org.mocha.util;

public class Mathf {

    public static double angleDifference(double a, double b) {
        double difference = (b - a) % (Math.PI * 2);
	    return (2.0 * difference) % (Math.PI * 2) - difference;
    }

    public static double lerp(double a, double b, double factor) {
        return a + (b - a) * factor;
    }

    public static double lerpAngle(double a, double b, double factor) {
        return a + angleDifference(a, b) * factor;
    }

    public static double clamp(double value, double min, double max) {
        value = Math.min(value, max);
        value = Math.max(value, min);
        return value;
    }
}
