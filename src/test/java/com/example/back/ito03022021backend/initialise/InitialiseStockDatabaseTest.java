package com.example.back.ito03022021backend.initialise;

import com.example.back.ito03022021backend.dto.StockDto;
import com.example.back.ito03022021backend.repositories.StockRepository;
import com.example.back.ito03022021backend.services.api.ApiService;
import com.example.back.ito03022021backend.services.api.StockCalculations;
import com.example.back.ito03022021backend.services.api.StockSendingService;
import com.example.back.ito03022021backend.services.api.StockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class InitialiseStockDatabaseTest {

    private final ApiService apiService = new ApiService();
    private final StockService stockService = new StockService(apiService);
    private final StockCalculations stockCalculations = new StockCalculations();
    private final StockSendingService stockSendingService = new StockSendingService(stockService, stockCalculations);

    @Autowired
    private StockRepository stockRepository;

    @Test
    void testAddStockToDatabase() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        executorService.execute(new Runnable() {
            public void run() {

                List<String> symbols = List.of("AAPL", "GOOG", "MMM", "ABT", "ABBV");
                InitialiseStockDatabase initialiseStockDatabase = new InitialiseStockDatabase();

                initialiseStockDatabase.addStocksToDatabase(symbols, stockRepository);

                // Stocks are in the database.
                assertNotNull(stockRepository.findStockBySymbol("AAPL"));
                assertEquals("AAPL", stockRepository.findStockBySymbol("AAPL").getSymbol());
                assertEquals("GOOG", stockRepository.findStockBySymbol("GOOG").getSymbol());
                assertEquals("MMM", stockRepository.findStockBySymbol("MMM").getSymbol());
                assertEquals("ABT", stockRepository.findStockBySymbol("ABT").getSymbol());
                assertEquals("ABBV", stockRepository.findStockBySymbol("ABBV").getSymbol());

                Optional<StockDto> AAPLStockDtoOptional = stockSendingService.getStockDaily("AAPL");
                StockDto AAPLStockDto = new StockDto();
                if (AAPLStockDtoOptional.isPresent()) {
                    AAPLStockDto = AAPLStockDtoOptional.get();
                }

                // Close and lastClose are in the database.
                assertEquals(AAPLStockDto.getStockCloseInfo().get(1), stockRepository.findStockBySymbol("AAPL").getLastClose());
                assertEquals(AAPLStockDto.getClose(), stockRepository.findStockBySymbol("AAPL").getClose());
            }
        });

        executorService.shutdown();
    }

    @Test
    void testInitialiseStockDatabase() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        executorService.execute(new Runnable() {
            public void run() {

                String contents = "AAPL, GOOG, MMM, ABT, ABBV";

                InitialiseStockDatabase initialiseStockDatabase = new InitialiseStockDatabase();

                initialiseStockDatabase.initialiseStockDatabase(stockRepository, contents);

                // Stocks are in the database.
                assertNotNull(stockRepository.findStockBySymbol("AAPL"));
                assertEquals("AAPL", stockRepository.findStockBySymbol("AAPL").getSymbol());
                assertEquals("GOOG", stockRepository.findStockBySymbol("GOOG").getSymbol());
                assertEquals("MMM", stockRepository.findStockBySymbol("MMM").getSymbol());
                assertEquals("ABT", stockRepository.findStockBySymbol("ABT").getSymbol());
                assertEquals("ABBV", stockRepository.findStockBySymbol("ABBV").getSymbol());

                Optional<StockDto> GOOGStockDtoOptional = stockSendingService.getStockDaily("GOOG");
                StockDto GOOGStockDto = new StockDto();
                if (GOOGStockDtoOptional.isPresent()) {
                    GOOGStockDto = GOOGStockDtoOptional.get();
                }

                // Close and lastClose are in the database.
                assertEquals(GOOGStockDto.getStockCloseInfo().get(1), stockRepository.findStockBySymbol("GOOG").getLastClose());
                assertEquals(GOOGStockDto.getClose(), stockRepository.findStockBySymbol("GOOG").getClose());
            }
        });

        executorService.shutdown();
    }
}
