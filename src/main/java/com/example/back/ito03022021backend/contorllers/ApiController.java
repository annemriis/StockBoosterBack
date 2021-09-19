package com.example.back.ito03022021backend.contorllers;


import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.parameters.DataType;
import com.crazzyghost.alphavantage.parameters.Interval;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import com.example.back.ito03022021backend.api.ApiImport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.List;

@RequestMapping(path = "api/stocks")
@RestController
public class ApiController {

    private final ApiImport api;

    @Autowired
    public ApiController(ApiImport api) {
        this.api = api;
        this.api.setApiToIntraday();
    }


    // https://gitlab.cs.ttu.ee/petarv/iti0302-2021-heroes-back/-/tree/feature/api-w-db/src/main

    //Olegi naide, kuidas controller peaks tootama
    @GetMapping({"/daily/{symbol}"})
    public List<StockUnit> getStockDaily(@PathVariable String symbol) {
        return this.api.getAlphaVantage()
                .timeSeries()
                .daily()
                .forSymbol(symbol)
                .outputSize(OutputSize.FULL)
                .dataType(DataType.JSON)
                .fetchSync()
                .getStockUnits();
    }

    @GetMapping({"/intraday/{symbol}"})
    public List<StockUnit> getStockIntraday(@PathVariable String symbol) {
        return this.api.getAlphaVantage()
                .timeSeries()
                .intraday()
                .forSymbol(symbol)
                .outputSize(OutputSize.FULL)
                .dataType(DataType.JSON)
                .fetchSync()
                .getStockUnits();
    }


}
