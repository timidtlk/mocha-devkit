package org.mocha.inputs;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KeyHandler extends KeyAdapter {

    private InputManager manager;

    @Override
    public void keyPressed(KeyEvent e) {
        int src = e.getKeyCode();

        for (InputAction action : manager.getActions()) {
            for(int input : action.getInputs()) {
                if (src == input) {
                    action.setStatus(true);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int src = e.getKeyCode();

        for (InputAction action : manager.getActions()) {
            for(int input : action.getInputs()) {
                if (src == input) {
                    action.setStatus(false);
                }
            }
        }
    }
}
