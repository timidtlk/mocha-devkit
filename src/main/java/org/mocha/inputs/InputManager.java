package org.mocha.inputs;

import java.util.ArrayList;
import java.util.List;

import org.mocha.exceptions.InputNotDefinedException;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InputManager {
    private List<InputAction> actions;

    public InputManager() {
        actions = new ArrayList<>();
    }

    public void addAction(InputAction action) {
        actions.add(action);
    }

    public void addAction(String name, int action) {
        actions.add(new InputAction(name, action));
    }

    public int getInputStatus(String name) {
        try {
            for (InputAction action : actions) {
                if (action.getName().equals(name)) {
                    return action.getStatus();
                }
            }
            throw new InputNotDefinedException();
        } catch (InputNotDefinedException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
