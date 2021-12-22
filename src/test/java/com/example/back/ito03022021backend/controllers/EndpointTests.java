package com.example.back.ito03022021backend.controllers;

import com.example.back.ito03022021backend.services.api.ApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EndpointTests {

    private final MockMvc mockMvc;

    @Autowired
    public EndpointTests(MockMvc mockMvc, ApiService apiService, Environment environment) {
        this.mockMvc = mockMvc;
        apiService.initialiseApiService(environment.getProperty("api.key"));
    }

    @Test
    void apiFindStockIsPublic() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/stock/GOOG"))
                .andExpect(status().isOk());
    }

    @Test
    void apiTimeSeriesIntradayIsPublic() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/stock/AAPL?time-series=intraday"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "USER")
    void boostMoraleEndpointRequiresUserRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/stock/TSLA/boost-morale"))
                .andExpect(status().isOk());
    }
}
