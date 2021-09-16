package com.example.back.ito03022021backend.contorllers;


import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.parameters.Interval;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import com.example.back.ito03022021backend.api.ApiImport;
import netscape.javascript.JSObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/externalApi")
@RestController
public class ApiController {

    private final ApiImport api;

    public ApiController() {
        this.api = ApiImport.getInstance();
    }

    // https://gitlab.cs.ttu.ee/petarv/iti0302-2021-heroes-back/-/tree/feature/api-w-db/src/main

    //Olegi naide, kuidas controller paks tootama
    @GetMapping("{symbol}")
    public void getStock(@PathVariable String symbol) {
        this.api.getAlphaVantage()
                .timeSeries()
                .intraday()
                .forSymbol(symbol)
                .interval(Interval.DAILY)
                .outputSize(OutputSize.FULL)
                .fetch();
    }


}
