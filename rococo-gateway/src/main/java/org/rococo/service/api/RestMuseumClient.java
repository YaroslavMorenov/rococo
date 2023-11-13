package org.rococo.service.api;

import io.micrometer.common.lang.Nullable;
import org.rococo.model.MuseumJson;
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
import reactor.core.publisher.Mono;

import java.net.URI;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class RestMuseumClient {

    private final WebClient webClient;
    private final String rococoMuseumBaseUri;

    @Autowired
    public RestMuseumClient(WebClient webClient,
                            @Value("${rococo-museum.base-uri}") String rococoMuseumBaseUri) {
        this.webClient = webClient;
        this.rococoMuseumBaseUri = rococoMuseumBaseUri;
    }

    public Page<MuseumJson> getAll(@Nullable String title, Pageable pageable) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        if (title != null)
            params.add("title", title);
        params.add("size", String.valueOf(pageable.getPageSize()));
        params.add("page", String.valueOf(pageable.getPageNumber()));
        URI uri = UriComponentsBuilder.fromHttpUrl(rococoMuseumBaseUri + "/api/museum").queryParams(params).encode(UTF_8).build().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<RestResponsePage<MuseumJson>>() {
                }).block();
    }

    public MuseumJson findMuseumById(String id) {
        URI uri = UriComponentsBuilder.fromHttpUrl(rococoMuseumBaseUri + "/api/museum/").path(id).build().toUri();
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(MuseumJson.class)
                .block();
    }

    public MuseumJson update(MuseumJson museum) {
        return webClient.patch()
                .uri(rococoMuseumBaseUri + "/api/museum")
                .body(Mono.just(museum), MuseumJson.class)
                .retrieve()
                .bodyToMono(MuseumJson.class)
                .block();
    }

    public MuseumJson add(MuseumJson museum) {
        return webClient.post()
                .uri(rococoMuseumBaseUri + "/api/museum")
                .body(Mono.just(museum), MuseumJson.class)
                .retrieve()
                .bodyToMono(MuseumJson.class)
                .block();
    }
}
