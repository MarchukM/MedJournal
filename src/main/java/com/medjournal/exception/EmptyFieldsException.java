package com.medjournal.exception;

public class EmptyFieldsException extends Exception{
    public EmptyFieldsException() {
        super("Some fields are not completely filled");
    }
}
