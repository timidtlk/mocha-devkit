package org.mocha.sound;

import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineListener;

import lombok.Getter;

/**
 * Implementations of this class must <strong>maintain 
 * at least one constructor with the same signature</strong>.
 */
public abstract class Sound implements LineListener {
    public static final ArrayList<Class<? extends Sound>> IMPLEMENTATIONS = new ArrayList<>();
    @Getter
    private String name;

    static {
        IMPLEMENTATIONS.add(DesktopSoundImpl.class);
    }

    public Sound(String name) {
        this.name = name;
    }

    public abstract void open(AudioInputStream audioStream);

    public abstract void play();

    public abstract boolean isPlaying();

    public abstract void pause();

    public abstract void stop();

    public abstract float getVolumeLinear();

    public abstract void setVolumeLinear(float linearValue);

    public abstract float getVolumeDB();

    public abstract void setVolumeDB(float dbValue);
}
