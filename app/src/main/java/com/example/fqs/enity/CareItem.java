package com.example.fqs.enity;

public class CareItem {
    private int id;
    private String name;
    private double price;
    private String desc;

    public String getIconUrl() {
        return iconUrl;
    }

    private String iconUrl;

    public CareItem(int id, String name, double price, String desc) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDesc() {
        return desc;
    }
}
