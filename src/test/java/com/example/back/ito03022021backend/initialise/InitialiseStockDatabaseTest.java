package com.example.back.ito03022021backend.initialise;

import com.example.back.ito03022021backend.dto.StockDto;
import com.example.back.ito03022021backend.dto.StockDtoBuilder;
import com.example.back.ito03022021backend.repositories.StockRepository;
import com.example.back.ito03022021backend.services.api.ApiService;
import com.example.back.ito03022021backend.services.api.StockSendingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class InitialiseStockDatabaseTest {

    private final StockRepository stockRepository;

    @Autowired
    public InitialiseStockDatabaseTest(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Test
    void testAddStockToDatabase() {
        StockSendingService stockSendingService = Mockito.mock(StockSendingService.class);
        StockDto AAPLStockDto = new StockDtoBuilder()
                .withClose(192.4)
                .withStockCloseInfo(List.of(192.4, 293.4, 176.2))
                .withSymbol("AAPL")
                .buildStockDto();
        StockDto GOOGStockDto = new StockDtoBuilder()
                .withClose(132.4)
                .withStockCloseInfo(List.of(132.4, 2923.4, 1736.2))
                .withSymbol("GOOG")
                .buildStockDto();
        StockDto MMMStockDto = new StockDtoBuilder()
                .withClose(1932.4)
                .withStockCloseInfo(List.of(1932.4, 2293.4, 1736.2))
                .withSymbol("MMM")
                .buildStockDto();
        StockDto ABTStockDto = new StockDtoBuilder()
                .withClose(1932.4)
                .withStockCloseInfo(List.of(1932.4, 2943.4, 1276.2))
                .withSymbol("ABT")
                .buildStockDto();
        StockDto ABBVStockDto = new StockDtoBuilder()
                .withClose(192.4)
                .withStockCloseInfo(List.of(192.4, 2933.4, 176.2))
                .withSymbol("ABBV")
                .buildStockDto();
        when(stockSendingService.getStockDaily("AAPL")).thenReturn(Optional.of(AAPLStockDto));
        when(stockSendingService.getStockDaily("GOOG")).thenReturn(Optional.of(GOOGStockDto));
        when(stockSendingService.getStockDaily("MMM")).thenReturn(Optional.of(MMMStockDto));
        when(stockSendingService.getStockDaily("ABT")).thenReturn(Optional.of(ABTStockDto));
        when(stockSendingService.getStockDaily("ABBV")).thenReturn(Optional.of(ABBVStockDto));
        List<String> symbols = List.of("AAPL", "GOOG", "MMM", "ABT", "ABBV");
        ApiService apiService = new ApiService();
        apiService.initialiseApiService(System.getProperty("api.key"));
        InitialiseStockDatabase initialiseStockDatabase = new InitialiseStockDatabase(apiService);
        initialiseStockDatabase.setStockSendingService(stockSendingService);

        initialiseStockDatabase.addStocksToDatabase(symbols, stockRepository);

        // Stocks are in the database.
        assertNotNull(stockRepository.findStockBySymbol("AAPL"));
        assertEquals("AAPL", stockRepository.findStockBySymbol("AAPL").getSymbol());
        assertEquals("GOOG", stockRepository.findStockBySymbol("GOOG").getSymbol());
        assertEquals("MMM", stockRepository.findStockBySymbol("MMM").getSymbol());
        assertEquals("ABT", stockRepository.findStockBySymbol("ABT").getSymbol());
        assertEquals("ABBV", stockRepository.findStockBySymbol("ABBV").getSymbol());

        // Close and lastClose are in the database.
        assertEquals(AAPLStockDto.getStockCloseInfo().get(1), stockRepository.findStockBySymbol("AAPL").getLastClose());
        assertEquals(AAPLStockDto.getClose(), stockRepository.findStockBySymbol("AAPL").getClose());
    }

    @Test
    void testInitialiseStockDatabase() {
        StockSendingService stockSendingService = Mockito.mock(StockSendingService.class);
        StockDto GOOGStockDto = new StockDtoBuilder()
                .withClose(234.5)
                .withStockCloseInfo(List.of(234.5, 345.5, 123.5))
                .withSymbol("GOOG")
                .buildStockDto();
        StockDto AAPLStockDto = new StockDtoBuilder()
                .withClose(192.4)
                .withStockCloseInfo(List.of(192.4, 293.4, 176.2))
                .withSymbol("AAPL")
                .buildStockDto();
        StockDto MMMStockDto = new StockDtoBuilder()
                .withClose(192.4)
                .withStockCloseInfo(List.of(192.4, 2393.4, 176.2))
                .withSymbol("MMM")
                .buildStockDto();
        StockDto ABTStockDto = new StockDtoBuilder()
                .withClose(192.4)
                .withStockCloseInfo(List.of(192.4, 2393.4, 176.2))
                .withSymbol("ABT")
                .buildStockDto();
        StockDto ABBVStockDto = new StockDtoBuilder()
                .withClose(1922.4)
                .withStockCloseInfo(List.of(1922.4, 293.4, 176.2))
                .withSymbol("ABBV")
                .buildStockDto();
        when(stockSendingService.getStockDaily("GOOG")).thenReturn(Optional.of(GOOGStockDto));
        when(stockSendingService.getStockDaily("AAPL")).thenReturn(Optional.of(AAPLStockDto));
        when(stockSendingService.getStockDaily("MMM")).thenReturn(Optional.of(MMMStockDto));
        when(stockSendingService.getStockDaily("ABT")).thenReturn(Optional.of(ABTStockDto));
        when(stockSendingService.getStockDaily("ABBV")).thenReturn(Optional.of(ABBVStockDto));
        String contents = "AAPL, GOOG, MMM, ABT, ABBV";

        ApiService apiService = new ApiService();
        apiService.initialiseApiService(System.getProperty("api.key"));
        InitialiseStockDatabase initialiseStockDatabase = new InitialiseStockDatabase(apiService);
        initialiseStockDatabase.setStockSendingService(stockSendingService);

        initialiseStockDatabase.initialiseStockDatabase(stockRepository, contents);

        // Stocks are in the database.
        assertNotNull(stockRepository.findStockBySymbol("AAPL"));
        assertEquals("AAPL", stockRepository.findStockBySymbol("AAPL").getSymbol());
        assertEquals("GOOG", stockRepository.findStockBySymbol("GOOG").getSymbol());
        assertEquals("MMM", stockRepository.findStockBySymbol("MMM").getSymbol());
        assertEquals("ABT", stockRepository.findStockBySymbol("ABT").getSymbol());
        assertEquals("ABBV", stockRepository.findStockBySymbol("ABBV").getSymbol());

        // Close and lastClose are in the database.
        assertEquals(GOOGStockDto.getStockCloseInfo().get(1), stockRepository.findStockBySymbol("GOOG").getLastClose());
        assertEquals(GOOGStockDto.getClose(), stockRepository.findStockBySymbol("GOOG").getClose());
    }
}
