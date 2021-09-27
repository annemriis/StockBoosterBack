package com.example.back.ito03022021backend.services.api;

import com.crazzyghost.alphavantage.parameters.DataType;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.QuoteResponse;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {
    private final ApiService api;

    @Autowired
    public StockService(ApiService apiService) {
        this.api = apiService;
    }

    public QuoteResponse getQuote(String symbol) {
        return this.api
                .getAlphaVantage()
                .timeSeries()
                .quote()
                .forSymbol(symbol)
                .fetchSync();
    }


    public List<StockUnit> getStockDaily(String symbol) {
        return this.api.getAlphaVantage()
                .timeSeries()
                .daily()
                .forSymbol(symbol)
                .outputSize(OutputSize.COMPACT)
                .dataType(DataType.JSON)
                .fetchSync()
                .getStockUnits();
    }

    /**
     * Return a list with stock units within the current month.
     *
     * @param symbol of the stock (String)
     * @return List with stock stock units within the current month.
     */
    public List<StockUnit> getStockDailyWithTimePeriodOneMonth(String symbol) {
        List<StockUnit> stockUnits = this.api.getAlphaVantage()
                .timeSeries()
                .daily()
                .forSymbol(symbol)
                .outputSize(OutputSize.COMPACT)
                .dataType(DataType.JSON)
                .fetchSync()
                .getStockUnits();
        String[] stockUnitDate = stockUnits.get(0).getDate().split("-");
        int day = Integer.parseInt(stockUnitDate[2]);
        int month = Integer.parseInt(stockUnitDate[1]);
        int year = Integer.parseInt(stockUnitDate[0]);
        return filterStockByDateOneMonth(stockUnits, day, month, year);
    }

    /**
     * Filter stock units.
     *
     * @param stockUnits list of stock units (List<StockUnit>)
     * @param day current day (int)
     * @param month current month (int)
     * @param year current year (int)
     * @return List with stock units within the current month.
     */
    public List<StockUnit> filterStockByDateOneMonth(List<StockUnit> stockUnits, int day, int month, int year) {
        List<StockUnit> filteredStockUnits = new ArrayList<>();
        for (int i = 0; i < stockUnits.size(); i++) {
            StockUnit stockUnit = stockUnits.get(i);
            String[] stockUnitDate = stockUnit.getDate().split("-");  // Year-Month-Day
            int stockUnitDay = Integer.parseInt(stockUnitDate[2]);
            int stockUnitMonth = Integer.parseInt(stockUnitDate[1]);
            int stockUnitYear = Integer.parseInt(stockUnitDate[0]);
            // Filter stock units by date.
            if ((day - stockUnitDay < 0 && stockUnitMonth == month - 1)  // Date is from the previous month.
                    || (stockUnitDay <= day && stockUnitMonth == month)  // Current month.
                    && stockUnitYear == year) { // Loogika võib vigane olla.
                filteredStockUnits.add(stockUnit);
            } else {
                break;
            }
        }
        return filteredStockUnits;
    }

    public List<StockUnit> getStockIntraday(String symbol) {
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
