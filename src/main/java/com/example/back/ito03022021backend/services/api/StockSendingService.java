package com.example.back.ito03022021backend.services.api;

import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.example.back.ito03022021backend.dto.StockDto;
import com.example.back.ito03022021backend.dto.StockDtoBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class StockSendingService {

    private final StockService stockService;
    private final StockCalculations stockCalculations;

    /**
     * Class for sending converted stock data.
     *
     * @param stockService StockService instance
     */
    @Autowired
    public StockSendingService(@Lazy StockService stockService,@Lazy StockCalculations stockCalculations) {
        this.stockService = stockService;
        this.stockCalculations = stockCalculations;
    }

    /**
     * Return StockDto instance with latest data points (approximately 20 data points).
     *
     * @param symbol of the stock (String)
     * @return StockDto instance
     */
    public StockDto getStockDaily(String symbol) {  // Test.
        return convertToStockDto(symbol, stockService.getStockDailyWithTimePeriodOneMonth(symbol));
    }

    /**
     * Convert stock units into StockDto.
     *
     * @param symbol of the stock (String)
     * @param stockUnits list of stock units (List<StockUnit>)
     * @return StockDto instance
     */
    public StockDto convertToStockDto(String symbol, List<StockUnit> stockUnits) {  // Test.
        List<String> stockDateInfo = new LinkedList<>();
        List<Double> stockCloseInfo = new LinkedList<>();
        List<Long> stockVolumesMonthly = new ArrayList<>();
        StockUnit stockUnit = stockUnits.get(0);
        Double open = stockUnit.getOpen();
        Double close = stockUnit.getClose();
        Long volume = stockUnit.getVolume();
        Double high = stockUnit.getHigh();
        String date = stockUnit.getDate();

        for (int i = 0; i < stockUnits.size(); i++) {
            stockUnit = stockUnits.get(i);
            stockCloseInfo.add(stockUnit.getClose());
            stockDateInfo.add(stockUnit.getDate());
            stockVolumesMonthly.add(stockUnit.getVolume());
        }
        return new StockDtoBuilder()
                .withClose(close)
                .withHigh(high)
                .withOpen(open)
                .withVolume(volume)
                .withStockCloseInfo(stockCloseInfo)
                .withStockDateInfo(stockDateInfo)
                .withLastDate(date)
                .withSymbol(symbol)
                .withAverageMonthlyVolume(this.stockCalculations.getMonthlyAverageTradingVolume(stockVolumesMonthly))
                .withAverageMonthlyPrice(this.stockCalculations.getMonthlyAveragePrice(stockCloseInfo))
                .buildStockDto();

    }
}