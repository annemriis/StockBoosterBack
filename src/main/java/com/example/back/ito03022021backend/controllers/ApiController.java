package com.example.back.ito03022021backend.controllers;

import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.example.back.ito03022021backend.dto.StockDto;
import com.example.back.ito03022021backend.security.ApplicationRoles;
import com.example.back.ito03022021backend.services.api.ApiService;
import com.example.back.ito03022021backend.services.api.FindStock;
import com.example.back.ito03022021backend.services.api.StockSendingService;
import com.example.back.ito03022021backend.services.api.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
    @GetMapping(path = "/stock/{symbol}/test")
    public List<StockUnit> getStockk(@PathVariable String symbol) {
        // Header needs to be attatched to dto so that front-end can get header
        return this.stockService.getStockDaily(symbol);
    }

    @GetMapping(path = {"/stock/{symbol}", "/stock2/{symbol}"})
    public StockDto getStock(@PathVariable String symbol) {
        Optional<StockDto> stockDtoOptional = this.stockSendingService.getStockDaily(symbol);
        return stockDtoOptional.orElseGet(StockDto::new);
    }

    @GetMapping(path = "/stock/{symbol}?time-series=intraday")
    public List<StockUnit> getStockIntraday(@PathVariable String symbol) {
        return this.stockService.getStockIntraday(symbol);
    }

    @Secured(ApplicationRoles.USER)
    @GetMapping(path = "/stock/{symbol}/boost-morale")
    public StockDto getStockWithWorsePerformance(@PathVariable String symbol) {
        Optional<StockDto> stockDtoOptional = this.findStock.findStockWithWorsePerformance(symbol);
        return stockDtoOptional.orElseGet(StockDto::new);
    }
}
