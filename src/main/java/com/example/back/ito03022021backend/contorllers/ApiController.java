package com.example.back.ito03022021backend.contorllers;


import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.example.back.ito03022021backend.services.api.ApiService;
import com.example.back.ito03022021backend.services.api.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/api")
@RestController
public class ApiController {

    private final ApiService api;
    private final StockService stockService;

    @Autowired
    public ApiController(ApiService api, StockService stockService) {
        this.api = api;
        this.api.setApiToIntraday();
        this.stockService = stockService;
    }
    // https://gitlab.cs.ttu.ee/petarv/iti0302-2021-heroes-back/-/tree/feature/api-w-db/src/main

    @GetMapping(path = "/stock/{symbol}")
    public List<StockUnit> getStock(@PathVariable String symbol) {
        return this.stockService.getStockDaily(symbol);
    }





}
