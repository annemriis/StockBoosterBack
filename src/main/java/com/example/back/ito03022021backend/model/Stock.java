package com.example.back.ito03022021backend.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Stock {

    @Id
    private String symbol;  // Siin meetodis on midagi valesti?

    @Column(name = "open")
    private Double open;

    @Column(name = "close")
    private Double close;

    @Column(name = "high")
    private Double high;

    @Column(name = "volume")
    private Long volume;

    @Column(name = "lastDate")
    private String lastDate;

    @ElementCollection
    @CollectionTable(name = "_stock_date_info", joinColumns = @JoinColumn(name = "symbol"))
    @Column(name = "stock_date_info")
    private List<String> stockDateInfo;

    @ElementCollection
    @CollectionTable(name = "_stock_close_info", joinColumns = @JoinColumn(name = "symbol"))
    @Column(name = "stock_close_info")
    private List<Double> stockCloseInfo;

    public Stock() {}

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getOpen() {
        return open;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getClose() {
        return close;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getHigh() {
        return high;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public Long getVolume() {
        return volume;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getLastDate() {
        return lastDate;
    }
}
