package com.depromeet.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class WebConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void CORS_테스트() throws Exception {
        this.mockMvc.perform(options("/ping")
            .header("Origin", "http://localhost:3000")
            .header("Access-Control-Request-Method", "GET"))
            .andExpect(status().isOk());
    }

}
