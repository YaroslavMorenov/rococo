package org.rococo.api.country;

import io.qameta.allure.Step;
import org.rococo.api.RestService;
import org.rococo.model.CountryJson;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class CountryServiceClient extends RestService {

    public CountryServiceClient() {
        super(CFG.rococoCountryUrl());
    }

    private final CountryService countryService = retrofit.create(CountryService.class);

    @Step("Get countries")
    public List<CountryJson> getCountries() throws IOException {
        return requireNonNull(countryService.getCountries("20", "0")
                        .execute()
                        .body())
                .getContent();
    }

    @Step("Get countries")
    public List<CountryJson> getCountries(String size) throws IOException {
        return requireNonNull(countryService.getCountries(size, "0")
                .execute()
                .body())
                .getContent();
    }
}
