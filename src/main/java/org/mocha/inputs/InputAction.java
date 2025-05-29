package org.mocha.inputs;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InputAction {
    private String name;
    private int status;
    private List<Integer> inputs;

    public InputAction(String name, int action) {
        this.name = name;
        status = 0;
        inputs = new ArrayList<>();
        inputs.add(action);
    }

    public void addInput(int action) {
        inputs.add(action);
    }
}
