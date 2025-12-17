package com.example.footballarchive.controller;

import com.example.footballarchive.dto.CreatedArchivedMatchRequest;
import com.example.footballarchive.dto.MatchType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MatchArchiveControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void publicEndpoint_worksWithoutAuthentication() throws Exception {

        mockMvc.perform(get("/api/knowledge/public"))
                .andExpect(status().isOk());
    }

    @Test
    void securedEndpoint_returns401_whenNotAuthenticated() throws Exception {

        mockMvc.perform(get("/api/knowledge"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void securedEndpoint_works_whenAuthenticated() throws Exception {

        CreatedArchivedMatchRequest request = new CreatedArchivedMatchRequest();
        request.setType(MatchType.REPORT);
        request.setMatchTitle("Test Match");
        request.setVenue("Test Venue");
        request.setReportText("This is a valid report text with more than twenty characters.");

        mockMvc.perform(post("/api/knowledge")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}
