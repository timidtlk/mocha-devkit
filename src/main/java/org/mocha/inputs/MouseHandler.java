package org.mocha.inputs;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MouseHandler extends MouseAdapter {

    private InputManager manager;

    @Override
    public void mousePressed(MouseEvent e) {
        int src = e.getButton();

        for (InputAction action : manager.getActions()) {
            for(int input : action.getInputs()) {
                if (src == input) {
                    action.setStatus(true);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int src = e.getButton();

        for (InputAction action : manager.getActions()) {
            for(int input : action.getInputs()) {
                if (src == input) {
                    action.setStatus(false);
                }
            }
        }
    }

}