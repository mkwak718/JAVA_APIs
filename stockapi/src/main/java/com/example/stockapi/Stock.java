package com.example.stockapi;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Stock {
    @Id
    private String symbol;
    private String companyName;
    private double price;
    private double volume;
    private double marketCap;

    public Stock() {}

    public Stock(String symbol, String companyName, double price, double volume, double marketCap) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.price = price;
        this.volume = volume;
        this.marketCap = marketCap;
    }

    // Getters and Setters
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public double getVolume() { return volume; }
    public void setVolume(double volume) { this.volume = volume; }
    public double getMarketCap() { return marketCap; }
    public void setMarketCap(double marketCap) { this.marketCap = marketCap; }
}


