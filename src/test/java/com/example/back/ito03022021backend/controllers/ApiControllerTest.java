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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
@ActiveProfiles("test")
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getStockReturnsAStockDto() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        executorService.execute(new Runnable() {
            public void run() {

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
                assertEquals("GOOG", stockDto.getSymbol());

                // Current date.
                Date date = new Date();
                String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
                int day = Integer.parseInt(modifiedDate.substring(8));
                int month = Integer.parseInt(modifiedDate.substring(5, 7));
                int year = Integer.parseInt(modifiedDate.substring(0, 4));

                // Test day, month and year.
                assertTrue(day == Integer.parseInt(stockDto.getLastDate().substring(8))  // Day.
                        || day - 1 == Integer.parseInt(stockDto.getLastDate().substring(8))
                        || day - 2 == Integer.parseInt(stockDto.getLastDate().substring(8)));
                assertTrue(month == Integer.parseInt(stockDto.getLastDate().substring(5, 7))  // Month.
                        || month - 1 == Integer.parseInt(stockDto.getLastDate().substring(5, 7)));
                assertEquals(year, Integer.parseInt(stockDto.getLastDate().substring(0, 4)));  // Year.
            }
        });

        executorService.shutdown();
    }
}
