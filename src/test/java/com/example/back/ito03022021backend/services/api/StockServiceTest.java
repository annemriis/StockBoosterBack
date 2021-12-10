package com.example.back.ito03022021backend.services.api;

import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class StockServiceTest {

    private final StockService stockService;

    @Autowired
    public StockServiceTest(StockService stockService) {
        this.stockService = stockService;
    }

    @Test
    void filterStockByDateOneMonthFiltersListWithStockUnits() {
        List<StockUnit> stockUnits = stockService.getStockDaily("TWTR");
        assertEquals(100, stockUnits.size());

        // Current date.
        Date date = new Date();
        LocalDate localDate = LocalDate.now();
        int day = localDate.getDayOfMonth();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();

        List<StockUnit> filteredStockUnits = stockService.filterStockByDateOneMonth(stockUnits, localDate);
        assertTrue(30 > filteredStockUnits.size());
        assertTrue(20 <= filteredStockUnits.size());

        StockUnit stockUnit = stockUnits.get(0);
        assertEquals(StockUnit.class, stockUnit.getClass());
        // Test day, month and year.
        assertTrue(day == Integer.parseInt(stockUnit.getDate().substring(8))
                || day - 1 == Integer.parseInt(stockUnit.getDate().substring(8))
                || day - 2 == Integer.parseInt(stockUnit.getDate().substring(8)));
        assertTrue(month == Integer.parseInt(stockUnit.getDate().substring(5, 7))
                || month - 1 == Integer.parseInt(stockUnit.getDate().substring(5, 7)));
        assertEquals(year, Integer.parseInt(stockUnit.getDate().substring(0, 4)));
    }

    @Test
    void getStockDailyWithTimePeriodOneMonthReturnsListWithStockUnits() {
        List<StockUnit> stockUnits = stockService.getStockDailyWithTimePeriodOneMonth("GOOG");
        assertTrue(30 > stockUnits.size());
    }
}
