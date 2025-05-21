package org.mocha.animation;

import java.awt.image.BufferedImage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Frame {

    private BufferedImage frame;
    private double duration;

    public Frame(BufferedImage frame, double duration) {
        this.frame = frame;
        this.duration = duration;
    }
}