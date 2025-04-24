package org.mocha.inputs;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InputAction {
    private boolean status;
    private List<Integer> inputs;
}
