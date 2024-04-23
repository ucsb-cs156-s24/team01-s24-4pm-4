package edu.ucsb.cs156.spring.backenddemo.services;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Service
public class LocationQueryService {

    private final RestTemplate restTemplate;

    public LocationQueryService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public static final String ENDPOINT = "https://nominatim.openstreetmap.org/search?q={location}&format=jsonv2";


    public String getJSON(String location) throws HttpClientErrorException {
    UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT)
                                                            .queryParam("q", location)
                                                            .queryParam("format", "jsonv2");
    return restTemplate.getForObject(uriBuilder.toUriString(), String.class);
    }
}