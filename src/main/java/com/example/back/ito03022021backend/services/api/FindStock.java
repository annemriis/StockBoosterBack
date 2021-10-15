package com.example.back.ito03022021backend.services.api;

import com.example.back.ito03022021backend.dto.StockDto;
import com.example.back.ito03022021backend.model.Stock;
import com.example.back.ito03022021backend.model.StockRepository;
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

// Jätsin selle igaks juhuks hetkel alles, et kui tahame kunagi viite aktsiat leida.
/**
 * public class FindStock {
 *
 *     private final ApiService apiService = new ApiService();
 *     private final StockService stockService = new StockService(apiService);
 *     private final StockCalculations stockCalculations = new StockCalculations();
 *     private final StockSendingService stockSendingService = new StockSendingService(stockService, stockCalculations);
 *     private final ApiController apiController = new ApiController(apiService, stockService, stockSendingService);
 *
 *     public List<StockDto> findStocksWithWorsePerformance(String symbol, StockRepository stockRepository) {
 *         List<Double> closeInfo = findStockCloseInformation(symbol);
 *         Double requestedStockLastClose;
 *         Double requestedStockClose;
 *         if (closeInfo.size() > 1) {
 *             requestedStockLastClose = closeInfo.get(0);
 *             requestedStockClose = closeInfo.get(1);
 *         } else {
 *             // If the ApiController can not find information about the given stock.
 *             return List.of();
 *         }
 *
 *         List<String> stockSymbols = new ArrayList<>();
 *         List<Stock> stocks = stockRepository.findAll();
 *         for (int i = 0; i < stocks.size(); i++) {
 *             Stock stock = stocks.get(i);
 *             Double lastClose = stock.getLastClose();
 *             Double close = stock.getClose();
 *             if (isRequestedStockWithBetterPerformance(requestedStockLastClose, requestedStockClose, lastClose, close)) {
 *                 stockSymbols.add(stock.getSymbol());
 *                 if (stockSymbols.size() == 5) {
 *                     break;
 *                 }
 *             }
 *         }
 *         return findStockDtoList(stockSymbols);
 *     }
 *
 *     private List<Double> findStockCloseInformation(String symbol) {
 *         List<Double> closeInfo = new LinkedList<>();
 *         StockDto stockDto = apiController.getStock(symbol);
 *         List<Double> stockCloseInfo = stockDto.getStockCloseInfo();
 *         if (stockCloseInfo != null && stockCloseInfo.size() > 1) {
 *             Double lastClose = stockCloseInfo.get(0);
 *             Double close = stockCloseInfo.get(1);
 *             closeInfo.add(lastClose);
 *             closeInfo.add(close);
 *         }
 *         return closeInfo;
 *     }
 *
 *     private Boolean isRequestedStockWithBetterPerformance(Double requestedStockLastClose, Double requestedStockClose,
 *                                                           Double lastClose, Double close) {
 *         return calculateClosePercentage(requestedStockLastClose, requestedStockClose)
 *                 > calculateClosePercentage(lastClose, close);
 *     }
 *
 *     private Double calculateClosePercentage(Double lastClose, Double close) {
 *         return 100 - (close / lastClose) * 100;
 *     }
 *
 *     private List<StockDto> findStockDtoList(List<String> stockSymbols) {
 *         List<StockDto> stockDtoList = new ArrayList<>();
 *         for (int i = 0; i < stockSymbols.size(); i++) {
 *             String symbol = stockSymbols.get(i);
 *             StockDto stockDto = apiController.getStock(symbol);
 *             stockDtoList.add(stockDto);
 *         }
 *         return stockDtoList;
 *     }
 * }
 */
