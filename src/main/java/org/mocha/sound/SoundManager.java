package org.mocha.sound;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import org.mocha.util.Resources;

public class SoundManager {
    private static final ArrayList<WeakReference<SoundManager>> INSTANCES = new ArrayList<>();
    private static final Volume GENERAL_VOLUME = Volume.fromLinear(1);

    private final Volume VOLUME = Volume.fromLinear(1);
    private HashMap<String, Sound> sounds = new HashMap<>();

    public SoundManager() {
        INSTANCES.add(new WeakReference<>(this));
    }

    public Sound load(String name) {
        var sound = sounds.get(name);
        if (sound == null) {
            try {
                sound = Resources.getSound(name);
                sound.setVolumeLinear(GENERAL_VOLUME.getLinearValue() * VOLUME.getLinearValue());
                sounds.put(name, sound);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return sound;
    }

    public void play(String name) {
        var sound = load(name);
        sound.play();
    }

    public void playIfNotPlaying(String name) {
        var sound = load(name);
        if (!sound.isPlaying()) sound.play();
    }

    public void stop(String name) {
        var sound = sounds.get(name);
        if (sound == null) return;
        sound.stop();
    }

    public void pause(String name) {
        var sound = sounds.get(name);
        if (sound == null) return;
        sound.pause();
    }

    public void resume(String name) {
        var sound = sounds.get(name);
        if (sound == null) return;
        sound.play();
    }

    public static void updateVolume() {
        for (int i = INSTANCES.size()-1; i >= 0; i--) {
            var sm = INSTANCES.get(i).get();
            if (sm == null) {
                INSTANCES.remove(i);
                return;
            }
            sm.innerUpdateVolume();
        }
    }

    private void innerUpdateVolume() {
        var val = GENERAL_VOLUME.getLinearValue() * VOLUME.getLinearValue();
        for (var sound : sounds.values()) {
            sound.setVolumeLinear(val);
        }
    }

    public static float getGeneralVolumeLinear() {    
        return GENERAL_VOLUME.getLinearValue();
    }

    public static void setGeneralVolumeLinear(float linearValue) {
        GENERAL_VOLUME.setLinearValue(linearValue);
        updateVolume();
    }

    public static float getGeneralVolumeDB() {    
        return GENERAL_VOLUME.getDbValue();
    }

    public static void setGeneralVolumeDB(float dbValue) {
        GENERAL_VOLUME.setDbValue(dbValue);
        updateVolume();
    }

    public float getVolumeLinear() {    
        return VOLUME.getLinearValue();
    }

    public void setVolumeLinear(float linearValue) {
        VOLUME.setLinearValue(linearValue);
        innerUpdateVolume();
    }

    public float getVolumeDB() {    
        return VOLUME.getDbValue();
    }

    public void setVolumeDB(float dbValue) {
        VOLUME.setDbValue(dbValue);
        innerUpdateVolume();
    }
}
