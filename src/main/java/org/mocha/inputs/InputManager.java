package org.mocha.inputs;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InputManager {
    private ArrayList<InputAction> actions;

    public InputManager() {
        actions = new ArrayList<>();
    }
}
