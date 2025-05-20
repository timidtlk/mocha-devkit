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

        var actions = manager.getActions();
        actions.forEach((a) -> {
            for (Integer input : a.getInputs()) {
                if (src == input) a.setStatus(1);
            }
        });
        manager.setActions(actions);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int src = e.getButton();

        var actions = manager.getActions();
        actions.forEach((a) -> {
            for (Integer input : a.getInputs()) {
                if (src == input) a.setStatus(0);
            }
        });
        manager.setActions(actions);
    }

}