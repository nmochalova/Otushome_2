package com.otus.exeptions;

public class NoDataFound extends Exception{
    public NoDataFound(String message) {
        super("No data found => " + message);
    }
}
