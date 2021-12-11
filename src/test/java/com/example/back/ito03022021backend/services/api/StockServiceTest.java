package com.example.back.ito03022021backend.services.api;

import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.example.back.ito03022021backend.StockUnitListBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

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
        StockService stockService = Mockito.mock(StockService.class);
        List<StockUnit> mockStockUnits = null;
        try {
            mockStockUnits = StockUnitListBuilder.readFromFile("AAPL_get_daily");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        when(stockService.getStockDaily("AAPL")).thenReturn(mockStockUnits);
        List<StockUnit> stockUnits = stockService.getStockDaily("AAPL");

        LocalDate localDate = LocalDate.parse("2021-11-12");

        List<StockUnit> filteredStockUnits = stockService.filterStockByDateOneMonth(stockUnits, localDate);
        assertTrue(30 > filteredStockUnits.size());
    }

    @Test
    void getStockDailyWithTimePeriodOneMonthReturnsListWithStockUnits() {
        List<StockUnit> stockUnits = stockService.getStockDailyWithTimePeriodOneMonth("GOOG");
        assertTrue(30 > stockUnits.size());
    }
}
