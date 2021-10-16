package com.example.back.ito03022021backend.services.api;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockCalculations {

    public Double getMonthlyAveragePrice(List<Double> closePrices) {
        double average = 0;
        for (int i = 0; i < closePrices.size(); i++) {
            average += closePrices.get(i);
        }
        return average / closePrices.size();
    }

    public Long getMonthlyAverageTradingVolume(List<Long> volumes) {
        long average = 0;
        for (int i = 0; i < volumes.size(); i++) {
            average += volumes.get(i);
        }
        return average / volumes.size();
    }

    public Double getDailyPercentageChange(Double lastClose, Double previousCloseBeforeLast) {
        return ((lastClose - previousCloseBeforeLast) / previousCloseBeforeLast) * 100;
    }

    public Double getDailyPriceChange(Double lastClose, Double previousCloseBeforeLast) {
        return lastClose - previousCloseBeforeLast;
    }

}
