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

    /**
     * Class for sending converted stock data.
     *
     * @param stockService StockService instance
     */
    @Autowired
    public StockSendingService(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * Return StockDto instance with latest data points (approximately 20 data points).
     *
     * @param symbol of the stock (String)
     * @return StockDto instance
     */
    public StockDto getStockDaily(String symbol) {
        // .getStockDaily() vaja muuta OneMonth meetodiks.
        return convertStock(symbol, stockService.getStockDaily(symbol));
    }

    /**
     * Convert stock units into StockDto.
     *
     * @param symbol of the stock (String)
     * @param stockUnits list of stock units (List<StockUnit>)
     * @return StockDto instance
     */
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
