package br.edu.ufop.web.sales.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EventControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void eventsCrud_happyPath() throws Exception {
        String createPayload = """
                {
                  "description": "Show Test",
                  "type": 2,
                  "date": "%s",
                  "startSales": "%s",
                  "endSales": "%s",
                  "price": 99.9
                }
                """.formatted(
                LocalDateTime.now().plusDays(10),
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(9)
        );

        String createBody = mvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createPayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.description").value("Show Test"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode created = objectMapper.readTree(createBody);
        UUID id = UUID.fromString(created.get("id").asText());

        mvc.perform(get("/events/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.description").value("Show Test"));

        mvc.perform(put("/events/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "description": "Show Updated" }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Show Updated"));

        mvc.perform(delete("/events/{id}", id))
                .andExpect(status().isNoContent());

        mvc.perform(get("/events/{id}", id))
                .andExpect(status().isNotFound());
    }
}

