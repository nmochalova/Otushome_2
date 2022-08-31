package com.otus.datatable;

public class DataTableCoursePrice {
    private String name;
    private String priceString;
    private int price;

    public DataTableCoursePrice(String name, String priceString, int price) {
        this.name = name;
        this.priceString = priceString;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getPriceString() {
        return priceString;
    }
}
