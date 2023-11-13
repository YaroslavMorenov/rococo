package org.rococo.service.api;

import io.micrometer.common.lang.Nullable;
import org.rococo.model.CountryJson;
import org.rococo.service.RestResponsePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class RestCountryClient {

    private final WebClient webClient;
    private final String rococoCountryBaseUri;

    @Autowired
    public RestCountryClient(WebClient webClient,
                             @Value("${rococo-country.base-uri}") String rococoCountryBaseUri) {
        this.webClient = webClient;
        this.rococoCountryBaseUri = rococoCountryBaseUri;
    }

    public Page<CountryJson> getAll(@Nullable String name, Pageable pageable) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("size", String.valueOf(pageable.getPageSize()));
        params.add("page", String.valueOf(pageable.getPageNumber()));
        URI uri = UriComponentsBuilder.fromHttpUrl(rococoCountryBaseUri + "/api/country").queryParams(params).build().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<RestResponsePage<CountryJson>>() {
                }).block();
    }
}
