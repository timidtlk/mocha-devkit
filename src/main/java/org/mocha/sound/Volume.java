package org.mocha.sound;

import lombok.Getter;
import lombok.Setter;

/**
 * Linear value: {@code 0 - 1f}.<p>
 * Decibel value: {@code -inf - 0dB}.
 */
@Getter
@Setter
public class Volume {
    private float linearValue;
    private float dbValue;

    private Volume() { }

    public static Volume fromLinear(float linearValue) {
        var v = new Volume();
        v.setLinearValue(linearValue);
        return v;
    }

    public static Volume fromDB(float dbValue) {
        var v = new Volume();
        v.setDbValue(dbValue);
        return v;
    }

    public void setVolumeLinear(float linearValue) {
        this.linearValue = linearValue;
        dbValue = 20f * (float) Math.log10(linearValue);
    }

    public void setVolumeDB(float dbValue) {
        this.linearValue = (float) Math.pow(10f, dbValue / 20f);
        this.dbValue = dbValue;
    }
}
