package org.mocha;

public class InputNotDefinedException extends Exception {
    public InputNotDefinedException() {
        super("This input has not been defined");
    }
}
