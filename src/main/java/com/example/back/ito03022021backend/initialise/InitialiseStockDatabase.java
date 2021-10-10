package com.example.back.ito03022021backend.initialise;

import com.example.back.ito03022021backend.builders.StockBuilder;
import com.example.back.ito03022021backend.dto.StockDto;
import com.example.back.ito03022021backend.model.Stock;
import com.example.back.ito03022021backend.model.StockRepository;
import com.example.back.ito03022021backend.services.api.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.lang.Thread.sleep;

public class InitialiseStockDatabase {

    private final ApiService apiService = new ApiService();
    private final StockService stockService = new StockService(apiService);
    private final StockCalculations stockCalculations = new StockCalculations();
    private final StockSendingService stockSendingService = new StockSendingService(stockService, stockCalculations);

    public void initialiseStockDatabase(StockRepository repository, String contents) {
        List<String> symbols = Arrays.asList(contents.split(", "));
        addStocksToDatabase(symbols, repository);
    }

    public void addStocksToDatabase(List<String> symbols, StockRepository stockRepository) {
        symbols.forEach(symbol -> {
            System.out.println(symbol);
            StockDto stockDto = getStockDto(symbol);
            List<Double> stockCloseInfo = stockDto.getStockCloseInfo();
            if (stockCloseInfo != null && stockCloseInfo.size() > 1) {
                // Get data for stock.
                Double lastClose = stockCloseInfo.get(0);
                Double close = stockCloseInfo.get(1);
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
        });
    }

    private StockDto getStockDto(String symbol) {
        Optional<StockDto> stockDtoOptional = stockSendingService.getStockDaily(symbol);
        return stockDtoOptional.orElseGet(StockDto::new);
    }
}
