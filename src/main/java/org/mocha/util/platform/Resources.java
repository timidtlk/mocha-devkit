package org.mocha.util.platform;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.json.JSONObject;
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

    public static String getString(String name) throws IOException {
        var scan = new Scanner(getLocation(name).openStream());
        var txt = scan.useDelimiter("\\A").next();
        scan.close();
        return txt;
    }

    public static JSONObject getJSONObject(String name) throws IOException {
        return new JSONObject(getString(name));
    }

    public static BufferedImage getImage(String name) throws IOException {
        return ImageIO.read(getLocation(name));
    }

    public static Sound getSound(String name) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        var clazz = PlatformMan.getImpl(Sound.class);
        Sound instance = null;
        try {
            instance = clazz.getConstructor(String.class).newInstance(name);
            instance.open(AudioSystem.getAudioInputStream(getLocation(name)));

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException | SecurityException | InvocationTargetException  | IllegalArgumentException | IllegalAccessException e) {
            //this shouldn't happen
            e.printStackTrace();
        }
        return instance;
    }
}
