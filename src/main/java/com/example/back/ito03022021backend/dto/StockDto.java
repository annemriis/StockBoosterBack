package com.example.back.ito03022021backend.dto;

import java.util.List;

public class StockDto {

    private String symbol;
    private List<List<String>> stockInfo;

    public StockDto(String symbol, List<List<String>> stockInfo) {
        this.symbol = symbol;
        this.stockInfo = stockInfo;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setStockInfo(List<List<String>> stockInfo) {
        this.stockInfo = stockInfo;
    }

    public String getSymbol() {
        return symbol;
    }

    public List<List<String>> getStockInfo() {
        return stockInfo;
    }

}
