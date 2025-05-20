package org.mocha.animation;

import java.awt.image.BufferedImage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Frame {

    private BufferedImage frame;
    private int duration;

    public Frame(BufferedImage frame, int duration) {
        this.frame = frame;
        this.duration = duration;
    }
}