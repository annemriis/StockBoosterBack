package com.example.back.ito03022021backend.services.api;

import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.example.back.ito03022021backend.dto.StockDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class StockSendingService {

    private final StockService stockService;

    @Autowired
    public StockSendingService(StockService stockService) {
        this.stockService = stockService;
    }

    public StockDto getStockDaily(String symbol) {
        // .getStockDaily() vaja muuta OneMonth meetodiks.
        return convertStock(symbol, stockService.getStockDaily(symbol));
    }

    public StockDto convertStock(String symbol, List<StockUnit> stockUnits) {
        List<List<String>> stockInfo = new LinkedList<>();
        for (int i = 0; i < stockUnits.size(); i++) {
            StockUnit stockUnit = stockUnits.get(i);
            String date = stockUnit.getDate();
            String high = String.valueOf(stockUnit.getHigh());  // Mis parameetreid oleks vaja StockUnitist võtta?
            stockInfo.add(List.of(date, high));
        }
        return new StockDto(symbol, stockInfo);
    }
}
