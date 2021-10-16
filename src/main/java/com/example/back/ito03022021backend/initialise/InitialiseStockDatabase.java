package com.example.back.ito03022021backend.initialise;

import com.example.back.ito03022021backend.builders.StockBuilder;
import com.example.back.ito03022021backend.dto.StockDto;
import com.example.back.ito03022021backend.model.Stock;
import com.example.back.ito03022021backend.model.StockRepository;
import com.example.back.ito03022021backend.services.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

@Configuration
@EnableAsync
@EnableScheduling
public class InitialiseStockDatabase {

    private final ApiService apiService = new ApiService();
    private final StockService stockService = new StockService(apiService);
    private final StockCalculations stockCalculations = new StockCalculations();
    private final StockSendingService stockSendingService = new StockSendingService(stockService, stockCalculations);

    @Autowired
    private StockRepository stockRepository;

    public void initialiseStockDatabase(StockRepository repository, String contents) {
        List<String> symbols = Arrays.asList(contents.split(", "));
        addStocksToDatabase(symbols, repository);
    }

    public void addStocksToDatabase(List<String> symbols, StockRepository stockRepository) {
        // Method does not add all of the symbols from symbols list to the database, because it would take too much time.
        // If the API is in the server then we will add that it will add all symbols to the database.
        // Correct for loop would be (int i = 0; i < symbols.size(); i++).

        // Meetod ei käi hetkel kõiki sümboleid läbi, sest see võtaks liiga palju aega (kui keegi tahab nt oma koodi katsetada)
        // StockRepository'sse lisatakse hetkel ainult 20 aktsiat, aga nendest katsetamisl peaks piisama.
        // Õige for loop oleks (int i = 0; i < symbols.size(); i++)

        for (int i = 0; i < 20; i++) {
            String symbol = symbols.get(i);
            StockDto stockDto = getStockDto(symbol);
            List<Double> stockCloseInfo = stockDto.getStockCloseInfo();
            if (stockCloseInfo != null && stockCloseInfo.size() > 1) {
                // Get data for stock.
                Double lastClose = stockCloseInfo.get(1);
                Double close = stockCloseInfo.get(0);
                // Create new Stock instance.
                Stock stock = new StockBuilder()
                        .withSymbol(symbol)
                        .withLastClose(lastClose)
                        .withClose(close)
                        .buildStock();
                stockRepository.save(stock);
            }
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private StockDto getStockDto(String symbol) {
        Optional<StockDto> stockDtoOptional = stockSendingService.getStockDaily(symbol);
        return stockDtoOptional.orElseGet(StockDto::new);
    }

    @Scheduled(cron = "0 0 4 * * TUE-SAT", zone = "Europe/Sofia")
    public void updateStockDatabase() {
        Logger.getLogger(getClass().getName()).info("Update database");
        try {
            InputStream is = new ClassPathResource("/symbols/symbols_list.txt").getInputStream();
            String contents = new String(FileCopyUtils.copyToByteArray(is), StandardCharsets.UTF_8);
            initialiseStockDatabase(stockRepository, contents);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
