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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

    /**
    @Test
    void apiLoginIsPublic() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/login"))
                .andExpect(status().isOk());
    }

    @Test
    void apiRegisterIsPublic() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/register"))
                .andExpect(status().isOk());
    }

    @Test
    void userEndpointRequiresUserToBeLoggedIn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/user"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test2", password = "test2", roles = "USER")
    void userEndpointRequiresUserRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/user"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test1", password = "test1", roles = "USER")
    void adminEndpointRequiresAdminRoleUserError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/admin"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void adminEndpointRequiresAdminRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/admin"))
                .andExpect(status().isOk())
                .andExpect(content().string("admin"));
    }
    **/
}
