package com.example.back.ito03022021backend.services.api;

import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.example.back.ito03022021backend.dto.StockDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StockSendingServiceTest {

    // private StockService stockService = new StockService()
    // private StockSendingService stockSendingService = new StockSendingService();

    //@Test
    //void getStockDailyReturnsStockDto() {
        //StockDto stockDto = stockSendingService.getStockDaily("GOOG");
        //assertEquals(StockDto.class, stockDto.getClass());
        //assertEquals("GOOG", stockDto.getSymbol());
    //}

    //@Test
    //void convertToStockDtoReturnsStockDto() {
        //StockDto stockDto = stockSendingService.convertToStockDto("AAPL", List.of());
        //assertEquals(StockDto.class, stockDto.getClass());
        //assertEquals("AAPL", stockDto.getSymbol());
    //}
}
