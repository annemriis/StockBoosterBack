package com.example.back.ito03022021backend.services.api;

import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.example.back.ito03022021backend.StockUnitListBuilder;
import com.example.back.ito03022021backend.dto.StockDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class StockCalculationsServiceTest {

    StockCalculations stockCalculations;
    StockSendingService stockSendingService;
    StockService stockService;

    @Autowired
    public StockCalculationsServiceTest(StockService stockService, StockSendingService stockSendingService,
                                        StockCalculations stockCalculations) {
        this.stockCalculations = stockCalculations;
        this.stockSendingService = stockSendingService;
        this.stockService = stockService;

    }

    @Test
    void testStockCalculationsOnAAPL() {
        StockService stockService = Mockito.mock(StockService.class);
        List<StockUnit> stockUnits = null;
        try {
            stockUnits = StockUnitListBuilder.readFromFile("AAPL_get_daily");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        when(stockService.getStockDailyWithTimePeriodOneMonth("AAPL")).thenReturn(stockUnits);
        assert stockUnits != null;
        Optional<StockDto> dto = stockSendingService.convertToStockDto("AAPL", stockUnits);
        List<String> stockDateInfo = new LinkedList<>();
        List<Double> stockCloseInfo = new LinkedList<>();
        List<Long> stockVolumesMonthly = new ArrayList<>();
        Double close = stockUnits.get(0).getClose();
        Double prevClose = stockUnits.get(1).getClose();
        for (int i = 0; i < stockUnits.size(); i++) {
            StockUnit stockUnit = stockUnits.get(i);
            stockCloseInfo.add(stockUnit.getClose());
            stockDateInfo.add(stockUnit.getDate());
            stockVolumesMonthly.add(stockUnit.getVolume());
        }

        StockDto stockDto = new StockDto();
        if (dto.isPresent()) {
            stockDto = dto.get();
        }

        Double percentageChange = stockCalculations.getDailyPercentageChange(close, prevClose);
        assertEquals(stockDto.getDailyPercentageChange(), percentageChange);

        Double priceChange = stockCalculations.getDailyPriceChange(close, prevClose);
        assertEquals(stockDto.getDailyPriceChange(), priceChange);

        double avgPrice = stockCalculations.getMonthlyAveragePrice(stockCloseInfo);
        assertEquals(avgPrice, stockDto.getAveragePriceMonthly());

        long avgVolume = stockCalculations.getMonthlyAverageTradingVolume(stockVolumesMonthly);
        assertEquals(avgVolume, stockDto.getAverageVolumeMonthly());
    }

    @Test
    void testStockCalculationsOnGOOG() {
        StockService stockService = Mockito.mock(StockService.class);
        List<StockUnit> stockUnits = null;
        try {
            stockUnits = StockUnitListBuilder.readFromFile("GOOG_get_daily");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        when(stockService.getStockDailyWithTimePeriodOneMonth("GOOG")).thenReturn(stockUnits);
        assert stockUnits != null;
        Optional<StockDto> dto = stockSendingService.convertToStockDto("GOOG", stockUnits);
        List<String> stockDateInfo = new LinkedList<>();
        List<Double> stockCloseInfo = new LinkedList<>();
        List<Long> stockVolumesMonthly = new ArrayList<>();
        for (int i = 0; i < stockUnits.size(); i++) {
            StockUnit stockUnit = stockUnits.get(i);
            stockCloseInfo.add(stockUnit.getClose());
            stockDateInfo.add(stockUnit.getDate());
            stockVolumesMonthly.add(stockUnit.getVolume());
        }

        StockDto stockDto = new StockDto();
        if (dto.isPresent()) {
            stockDto = dto.get();
        }

        double avgPrice = stockCalculations.getMonthlyAveragePrice(stockCloseInfo);
        assertEquals(avgPrice, stockDto.getAveragePriceMonthly());

        long avgVolume = stockCalculations.getMonthlyAverageTradingVolume(stockVolumesMonthly);
        assertEquals(avgVolume, stockDto.getAverageVolumeMonthly());
    }
}
