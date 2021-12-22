package com.example.back.ito03022021backend.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@WebAppConfiguration
@ActiveProfiles("test")
public class StockDtoBuilderTest {

    @Test
    void buildStockDtoBuildsStockDto() {
        StockDto stockDto = new StockDtoBuilder()
                .withSymbol("AAPL")
                .withStockCloseInfo(List.of(3.4, 5.3))
                .withStockDateInfo(List.of("2021-09-27", "2021-09-26", "2021-09-25"))
                .withOpen(234.5)
                .withClose(2374.4)
                .withHigh(2789.3)
                .withLastDate("2021-09-27")
                .withVolume(24456679L)
                .withAverageMonthlyPrice(1930.5)
                .withAverageMonthlyVolume(384848L)
                .withDailyPercentageChange(1.5)
                .withDailyPriceChange(100.00)
                .buildStockDto();
        assertEquals("AAPL", stockDto.getSymbol());
        assertEquals(List.of(3.4, 5.3), stockDto.getStockCloseInfo());
        assertEquals(List.of("2021-09-27", "2021-09-26", "2021-09-25"), stockDto.getStockDateInfo());
        assertEquals(234.5, stockDto.getOpen());
        assertEquals(2374.4, stockDto.getClose());
        assertEquals(2789.3, stockDto.getHigh());
        assertEquals("2021-09-27", stockDto.getLastDate());
        assertEquals(24456679L, stockDto.getVolume());
        assertEquals(1930.5, stockDto.getAveragePriceMonthly());
        assertEquals(384848L, stockDto.getAverageVolumeMonthly());
        assertEquals(1.5, stockDto.getDailyPercentageChange());
        assertEquals(100.00, stockDto.getDailyPriceChange());
    }
}
