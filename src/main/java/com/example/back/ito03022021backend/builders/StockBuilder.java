package com.example.back.ito03022021backend.builders;

import com.example.back.ito03022021backend.model.Stock;

public class StockBuilder {

    private String symbol;
    private Double previousClose;
    private Double close;

    public StockBuilder withSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public StockBuilder withPreviousClose(Double previousClose) {
        this.previousClose = previousClose;
        return this;
    }

    public StockBuilder withClose(Double close) {
        this.close = close;
        return this;
    }

    public Stock buildStock() {
        Stock stock = new Stock();
        stock.setSymbol(this.symbol);
        stock.setPreviousClose(this.previousClose);
        stock.setClose(this.close);
        return stock;
    }
}
