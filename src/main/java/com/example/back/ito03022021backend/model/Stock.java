package com.example.back.ito03022021backend.model;

import javax.persistence.*;

@Entity
public class Stock {

    @Id
    private String symbol;

    @Column(name = "lastClose")
    private Double lastClose;

    @Column(name = "close")
    private Double close;

    public Stock() {}

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setLastClose(Double lastClose) {
        this.lastClose = lastClose;
    }

    public Double getLastClose() {
        return lastClose;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getClose() {
        return close;
    }
}
