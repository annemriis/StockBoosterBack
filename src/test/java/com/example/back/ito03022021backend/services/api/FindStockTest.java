package com.example.back.ito03022021backend.services.api;

import com.example.back.ito03022021backend.builders.StockBuilder;
import com.example.back.ito03022021backend.dto.StockDto;
import com.example.back.ito03022021backend.dto.StockDtoBuilder;
import com.example.back.ito03022021backend.model.Stock;
import com.example.back.ito03022021backend.repositories.StockRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class FindStockTest {

    @Test
    void testFindStockWithWorsePerformance() {
        StockRepository stockRepository = Mockito.mock(StockRepository.class);
        StockSendingService stockSendingService = Mockito.mock(StockSendingService.class);
        FindStock findStock = new FindStock(stockSendingService, stockRepository);

        List<Stock> stocks = new LinkedList<>();
        List<String> symbols = List.of("AAPL", "GOOG", "MMM", "ABT", "ABBV");
        List<Double> lastCloseData = List.of(103.5, 244.5, 543.5, 445.6, 455.7);
        List<Double> closeData = List.of(120.4, 247.1, 100.3, 566.4, 789.5);
        for (int i = 0; i < symbols.size(); i++) {
            String symbol = symbols.get(i);
            Double lastClose = lastCloseData.get(i);
            Double close = closeData.get(i);
            Stock stock = new StockBuilder()
                    .withSymbol(symbol)
                    .withLastClose(lastClose)
                    .withClose(close)
                    .buildStock();
            stocks.add(stock);
        }
        StockDto stockDtoGOOG = new StockDtoBuilder()
                .withSymbol("GOOG")
                .withStockCloseInfo(List.of(300.2, 368.5, 456.4))
                .withClose(300.2)
                .buildStockDto();
        Optional<StockDto> stockDtoOptionalGOOG = Optional.of(stockDtoGOOG);

        StockDto stockDtoMMM = new StockDtoBuilder()
                .withSymbol("MMM")
                .withStockCloseInfo(List.of(100.3, 543.5))
                .withClose(100.3)
                .buildStockDto();
        Optional<StockDto> stockDtoOptionalMMM = Optional.of(stockDtoMMM);

        when(stockRepository.findAll()).thenReturn(stocks);
        when(stockSendingService.getStockDaily("GOOG")).thenReturn(stockDtoOptionalGOOG);
        when(stockSendingService.getStockDaily("MMM")).thenReturn(stockDtoOptionalMMM);

        Optional<StockDto> stockDtoOptionalWorsePerformance= findStock.findStockWithWorsePerformance("GOOG");
        StockDto stockDto = stockDtoOptionalWorsePerformance.orElseGet(StockDto::new);
        assertEquals("MMM", stockDto.getSymbol());
        assertEquals(100.3, stockDto.getClose());
    }
}
