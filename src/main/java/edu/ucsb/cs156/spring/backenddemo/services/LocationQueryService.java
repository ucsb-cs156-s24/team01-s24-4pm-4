package edu.ucsb.cs156.spring.backenddemo.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.*;
import org.springframework.boot.web.client.RestTemplateBuilder;
import lombok.extern.slf4j.Slf4j;
import java.util.Collections;

@Slf4j
@Service
public class LocationQueryService {

    private final RestTemplate restTemplate;

    public LocationQueryService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    private static final String ENDPOINT = "https://nominatim.openstreetmap.org/search?q={location}&format=jsonv2";

    public String getJSON(String location) throws HttpClientErrorException {
        log.info("Fetching location data for: {}", location);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
            ENDPOINT,
            HttpMethod.GET,
            entity,
            String.class,
            Collections.singletonMap("location", location)
        );

        return response.getBody();
    }
}
