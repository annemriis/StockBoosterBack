package com.example.back.ito03022021backend.services.api;

import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.example.back.ito03022021backend.StockUnitListBuilder;
import com.example.back.ito03022021backend.dto.StockDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StockSendingServiceTest {

    private final ApiService apiService = new ApiService();
    private final StockService stockService = new StockService(apiService);
    private final StockCalculations stockCalculations = new StockCalculations();
    private final StockSendingService stockSendingService = new StockSendingService(stockService, stockCalculations);

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
