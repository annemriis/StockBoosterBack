package com.example.back.ito03022021backend.dto;

import java.util.List;

public class StockDto {

    private String symbol;
    private Double open;
    private Double close;
    private Double high;
    private Long volume;
    private String lastDate;
    private List<String> stockDateInfo;
    private List<Double> stockCloseInfo;
    private Double averagePriceMonthly;
    private Long averageVolumeMonthly;
    private Double dailyPercentageChange;
    private Double dailyPriceChange;

    public StockDto() {
    }

    public Double getAveragePriceMonthly() {
        return averagePriceMonthly;
    }

    public void setAveragePriceMonthly(Double averagePriceMonthly) {
        this.averagePriceMonthly = averagePriceMonthly;
    }

    public Long getAverageVolumeMonthly() {
        return averageVolumeMonthly;
    }

    public void setAverageVolumeMonthly(Long averageVolumeMonthly) {
        this.averageVolumeMonthly = averageVolumeMonthly;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public List<String> getStockDateInfo() {
        return stockDateInfo;
    }

    public void setStockDateInfo(List<String> stockDateInfo) {
        this.stockDateInfo = stockDateInfo;
    }

    public List<Double> getStockCloseInfo() {
        return stockCloseInfo;
    }

    public void setStockCloseInfo(List<Double> stockCloseInfo) {
        this.stockCloseInfo = stockCloseInfo;
    }

    public Double getDailyPercentageChange() {
        return dailyPercentageChange;
    }

    public void setDailyPercentageChange(Double dailyPercentageChange) {
        this.dailyPercentageChange = dailyPercentageChange;
    }

    public Double getDailyPriceChange() {
        return dailyPriceChange;
    }

    public void setDailyPriceChange(Double dailyPriceChange) {
        this.dailyPriceChange = dailyPriceChange;
    }
}
