package com.example.back.ito03022021backend.dto;

import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.example.back.ito03022021backend.StockUnitListBuilder;
import com.example.back.ito03022021backend.services.api.StockCalculations;
import com.example.back.ito03022021backend.services.api.StockSendingService;
import com.example.back.ito03022021backend.services.api.StockService;
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
public class StockDtoTest {

    private final StockSendingService stockSendingService;
    private final StockCalculations stockCalculations;

    @Autowired
    public StockDtoTest(StockSendingService stockSendingService, StockCalculations stockCalculations) {
        this.stockSendingService = stockSendingService;
        this.stockCalculations = stockCalculations;
    }

    @Test
    public void testStockDtoOnAAPLStock() {
        StockService stockService = Mockito.mock(StockService.class);
        List<StockUnit> AAPLGetDaily = null;
        try {
            AAPLGetDaily = StockUnitListBuilder.readFromFile("AAPL_get_daily");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        when(stockService.getStockDaily("AAPL")).thenReturn(AAPLGetDaily);
        List<StockUnit> stockUnits = stockService.getStockDaily("AAPL");
        // Stock service makes StockDto.
        Optional<StockDto> stockDtoOptional = stockSendingService.convertToStockDto("AAPL", stockUnits);
        StockDto stockDto = new StockDto();
        if (stockDtoOptional.isPresent()) {
            stockDto = stockDtoOptional.get();
        }

        // Make stockDto manually.
        List<String> stockDateInfo = new LinkedList<>();
        List<Double> stockCloseInfo = new LinkedList<>();
        List<Long> stockVolumesMonthly = new ArrayList<>();
        StockUnit stockUnit = stockUnits.get(0);
        Double open = stockUnit.getOpen();
        Double close = stockUnit.getClose();
        Long volume = stockUnit.getVolume();
        Double high = stockUnit.getHigh();
        String date = stockUnit.getDate();
        Double prevClose = stockUnits.get(1).getClose();
        Double percentageChange = stockCalculations.getDailyPercentageChange(close, prevClose);
        Double priceChange = stockCalculations.getDailyPriceChange(close, prevClose);
        for (int i = 0; i < stockUnits.size(); i++) {
            stockUnit = stockUnits.get(i);
            stockCloseInfo.add(stockUnit.getClose());
            stockDateInfo.add(stockUnit.getDate());
            stockVolumesMonthly.add(stockUnit.getVolume());
        }
        StockDto stockDtoManual =  new StockDtoBuilder()
                .withClose(close)
                .withHigh(high)
                .withOpen(open)
                .withVolume(volume)
                .withStockCloseInfo(stockCloseInfo)
                .withStockDateInfo(stockDateInfo)
                .withLastDate(date)
                .withSymbol("AAPL")
                .withAverageMonthlyVolume(stockCalculations.getMonthlyAverageTradingVolume(stockVolumesMonthly))
                .withAverageMonthlyPrice(stockCalculations.getMonthlyAveragePrice(stockCloseInfo))
                .withDailyPercentageChange(percentageChange)
                .withDailyPriceChange(priceChange)
                .buildStockDto();

        // Both the stocks stats should be same.
        assertEquals(stockDto.getSymbol(), stockDtoManual.getSymbol());
        assertEquals(stockDto.getClose(), stockDtoManual.getClose());
        assertEquals(stockDto.getLastDate(), stockDtoManual.getLastDate());
        assertEquals(stockDto.getHigh(), stockDtoManual.getHigh());
        assertEquals(stockDto.getVolume(), stockDtoManual.getVolume());
        assertEquals(stockDto.getStockCloseInfo(), stockDtoManual.getStockCloseInfo());
        assertEquals(stockDto.getAveragePriceMonthly(), stockDtoManual.getAveragePriceMonthly());
        assertEquals(stockDto.getAverageVolumeMonthly(), stockDtoManual.getAverageVolumeMonthly());
        assertEquals(stockDto.getOpen(), stockDtoManual.getOpen());
        assertEquals(stockDto.getStockDateInfo(), stockDtoManual.getStockDateInfo());
        assertEquals(stockDto.getDailyPercentageChange(), stockDtoManual.getDailyPercentageChange());
        assertEquals(stockDto.getDailyPriceChange(), stockDtoManual.getDailyPriceChange());
    }
}
