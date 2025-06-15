package org.mocha.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.JTextPane;

import lombok.Getter;

public class Label extends GUIActor {
    @Getter
    private JTextPane pane;
    private float alignment;

    public Label(String text) {
        pane = new JTextPane();
        setText(text);
        setForeground(Color.WHITE);
    }

    @Override
    public void drawGUI(Graphics2D g2) {
        var trans = g2.getTransform();
        g2.translate(position.getFloorX(), position.getFloorY());

        pane.paint(g2);

        g2.setTransform(trans);
    }

    public String getText() {
        return pane.getText();
    }

    public void setText(String text) {
        pane.setText(text);
    }

    public void setSize(int width, int height) {
        pane.setSize(width, height);
    }

    public Font getFont() {
        return pane.getFont();
    }

    public void setFont(Font font) {
        pane.setFont(font);
    }

    public Color getForeground() {
        return pane.getForeground();
    }

    public void setForeground(Color color) {
        pane.setForeground(color);
    }

    public Color getBackground() {
        return pane.getBackground();
    }

    public void setBackground(Color color) {
        pane.setBackground(color);
    }

    public void setOpaque(boolean opaque) {
        pane.setOpaque(opaque);
    }
}
