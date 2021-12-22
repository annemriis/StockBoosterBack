package com.example.back.ito03022021backend.controllers;

import com.example.back.ito03022021backend.dto.StockDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
@ActiveProfiles("test")
public class ApiControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public ApiControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    void getStockReturnsAStockDto() {
        MvcResult mvcResult = null;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders
                            .get("http://localhost:8080/api/stock/GOOG"))
                            .andExpect(status().isOk())
                            .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String contentAsString = null;
        try {
            assert mvcResult != null;
            contentAsString = mvcResult.getResponse().getContentAsString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StockDto stockDto = null;
        try {
            stockDto = objectMapper.readValue(contentAsString, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        assert stockDto != null;
        assertEquals(StockDto.class, stockDto.getClass());
        String symbol = stockDto.getSymbol();
        assertTrue(symbol == null || symbol.equals("GOOG"));
    }

    @Test
    void testDayMonthYear() {
        MvcResult mvcResult = null;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders
                            .get("http://localhost:8080/api/stock/GOOG"))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String contentAsString = null;
        try {
            assert mvcResult != null;
            contentAsString = mvcResult.getResponse().getContentAsString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StockDto stockDto = null;
        try {
            stockDto = objectMapper.readValue(contentAsString, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        assert stockDto != null;
        assertEquals(StockDto.class, stockDto.getClass());

        LocalDate localDate = LocalDate.now();
        int day = localDate.getDayOfMonth();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();

        // Test day, month and year.
        assertTrue(stockDto.getLastDate() == null
                || day == Integer.parseInt(stockDto.getLastDate().substring(8))  // Day.
                || day - 1 == Integer.parseInt(stockDto.getLastDate().substring(8))
                || day - 2 == Integer.parseInt(stockDto.getLastDate().substring(8)));
        assertTrue(stockDto.getLastDate() == null
                || month == Integer.parseInt(stockDto.getLastDate().substring(5, 7))  // Month.
                || month - 1 == Integer.parseInt(stockDto.getLastDate().substring(5, 7)));
        assertTrue(stockDto.getLastDate() == null
                || year == Integer.parseInt(stockDto.getLastDate().substring(0, 4)));  // Year.
    }

    @Test
    void getStockWithInvalidSymbolReturnsAEmptyStockDto() {
        MvcResult mvcResult = null;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders
                            .get("http://localhost:8080/api/stock/GOOGFHF"))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String contentAsString = null;
        try {
            assert mvcResult != null;
            contentAsString = mvcResult.getResponse().getContentAsString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StockDto stockDto = null;
        try {
            stockDto = objectMapper.readValue(contentAsString, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        assert stockDto != null;
        assertEquals(StockDto.class, stockDto.getClass());
        assertNull(stockDto.getSymbol());
        assertNull(stockDto.getStockDateInfo());
        assertNull(stockDto.getLastDate());
        assertNull(stockDto.getAverageVolumeMonthly());
        assertNull(stockDto.getDailyPriceChange());
    }
}
