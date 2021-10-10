package com.example.back.ito03022021backend.contorllers;


import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.example.back.ito03022021backend.dto.StockDto;
import com.example.back.ito03022021backend.services.api.ApiService;
import com.example.back.ito03022021backend.services.api.StockSendingService;
import com.example.back.ito03022021backend.services.api.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpHeaders;
import java.util.List;
import java.util.Optional;

@RequestMapping(path = "/api")
@RestController
public class ApiController {

    private final ApiService api;
    private final StockService stockService;
    private final StockSendingService stockSendingService;

    @Autowired
    public ApiController(ApiService api, StockService stockService, StockSendingService stockSendingService) {
        this.api = api;
        this.api.setApiToIntraday();
        this.stockService = stockService;
        this.stockSendingService = stockSendingService;
    }
    // https://gitlab.cs.ttu.ee/petarv/iti0302-2021-heroes-back/-/tree/feature/api-w-db/src/main

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path = "/stock/{symbol}")
    public StockDto getStock(@PathVariable String symbol) {  // Test.
        // Header needs to be attatched to dto so that front-end can get header
        Optional<StockDto> stockDtoOptional = this.stockSendingService.getStockDaily(symbol);
        return stockDtoOptional.orElseGet(StockDto::new);
    }

    @GetMapping(path = "/stock/{symbol}?time-series=intraday")
    public List<StockUnit> getStockIntraday(@PathVariable String symbol) {
        return this.stockService.getStockIntraday(symbol);
    }


}
