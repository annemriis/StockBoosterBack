package com.example.back.ito03022021backend.services.api;

import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.example.back.ito03022021backend.dto.StockDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class StockSendingServiceTest {

    private ApiService apiService = new ApiService();
    private StockService stockService = new StockService(apiService);
    private StockSendingService stockSendingService = new StockSendingService(stockService);

    @Test
    void getStockDailyReturnsStockDto() {
        StockDto stockDto = stockSendingService.getStockDaily("GOOG");
        assertEquals(StockDto.class, stockDto.getClass());
        assertEquals("GOOG", stockDto.getSymbol());

        // Current date.
        Date date = new Date();
        String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        int day = Integer.parseInt(modifiedDate.substring(8));
        int month = Integer.parseInt(modifiedDate.substring(5, 7));
        int year = Integer.parseInt(modifiedDate.substring(0, 4));

        // Test day, month and year.
        assertTrue(day == Integer.parseInt(stockDto.getLastDate().substring(8))  // Day.
                || day - 1 == Integer.parseInt(stockDto.getLastDate().substring(8))
                || day - 2 == Integer.parseInt(stockDto.getLastDate().substring(8)));
        assertTrue(month == Integer.parseInt(stockDto.getLastDate().substring(5, 7))
                || month - 1 == Integer.parseInt(stockDto.getLastDate().substring(5, 7)));  // Month.
        assertEquals(year, Integer.parseInt(stockDto.getLastDate().substring(0, 4)));  // Year.
    }

    @Test
    void convertToStockDtoReturnsStockDto() {
        List<StockUnit> stockUnits = stockService.getStockDailyWithTimePeriodOneMonth("AAPL");
        StockDto stockDto = stockSendingService.convertToStockDto("AAPL", stockUnits);
        assertEquals(StockDto.class, stockDto.getClass());
        assertEquals("AAPL", stockDto.getSymbol());

        // Current date.
        Date date = new Date();
        String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        int day = Integer.parseInt(modifiedDate.substring(8));
        int month = Integer.parseInt(modifiedDate.substring(5, 7));
        int year = Integer.parseInt(modifiedDate.substring(0, 4));

        // Test day, month and year.
        assertTrue(day == Integer.parseInt(stockDto.getLastDate().substring(8))  // Day.
                || day - 1 == Integer.parseInt(stockDto.getLastDate().substring(8))
                || day - 2 == Integer.parseInt(stockDto.getLastDate().substring(8)));
        assertTrue(month == Integer.parseInt(stockDto.getLastDate().substring(5, 7))  // Month.
                || month - 1 == Integer.parseInt(stockDto.getLastDate().substring(5, 7)));
        assertEquals(year, Integer.parseInt(stockDto.getLastDate().substring(0, 4)));  // Year.
    }
}
