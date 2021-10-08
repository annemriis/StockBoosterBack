package com.example.back.ito03022021backend.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Stock {

    @Id
    private String symbol;  // Siin meetodis on midagi valesti?

    @Column(name = "previous-close")  // Või peaks name = "previous_close" või "previousClose.
    private Double previousClose;

    @Column(name = "close")
    private Double close;

    public Stock() {}

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setPreviousClose(Double previousClose) {
        this.previousClose = previousClose;
    }

    public Double getPreviousClose() {
        return previousClose;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getClose() {
        return close;
    }
}
