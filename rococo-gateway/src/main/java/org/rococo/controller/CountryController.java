package org.rococo.controller;

import org.rococo.model.CountryJson;
import org.rococo.service.api.RestCountryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/country")
public class CountryController {

    private final RestCountryClient geoClient;

    @Autowired
    public CountryController(RestCountryClient geoClient) {
        this.geoClient = geoClient;
    }

    @GetMapping()
    public Page<CountryJson> getAll(@RequestParam(required = false) String name,
                                    @PageableDefault Pageable pageable) {
        return geoClient.getAll(name, pageable);
    }
}
