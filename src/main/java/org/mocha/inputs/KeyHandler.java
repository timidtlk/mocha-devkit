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

        var actions = manager.getActions();
        actions.forEach((a) -> {
            for (Integer input : a.getInputs()) {
                if (src == input) a.setStatus(1);
            }
        });
        manager.setActions(actions);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int src = e.getKeyCode();

        var actions = manager.getActions();
        actions.forEach((a) -> {
            for (Integer input : a.getInputs()) {
                if (src == input) a.setStatus(0);
            }
        });
        manager.setActions(actions);
    }
}
