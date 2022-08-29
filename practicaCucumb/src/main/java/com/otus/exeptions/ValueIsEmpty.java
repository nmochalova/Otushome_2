package com.otus.exeptions;

public class ValueIsEmpty extends Exception{
    public ValueIsEmpty(String message) {
        super("The value is not set in the test.feature => " + message);
    }
}
