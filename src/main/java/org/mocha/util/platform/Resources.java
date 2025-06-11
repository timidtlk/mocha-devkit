package org.mocha.util.platform;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.json.JSONObject;
import org.mocha.sound.Sound;

public final class Resources {
    private static HashMap<String, Object> resources = new HashMap<>();

    private static <T> T load(String name, Builder builder, Class<T> returnType) {
        try {
            var res = resources.get(name);
            if (res == null) {
                res = builder.build(name);
                resources.put(name, res);
            }
            return returnType.cast(res);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
            return null;
        }
    }

    public static URL getLocation(String path) {
        URL fromClass = ClassLoader.getSystemResource(path);
        if (fromClass != null) {
            return fromClass;
        }

        try {
            return URI.create(path).toURL();
        } catch (MalformedURLException e) {
            try {
                return (new File(path)).toURI().toURL();
            } catch (MalformedURLException e1) {
                return null;
            }
        }
    }

    public static String getTextFile(String path) throws IOException {
        return load(path, Resources::buildTextFile, String.class);
    }

    private static String buildTextFile(String path) throws IOException {
        var scan = new Scanner(getLocation(path).openStream());
        var txt = scan.useDelimiter("\\A").next();
        scan.close();
        return txt;
    }

    public static JSONObject getJSONFromFile(String path) throws IOException {
        return new JSONObject(getTextFile(path));
    }

    public static BufferedImage getImage(String path) throws IOException {
        return load(path, Resources::buildImage, BufferedImage.class);
    }

    private static BufferedImage buildImage(String name) throws IOException {
        return ImageIO.read(getLocation(name));
    }

    public static Sound getSound(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        return load(path, Resources::buildSound, Sound.class);
    }

    private static Sound buildSound(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        var clazz = PlatformMan.getImpl(Sound.class);
        Sound instance = null;
        try {
            instance = clazz.getConstructor(String.class).newInstance(path);
            instance.open(AudioSystem.getAudioInputStream(getLocation(path)));

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException | SecurityException | InvocationTargetException  | IllegalArgumentException | IllegalAccessException e) {
            //this shouldn't happen
            e.printStackTrace();
        }
        return instance;
    }

    @FunctionalInterface
    private static interface Builder {

        public Object build(String path) throws Exception;
    }
}
