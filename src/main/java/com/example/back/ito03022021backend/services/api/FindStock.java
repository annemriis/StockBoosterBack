package com.example.back.ito03022021backend.services.api;

import com.example.back.ito03022021backend.dto.StockDto;
import com.example.back.ito03022021backend.model.Stock;
import com.example.back.ito03022021backend.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class FindStock {

    private final StockSendingService stockSendingService;
    private final StockRepository stockRepository;

    @Autowired
    public FindStock(StockSendingService stockSendingService, StockRepository stockRepository) {
        this.stockSendingService = stockSendingService;
        this.stockRepository = stockRepository;
    }

    public Optional<StockDto> findStockWithWorsePerformance(String symbol) {
        List<Double> closeInfo = findStockCloseInformation(symbol);
        Double requestedStockLastClose;
        Double requestedStockClose;
        if (closeInfo.size() > 1) {
            requestedStockLastClose = closeInfo.get(1);
            requestedStockClose = closeInfo.get(0);
        } else {
            // If the ApiController can not find information about the given stock.
            return Optional.empty();
        }

        String worsePerformanceStock = "";
        List<Stock> stocks = stockRepository.findAll();
        for (int i = 0; i < stocks.size(); i++) {
            Stock stock = stocks.get(i);
            Double lastClose = stock.getLastClose();
            Double close = stock.getClose();
            if (isRequestedStockWithBetterPerformance(requestedStockLastClose, requestedStockClose, lastClose, close)) {
                worsePerformanceStock = stock.getSymbol();
                break;
            }
        }
        return Optional.of(getStockDto(worsePerformanceStock));
    }

    private List<Double> findStockCloseInformation(String symbol) {
        List<Double> closeInfo = new LinkedList<>();
        StockDto stockDto = getStockDto(symbol);
        List<Double> stockCloseInfo = stockDto.getStockCloseInfo();
        if (stockCloseInfo != null && stockCloseInfo.size() > 1) {
            Double lastClose = stockCloseInfo.get(0);
            Double close = stockCloseInfo.get(1);
            closeInfo.add(lastClose);
            closeInfo.add(close);
        }
        return closeInfo;
    }

    private Boolean isRequestedStockWithBetterPerformance(Double requestedStockLastClose, Double requestedStockClose,
                                                          Double lastClose, Double close) {
        return calculateClosePercentage(requestedStockLastClose, requestedStockClose)
                > calculateClosePercentage(lastClose, close);
    }

    private Double calculateClosePercentage(Double lastClose, Double close) {
        return 100.0 - (lastClose / close) * 100.0;
    }

    private StockDto getStockDto(String stockSymbol) {
        Optional<StockDto> stockDtoOptional = stockSendingService.getStockDaily(stockSymbol);
        return stockDtoOptional.orElseGet(StockDto::new);
    }
}
