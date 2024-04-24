package edu.ucsb.cs156.spring.backenddemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import edu.ucsb.cs156.spring.backenddemo.services.LocationQueryService;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(LocationController.class)
public class LocationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocationQueryService locationQueryService;

    @org.junit.jupiter.api.Test
    public void getLocation_returnsLocationInfo() throws Exception {
        String expectedJson = "{\"place_id\":\"123\"}";
        String location = "Santa Barbara";

        when(locationQueryService.getJSON(any(String.class))).thenReturn(expectedJson);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/locations/get")
                .param("location", location)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedJson));
    }

    @org.junit.jupiter.api.Test
    public void getLocation_whenServiceThrows_returnsServerError() throws Exception {
        String location = "Santa Barbara";
        when(locationQueryService.getJSON(any(String.class))).thenThrow(new RuntimeException("Service failure"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/locations/get")
                .param("location", location)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().string("{\"error\":\"Service failure\"}"));
    }
}
