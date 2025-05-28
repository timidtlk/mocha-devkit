package org.mocha.util.platform;

import java.util.ArrayList;

import lombok.Getter;

@SuppressWarnings("unchecked")
public class PlatformMan {
    @Getter
    private static OS currentOS = getPlatform();

    public static <T> Class<? extends T> getImpl(Class<T> clazz) throws NoClassDefFoundError {
        try {
            var impls = (ArrayList<Class<? extends T>>) clazz.getField("IMPLEMENTATIONS").get(null);

            switch (currentOS) {
                case ANDROID:
                    for (int i = 0; i < impls.size(); i++) {
                        var impl = impls.get(i);
                        if (impl.getName().toLowerCase().contains("android")) return impl;
                    }
                    break;
                default:
                    for (int i = 0; i < impls.size(); i++) {
                        var impl = impls.get(i);
                        if (impl.getName().toLowerCase().contains("desktop")) return impl;
                    }
                    break;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            //this should not happen
            e.printStackTrace();
        }
        throw new NoClassDefFoundError("It was not possible to find some implementation for: "+clazz.getName());
    }

    public static OS getPlatform() {
        var osString = System.getProperty("os.name").toLowerCase();

        if (osString.contains("android")) {
            return OS.ANDROID;
        }

        return OS.DESKTOP;
    }

    public enum OS {
        DESKTOP,
        ANDROID;
    }
}
