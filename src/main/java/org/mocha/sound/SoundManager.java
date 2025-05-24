package org.mocha.sound;

import java.util.HashMap;

import org.mocha.util.Resources;

public class SoundManager {
    private HashMap<String, Sound> sounds = new HashMap<>();

    public void play(String name) {
        var sound = sounds.get(name);
        if (sound == null) {
            try {
                sound = Resources.getSound(name);
                sounds.put(name, sound);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        sound.play();
    }

    public void playIfNotPlaying(String name) {
        var sound = sounds.get(name);
        if (sound == null) {
            try {
                sound = Resources.getSound(name);
                sounds.put(name, sound);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
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
}
