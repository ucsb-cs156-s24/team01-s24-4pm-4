package edu.ucsb.cs156.spring.backenddemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import edu.ucsb.cs156.spring.backenddemo.services.LocationQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.extern.slf4j.Slf4j;

@Tag(name = "Location info")
@Slf4j
@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationQueryService locationQueryService;

    @GetMapping("/get")
    @Operation(summary = "Get location information from OpenStreetMap",
               description = "Uses the Nominatim API documented at https://nominatim.org/release-docs/develop/api/Search/")
    public ResponseEntity<String> getLocation(
        @Parameter(description = "Location to search for, e.g., 'Santa Barbara'", example = "Santa Barbara")
        @RequestParam String location) {

        log.info("Requesting location info for: {}", location);
        try {
            String result = locationQueryService.getJSON(location);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            log.error("Error retrieving location data: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body(String.format("{\"error\":\"%s\"}", e.getMessage()));
        }
    }
}
