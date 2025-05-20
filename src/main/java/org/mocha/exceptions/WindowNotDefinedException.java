package org.mocha.exceptions;

public class WindowNotDefinedException extends Exception {
    public WindowNotDefinedException() {
        super("Window annotation has not been added");
    }
}
