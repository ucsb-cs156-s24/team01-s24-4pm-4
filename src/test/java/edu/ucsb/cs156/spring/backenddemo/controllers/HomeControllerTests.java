package edu.ucsb.cs156.spring.backenddemo.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void test_getHome() throws Exception {
        String expectedBody = HomeController.getHomePageObjectJSON("http://localhost/");
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().string(equalTo(expectedBody)));
    }

    @Test
    public void test_getHomePageObjectJSON() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("greeting", "Greetings from Spring Boot!");

        String baseUrl = "http://localhost:8080/";

        List<String> team = new ArrayList<String>();
        team.add("Andrew C.");
        team.add("Emre C.");
        team.add("Ridhit G.");
        team.add("Anderson L.");
        team.add("Jaren L.");
        team.add("Winbert Z.");
        resultMap.put("team", team);
        resultMap.put("repo", "https://github.com/ucsb-cs156-s24/team01-s24-4pm-4");
        resultMap.put("api-documentation", baseUrl + "swagger-ui/index.html");
        String expected = mapper.writeValueAsString(resultMap);
        String actual = HomeController.getHomePageObjectJSON("http://localhost:8080/");

        assertEquals(expected, actual);
    }

}
