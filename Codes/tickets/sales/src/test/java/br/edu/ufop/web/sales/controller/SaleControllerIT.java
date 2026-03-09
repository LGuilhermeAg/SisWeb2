package br.edu.ufop.web.sales.controller;

import br.edu.ufop.web.sales.enums.EnumEventType;
import br.edu.ufop.web.sales.infrastructure.entities.EventEntity;
import br.edu.ufop.web.sales.infrastructure.repositories.IEventRepository;
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
class SaleControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IEventRepository eventRepository;

    @Test
    void salesCrud_happyPath() throws Exception {
        EventEntity event = eventRepository.save(EventEntity.builder()
                .description("Event for Sale")
                .type(EnumEventType.SHOW)
                .date(LocalDateTime.now().plusDays(5))
                .startSales(LocalDateTime.now().minusDays(1))
                .endSales(LocalDateTime.now().plusDays(4))
                .price(10.0f)
                .build());

        UUID userId = UUID.randomUUID();

        String createBody = mvc.perform(post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": "%s",
                                  "eventId": "%s"
                                }
                                """.formatted(userId, event.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.userId").value(userId.toString()))
                .andExpect(jsonPath("$.event.id").value(event.getId().toString()))
                .andExpect(jsonPath("$.status").value("EM_ABERTO"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode created = objectMapper.readTree(createBody);
        UUID saleId = UUID.fromString(created.get("id").asText());

        mvc.perform(get("/sales/{id}", saleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saleId.toString()))
                .andExpect(jsonPath("$.status").value("EM_ABERTO"));

        mvc.perform(put("/sales/{id}", saleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "saleStatus": 2 }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PAGO"));

        mvc.perform(delete("/sales/{id}", saleId))
                .andExpect(status().isNoContent());

        mvc.perform(get("/sales/{id}", saleId))
                .andExpect(status().isNotFound());
    }
}

