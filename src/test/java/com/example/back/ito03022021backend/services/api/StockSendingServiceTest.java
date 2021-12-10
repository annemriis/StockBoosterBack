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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class StockSendingServiceTest {

    private final StockSendingService stockSendingService;

    @Autowired
    public StockSendingServiceTest(StockSendingService stockSendingService) {
        this.stockSendingService = stockSendingService;
    }

    @Test
    void convertToStockDtoReturnsStockDto() {
        StockService stockService = Mockito.mock(StockService.class);
        List<StockUnit> AAPLGetDaily = null;
        try {
            AAPLGetDaily = StockUnitListBuilder.readFromFile("AAPL_get_daily");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        when(stockService.getStockDaily("AAPL")).thenReturn(AAPLGetDaily);
        List<StockUnit> stockUnits = stockService.getStockDaily("AAPL");
        Optional<StockDto> stockDtoOptional = stockSendingService.convertToStockDto("AAPL", stockUnits);
        StockDto stockDto;
        if (stockDtoOptional.isEmpty()) {
            fail();
        }
        stockDto = stockDtoOptional.get();
        assertEquals(StockDto.class, stockDto.getClass());
        assertEquals("AAPL", stockDto.getSymbol());
        assertEquals(148.96, stockDto.getOpen());
        assertEquals(149.43, stockDto.getHigh());
        assertEquals(147.87, stockDto.getClose());
    }
}
