package edu.ucsb.cs156.spring.backenddemo.services;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.web.util.UriComponentsBuilder;

@RestClientTest(LocationQueryService.class)
public class LocationQueryServiceTests {

    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Autowired
    private LocationQueryService locationQueryService;

    @Test
    public void testGetJSON() {
        String location = "Santa Barbara";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://nominatim.openstreetmap.org/search")
                .queryParam("q", location)
                .queryParam("format", "jsonv2");
        String expectedURL = builder.toUriString();  // This should avoid double encoding

        String fakeJsonResult = "[{\"place_id\":\"123\", \"licence\":\"Data © OpenStreetMap contributors\", \"osm_type\":\"relation\", \"osm_id\":\"165473\", \"lat\":\"34.4208305\", \"lon\":\"-119.6981901\"}]";

        this.mockRestServiceServer.expect(requestTo(expectedURL))
            .andExpect(header("Accept", MediaType.APPLICATION_JSON_VALUE))
            .andExpect(header("Content-Type", MediaType.APPLICATION_JSON.toString()))
            .andRespond(withSuccess(fakeJsonResult, MediaType.APPLICATION_JSON));

        String actualResult = locationQueryService.getJSON(location);
        assertEquals(fakeJsonResult, actualResult);
    }
}
