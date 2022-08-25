package com.otus.data;

public enum BrowserData {
    CHROME("chrome"),
    FIREFOX("firefox"),
    OPERA("opera");

    private String name;

    BrowserData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
