package com.example.back.ito03022021backend.services.api;

import com.crazzyghost.alphavantage.parameters.DataType;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.QuoteResponse;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class StockService {
    private final ApiService api;

    @Autowired
    public StockService(ApiService apiService) {
        this.api = apiService;
    }

    public QuoteResponse getQuote(String symbol) {
        return this.api
                .getAlphaVantage()
                .timeSeries()
                .quote()
                .forSymbol(symbol)
                .fetchSync();
    }


    public List<StockUnit> getStockDaily(String symbol) {
        return this.api.getAlphaVantage()
                .timeSeries()
                .daily()
                .forSymbol(symbol)
                .outputSize(OutputSize.FULL)
                .dataType(DataType.JSON)
                .fetchSync()
                .getStockUnits();
    }

    public List<StockUnit> getStockIntraday(String symbol) {
        return this.api.getAlphaVantage()
                .timeSeries()
                .intraday()
                .forSymbol(symbol)
                .outputSize(OutputSize.FULL)
                .dataType(DataType.JSON)
                .fetchSync()
                .getStockUnits();
    }

}
