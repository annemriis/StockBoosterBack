package com.example.back.ito03022021backend.contorllers;


import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.parameters.Interval;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import com.example.back.ito03022021backend.api.ApiImport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping(path = "api/v1/stocks")
@RestController
public class ApiController {

    private final ApiImport api;

    @Autowired
    public ApiController(ApiImport api) {
        this.api = api;
        this.api.setApiToIntraday();
    }

    @GetMapping
    public String getHello() {
        return "hello";
    }

    // https://gitlab.cs.ttu.ee/petarv/iti0302-2021-heroes-back/-/tree/feature/api-w-db/src/main

    //Olegi naide, kuidas controller peaks tootama
    @GetMapping({"IBM"})
    public List<StockUnit> getStock(@PathVariable String symbol) {
        return this.api.getAlphaVantage()
                .timeSeries()
                .intraday()
                .forSymbol("IBM")
                .interval(Interval.DAILY)
                .outputSize(OutputSize.FULL)
                .fetchSync()
                .getStockUnits();
    }


}
