package com.example.back.ito03022021backend.dto;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockDtoBuilder {

    private String symbol;
    private Double open;
    private Double close;
    private Double high;
    private Long volume;
    private String lastDate;
    private List<String> stockDateInfo;
    private List<Double> stockCloseInfo;


    public StockDtoBuilder withSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public StockDtoBuilder withStockDateInfo(List<String> stockDateInfo) {
        this.stockDateInfo = stockDateInfo;
        return this;
    }

    public StockDtoBuilder withStockCloseInfo(List<Double> stockCloseInfo) {
        this.stockCloseInfo = stockCloseInfo;
        return this;
    }

    public StockDtoBuilder withLastDate(String lastDate) {
        this.lastDate = lastDate;
        return this;
    }

    public StockDtoBuilder withVolume(Long volume) {
        this.volume = volume;
        return this;
    }

    public StockDtoBuilder withHigh(Double high) {
        this.high = high;
        return this;
    }

    public StockDtoBuilder withClose(Double close) {
        this.close = close;
        return this;
    }

    public StockDtoBuilder withOpen(Double open) {
        this.open = open;
        return this;
    }

    public StockDto buildStockDto() {
        StockDto dto = new StockDto();
        dto.setClose(this.close);
        dto.setHigh(this.high);
        dto.setStockCloseInfo(this.stockCloseInfo);
        dto.setStockDateInfo(this.stockDateInfo);
        dto.setOpen(this.open);
        dto.setVolume(this.volume);
        dto.setSymbol(this.symbol);
        dto.setLastDate(this.lastDate);
        return dto;
    }

}
