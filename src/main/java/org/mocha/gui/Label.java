package org.mocha.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.Utilities;

import lombok.Getter;
import lombok.Setter;

public class Label extends GUIActor {
    @Getter
    private JTextPane textPane;
    @Getter @Setter
    private float alignmentX;
    @Getter @Setter
    private float alignmentY;

    public Label(String text) {
        textPane = new JTextPane();
        setText(text);
        setForeground(Color.WHITE);
        setBorder(null);
    }

    @Override
    public void drawGUI(Graphics2D g2) {
        var trans = g2.getTransform();
        g2.translate(position.getFloorX(), position.getFloorY());

        textPane.paint(g2);

        g2.setTransform(trans);
    }

    public void autoSize() {
        //align horizontal
        var doc = textPane.getStyledDocument();
        var center = new SimpleAttributeSet();

        if (alignmentX == .5) StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        else if (alignmentX == 1) StyleConstants.setAlignment(center, StyleConstants.ALIGN_RIGHT);

        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        int lineCount = (getText().length() == 0) ? 1 : 0;
        try {
            int offset = getText().length(); 
            while (offset > 0) {
                offset = Utilities.getRowStart(textPane, offset) - 1;
                lineCount++;
            }
        } catch (Exception e) { e.printStackTrace(); }

        var metrics = textPane.getFontMetrics(textPane.getFont());

        var textWidth = 0;
        for (int i = 0; i < getText().length(); i++) {
            textWidth += metrics.charWidth(getText().charAt(i));
        }

        textPane.setBounds(0, 0, textWidth, lineCount * metrics.getHeight());
    }

    public String getText() {
        return textPane.getText();
    }

    public void setText(String text) {
        textPane.setText(text);
    }

    public void setSize(int width, int height) {
        autoSize();
        if (alignmentY == 0) {
            textPane.setSize(width, height);
        } else {
            var div = (alignmentY == 0.5 ? 2 : 1) ;
            var space = height / div - textPane.getHeight() / div;
            textPane.setSize(width, height);

            var doc = textPane.getStyledDocument();
            var center = new SimpleAttributeSet();
            StyleConstants.setSpaceAbove(center, space);
            doc.setParagraphAttributes(0, doc.getLength(), center, false);
        }
    }

    public Border getBorder() {
        return textPane.getBorder();
    }

    public void setBorder(Border border) {
        textPane.setBorder(border);
    }

    public Font getFont() {
        return textPane.getFont();
    }

    public void setFont(Font font) {
        textPane.setFont(font);
    }

    public Color getForeground() {
        return textPane.getForeground();
    }

    public void setForeground(Color color) {
        textPane.setForeground(color);
    }

    public Color getBackground() {
        return textPane.getBackground();
    }

    public void setBackground(Color color) {
        textPane.setBackground(color);
    }

    public void setOpaque(boolean opaque) {
        textPane.setOpaque(opaque);
    }

    public void setAlignment(float x, float y) {
        alignmentX = x;
        alignmentY = y;
    }
}
