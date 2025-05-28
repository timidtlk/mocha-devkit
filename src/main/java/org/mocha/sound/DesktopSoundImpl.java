package org.mocha.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DesktopSoundImpl extends Sound {
    private AudioInputStream audioStream;
    private AudioFormat format;
    private DataLine.Info info;
    private Clip audioClip;
    private String name;
    private long timming = 0;
    private boolean isPlaying = false;
    private FloatControl gainControl;

    public DesktopSoundImpl(String name) throws LineUnavailableException  {
        super(name);
        this.audioClip = AudioSystem.getClip();
        this.audioClip.addLineListener(this);
    }

    @Override
    public void open(AudioInputStream audioStream) {
        this.audioStream = audioStream;

        try {
            audioClip.open(audioStream); // pesado
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.gainControl = (FloatControl) this.audioClip.getControl(FloatControl.Type.MASTER_GAIN);
    }

    @Override
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

    @Override
    public boolean isPlaying() {
        return isPlaying || audioClip.isActive();
    }

    @Override
    public void pause() {
        var temp = audioClip.getMicrosecondPosition();
        audioClip.stop();
        timming = temp;
    }

    @Override
    public void stop() {
        audioClip.stop();
    }

    @Override
    public float getVolumeLinear() {    
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }

    @Override
    public void setVolumeLinear(float linearValue) {
        gainControl.setValue(20f * (float) Math.log10(linearValue));
    }

    @Override
    public float getVolumeDB() {    
        return gainControl.getValue();
    }

    @Override
    public void setVolumeDB(float dbValue) {
        gainControl.setValue(dbValue);
    }
}
