package org.mocha.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;

import org.mocha.ThreadMan;

import lombok.Data;

@Data
public class Sound implements LineListener {
    private AudioInputStream audioStream;
    private AudioFormat format;
    private DataLine.Info info;
    private Clip audioClip;
    private String name;
    private long timming = 0;
    private boolean isPlaying = false;

    public Sound(AudioInputStream audioStream, String name) throws LineUnavailableException {
        this.audioStream = audioStream;
        this.format = audioStream.getFormat();
        this.info = new DataLine.Info(Clip.class, format);
        this.audioClip = (Clip) AudioSystem.getLine(info);
        this.audioClip.addLineListener(this);
        this.name = name;
    }

    public void play() {
        isPlaying = true;
        ThreadMan.execute(() -> {
            synchronized(audioClip) {
                if (!audioClip.isOpen()) {
                    try {
                        audioClip.open(audioStream); // pesado
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                audioClip.setMicrosecondPosition(timming);
                audioClip.start();
            }
        });
    }

    @Override
    public void update(LineEvent e) {
        if (e.getType() == LineEvent.Type.STOP) {
            audioClip.close();
            isPlaying = false;
        } else if (e.getType() == LineEvent.Type.CLOSE) {
            isPlaying = false;
        }
    }

    public boolean isPlaying() {
        return isPlaying || audioClip.isActive();
    }

    public void pause() {
        audioClip.stop();
        timming = audioClip.getMicrosecondPosition();
    }

    public void stop() {
        timming = 0;
        audioClip.close();
    }
}
