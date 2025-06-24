package org.mocha.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.smartcardio.Card;
import javax.swing.BorderFactory;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.Utilities;

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

    public void autoSize() {
        //align vertical
        var doc = pane.getStyledDocument();
        var center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        int lineCount = (getText().length() == 0) ? 1 : 0;
        try {
            int offset = getText().length(); 
            while (offset > 0) {
                offset = Utilities.getRowStart(pane, offset) - 1;
                lineCount++;
            }
        } catch (Exception e) { e.printStackTrace(); }

        var metrics = pane.getFontMetrics(pane.getFont());

        var textWidth = 0;
        for (int i = 0; i < getText().length(); i++) {
            textWidth += metrics.charWidth(getText().charAt(i));
        }

        pane.setBounds(0, 0, textWidth, lineCount * metrics.getHeight());
        pane.setAlignmentX(JTextPane.CENTER_ALIGNMENT);
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
