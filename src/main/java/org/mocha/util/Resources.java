package org.mocha.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.mocha.sound.Sound;

public final class Resources {

    public static URL getLocation(String name) {
        URL fromClass = ClassLoader.getSystemResource(name);
        if (fromClass != null) {
            return fromClass;
        }

        try {
            return URI.create(name).toURL();
        } catch (MalformedURLException e) {
            try {
                return (new File(name)).toURI().toURL();
            } catch (MalformedURLException e1) {
                return null;
            }
        }
    }

    public static BufferedImage getImage(String name) throws IOException {
        return ImageIO.read(getLocation(name));
    }

    public static Sound getSound(String name) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        return new Sound(AudioSystem.getAudioInputStream(getLocation(name)), name);
    }
}
