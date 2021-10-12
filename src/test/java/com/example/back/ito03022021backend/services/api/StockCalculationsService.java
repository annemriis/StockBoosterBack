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
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootTest
public class StockCalculationsService {

    StockCalculations stockCalculations;
    StockSendingService stockSendingService;
    StockService stockService;

    @Autowired
    public StockCalculationsService(StockService stockService
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
    void testStockCalculations() {
        List<StockUnit> stockUnits = stockService.getStockDaily("AAPL");
        Optional<StockDto> dto = stockSendingService.convertToStockDto("AAPL", stockUnits);
        dto.ifPresent(System.out::println);
    }

}
