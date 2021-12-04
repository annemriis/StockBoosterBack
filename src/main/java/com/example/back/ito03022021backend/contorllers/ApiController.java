package com.example.back.ito03022021backend.contorllers;


import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.example.back.ito03022021backend.dto.StockDto;
import com.example.back.ito03022021backend.services.api.ApiService;
import com.example.back.ito03022021backend.services.api.FindStock;
import com.example.back.ito03022021backend.services.api.StockSendingService;
import com.example.back.ito03022021backend.services.api.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping({"/api", "/api-test"})
@RestController
public class ApiController {

    private final ApiService api;
    private final StockService stockService;
    private final StockSendingService stockSendingService;
    private final FindStock findStock;

    @Autowired
    public ApiController(ApiService api, StockService stockService, StockSendingService stockSendingService,
                         FindStock findStock) {
        this.api = api;
        this.api.setApiToIntraday();
        this.stockService = stockService;
        this.stockSendingService = stockSendingService;
        this.findStock = findStock;
    }
    // https://gitlab.cs.ttu.ee/petarv/iti0302-2021-heroes-back/-/tree/feature/api-w-db/src/main

    /**
     * This is for testing purposes only
     * @param symbol of the stock
     * @return list of stock unit data
     */
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path = "/stock/{symbol}/test")
    public List<StockUnit> getStockk(@PathVariable String symbol) {
        // Header needs to be attatched to dto so that front-end can get header
        return this.stockService.getStockDaily(symbol);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path = "/stock/{symbol}")
    public StockDto getStock(@PathVariable String symbol) {
        // Header needs to be attatched to dto so that front-end can get header
        Optional<StockDto> stockDtoOptional = this.stockSendingService.getStockDaily(symbol);
        return stockDtoOptional.orElseGet(StockDto::new);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path = "/stock/{symbol}?time-series=intraday")
    public List<StockUnit> getStockIntraday(@PathVariable String symbol) {
        return this.stockService.getStockIntraday(symbol);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path = "/stock/{symbol}/boost-morale")
    public StockDto getStockWithWorsePerformance(@PathVariable String symbol) {
        Optional<StockDto> stockDtoOptional = this.findStock.findStockWithWorsePerformance(symbol);
        return stockDtoOptional.orElseGet(StockDto::new);
    }
}
