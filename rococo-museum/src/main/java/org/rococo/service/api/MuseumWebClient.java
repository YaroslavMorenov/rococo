package org.rococo.service.api;

import org.rococo.model.CountryJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class MuseumWebClient {

    private final WebClient webClient;
    private final String rococoCountryBaseUri;

    @Autowired
    public MuseumWebClient(WebClient webClient,
                             @Value("${rococo-country.base-uri}") String rococoCountryBaseUri) {
        this.webClient = webClient;
        this.rococoCountryBaseUri = rococoCountryBaseUri;
    }

    public CountryJson findCountryById(String id) {
        URI uri = UriComponentsBuilder.fromHttpUrl(rococoCountryBaseUri + "/api/country/").path(id).build().toUri();
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(CountryJson.class)
                .block();
    }
}
