package org.rococo.controller;

import org.rococo.model.CountryJson;
import org.rococo.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/country")
public class CountryController {

  private final CountryService countryService;

  @Autowired
  public CountryController(CountryService countryService) {
    this.countryService = countryService;
  }

  @GetMapping()
  public Page<CountryJson> getAll(@RequestParam(required = false) String title,
                                  @PageableDefault Pageable pageable) {
    return countryService.getAll(title, pageable);
  }

  @GetMapping("/{id}")
  public CountryJson findCountryById(@PathVariable("id") String id) {
    return countryService.findCountryById(id);
  }
}
