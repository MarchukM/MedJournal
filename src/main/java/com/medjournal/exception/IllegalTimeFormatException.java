package com.medjournal.exception;

public class IllegalTimeFormatException extends Exception {
    public IllegalTimeFormatException() {
        super("Wrong time format");
    }
}
