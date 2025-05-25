package org.mocha.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;

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
    private FloatControl gainControl;

    public Sound(AudioInputStream audioStream, String name) throws LineUnavailableException {
        this.name = name;
        this.audioStream = audioStream;
        this.audioClip = AudioSystem.getClip();
        this.audioClip.addLineListener(this);

        try {
            audioClip.open(audioStream); // pesado
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.gainControl = (FloatControl) this.audioClip.getControl(FloatControl.Type.MASTER_GAIN);
    }

    public void play() {
        isPlaying = true;
        audioClip.setMicrosecondPosition(timming);
        audioClip.start();
    }

    @Override
    public void update(LineEvent e) {
        if (e.getType() == LineEvent.Type.STOP) {
            isPlaying = false;
            timming = 0;
        }
    }

    public boolean isPlaying() {
        return isPlaying || audioClip.isActive();
    }

    public void pause() {
        var temp = audioClip.getMicrosecondPosition();
        audioClip.stop();
        timming = temp;
    }

    public void stop() {
        audioClip.stop();
    }

    public float getVolumeLinear() {    
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }

    public void setVolumeLinear(float linearValue) {
        gainControl.setValue(20f * (float) Math.log10(linearValue));
    }

    public float getVolumeDB() {    
        return gainControl.getValue();
    }

    public void setVolumeDB(float dbValue) {
        gainControl.setValue(dbValue);
    }
}
