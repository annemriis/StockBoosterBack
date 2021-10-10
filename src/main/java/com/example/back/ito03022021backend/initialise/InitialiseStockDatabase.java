package com.example.back.ito03022021backend.initialise;

import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.example.back.ito03022021backend.builders.StockBuilder;
import com.example.back.ito03022021backend.model.Stock;
import com.example.back.ito03022021backend.model.StockRepository;
import com.example.back.ito03022021backend.services.api.ApiService;
import com.example.back.ito03022021backend.services.api.StockService;

import java.util.Arrays;
import java.util.List;

import static java.lang.Thread.sleep;

public class InitialiseStockDatabase {

    private ApiService apiService = new ApiService();  // Siin on ApiService, nüüd tehakse back-endis kaks ApiServicet, mis äkki ei ole kõige parem?
    private StockService stockService = new StockService(apiService);

    public void initialiseStockDatabase(StockRepository repository, String contents) {
        List<String> symbols = Arrays.asList(contents.split(", "));
        addStocksToDatabase(symbols, repository);
    }

    public void addStocksToDatabase(List<String> symbols, StockRepository stockRepository) {
        for (int i = 0; i < symbols.size(); i++) {
            String symbol = symbols.get(i);
            List<StockUnit> stockUnits = stockService.getStockDaily(symbol);
            if (stockUnits.size() > 1) {
                // Get data for stock.
                StockUnit stockUnit1 = stockUnits.get(0);
                StockUnit stockUnit2 = stockUnits.get(1);
                Double lastClose = stockUnit2.getClose();
                Double close = stockUnit1.getClose();
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
}
