package com.example.back.ito03022021backend.services.api;


import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.example.back.ito03022021backend.dto.StockDto;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StockCalculationsServiceTest {

    StockCalculations stockCalculations;
    StockSendingService stockSendingService;
    StockService stockService;

    @Autowired
    public StockCalculationsServiceTest(StockService stockService
            , StockSendingService stockSendingService
            , StockCalculations stockCalculations) {
        this.stockCalculations = stockCalculations;
        this.stockSendingService = stockSendingService;
        this.stockService = stockService;

    }

    public JSONArray getFile(String path) throws FileNotFoundException, JSONException {
        File myObj = new File(path);
        Scanner myReader = new Scanner(myObj);
        String data = "";
        while (myReader.hasNextLine()) {
            data = myReader.nextLine();
            System.out.println(data);
        }
        myReader.close();
        return new JSONArray(data);
    }

    @Test
    void testStockCalculationsOnAAPL() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        executorService.execute(new Runnable() {
            public void run() {

                List<StockUnit> stockUnits = stockService.getStockDaily("AAPL");
                Optional<StockDto> dto = stockSendingService.convertToStockDto("AAPL", stockUnits);
                List<String> stockDateInfo = new LinkedList<>();
                List<Double> stockCloseInfo = new LinkedList<>();
                List<Long> stockVolumesMonthly = new ArrayList<>();
                for (int i = 0; i < stockUnits.size(); i++) {
                    StockUnit stockUnit = stockUnits.get(i);
                    stockCloseInfo.add(stockUnit.getClose());
                    stockDateInfo.add(stockUnit.getDate());
                    stockVolumesMonthly.add(stockUnit.getVolume());
                }

                StockDto stockDto = new StockDto();
                if (dto.isPresent()) {
                    stockDto = dto.get();
                }

                double avgPrice = stockCalculations.getMonthlyAveragePrice(stockCloseInfo);
                assertEquals(avgPrice, stockDto.getAveragePriceMonthly());

                long avgVolume = stockCalculations.getMonthlyAverageTradingVolume(stockVolumesMonthly);
                assertEquals(avgVolume, stockDto.getAverageVolumeMonthly());
            }
        });

        executorService.shutdown();
    }

    @Test
    void testStockCalculationsOnGOOG() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        executorService.execute(new Runnable() {
            public void run() {

                List<StockUnit> stockUnits = stockService.getStockDaily("GOOG");
                Optional<StockDto> dto = stockSendingService.convertToStockDto("GOOG", stockUnits);
                List<String> stockDateInfo = new LinkedList<>();
                List<Double> stockCloseInfo = new LinkedList<>();
                List<Long> stockVolumesMonthly = new ArrayList<>();
                for (int i = 0; i < stockUnits.size(); i++) {
                    StockUnit stockUnit = stockUnits.get(i);
                    stockCloseInfo.add(stockUnit.getClose());
                    stockDateInfo.add(stockUnit.getDate());
                    stockVolumesMonthly.add(stockUnit.getVolume());
                }

                StockDto stockDto = new StockDto();
                if (dto.isPresent()) {
                    stockDto = dto.get();
                }

                double avgPrice = stockCalculations.getMonthlyAveragePrice(stockCloseInfo);
                assertEquals(avgPrice, stockDto.getAveragePriceMonthly());

                long avgVolume = stockCalculations.getMonthlyAverageTradingVolume(stockVolumesMonthly);
                assertEquals(avgVolume, stockDto.getAverageVolumeMonthly());
            }
        });

        executorService.shutdown();
    }
}
