package com.example.back.ito03022021backend.services.api;

import com.crazzyghost.alphavantage.parameters.DataType;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {
    private final ApiService api;

    @Autowired
    public StockService(ApiService apiService) {
        this.api = apiService;
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
        if (stockUnits.size() > 0) {
            String stockUnitDate = stockUnits.get(0).getDate();
            LocalDate stockUnitLocalDate = LocalDate.parse(stockUnitDate);
            return filterStockByDateOneMonth(stockUnits, stockUnitLocalDate);
        }
        return List.of();
    }

    /**
     * Filter stock units.
     *
     * @param stockUnits list of stock units (List<StockUnit>)
     * @param currentLocalDate current date (LocalDate)
     * @return List with stock units within the current month.
     */
    public List<StockUnit> filterStockByDateOneMonth(List<StockUnit> stockUnits, LocalDate currentLocalDate) {  // Test.
        List<StockUnit> filteredStockUnits = new ArrayList<>();
        for (int i = 0; i < stockUnits.size(); i++) {
            StockUnit stockUnit = stockUnits.get(i);
            String stockUnitDate = stockUnit.getDate();  // Year-Month-Day
            LocalDate stockUnitLocalDate = LocalDate.parse(stockUnitDate);
            // Filter stock units by date.
            if ((currentLocalDate.getDayOfMonth() - stockUnitLocalDate.getDayOfMonth() < 0
                    && stockUnitLocalDate.getMonthValue() == currentLocalDate.getMonthValue() - 1)  // Date is from the previous month.
                    || (stockUnitLocalDate.getDayOfMonth() <= currentLocalDate.getDayOfMonth()
                    && stockUnitLocalDate.getMonth() == currentLocalDate.getMonth())  // Current month.
                    && stockUnitLocalDate.getYear() == currentLocalDate.getYear()) {
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
