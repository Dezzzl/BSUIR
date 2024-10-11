package org.example.Exceptions;

public class NotFormulaException extends Exception {
    public NotFormulaException() {
        super("Input is not a valid formula.");
    }
}

