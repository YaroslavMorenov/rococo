package org.rococo.service.api;

import io.micrometer.common.lang.Nullable;
import org.rococo.model.PaintingJson;
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
public class RestPaintingClient {

    private final WebClient webClient;
    private final String rococoPaintingBaseUri;

    @Autowired
    public RestPaintingClient(WebClient webClient,
                              @Value("${rococo-painting.base-uri}") String rococoPaintingBaseUri) {
        this.webClient = webClient;
        this.rococoPaintingBaseUri = rococoPaintingBaseUri;
    }

    public Page<PaintingJson> getAll(@Nullable String title, Pageable pageable) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        if (title != null)
            params.add("title", title);
        params.add("size", String.valueOf(pageable.getPageSize()));
        params.add("page", String.valueOf(pageable.getPageNumber()));
        URI uri = UriComponentsBuilder.fromHttpUrl(rococoPaintingBaseUri + "/api/painting").queryParams(params).encode(UTF_8).build().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<RestResponsePage<PaintingJson>>() {
                }).block();
    }

    public PaintingJson findPaintingById(String id) {
        URI uri = UriComponentsBuilder.fromHttpUrl(rococoPaintingBaseUri + "/api/painting/").path(id).build().toUri();
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(PaintingJson.class)
                .block();
    }

    public Page<PaintingJson> findPaintingByAuthorId(String id, Pageable pageable) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("size", String.valueOf(pageable.getPageSize()));
        params.add("page", String.valueOf(pageable.getPageNumber()));
        URI uri = UriComponentsBuilder.fromHttpUrl(rococoPaintingBaseUri + "/api/painting/author/").path(id).queryParams(params).build().toUri();
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<RestResponsePage<PaintingJson>>() {
                }).block();
    }

    public PaintingJson update(PaintingJson painting) {
        return webClient.patch()
                .uri(rococoPaintingBaseUri + "/api/painting")
                .body(Mono.just(painting), PaintingJson.class)
                .retrieve()
                .bodyToMono(PaintingJson.class)
                .block();
    }

    public PaintingJson add(PaintingJson painting) {
        return webClient.post()
                .uri(rococoPaintingBaseUri + "/api/painting")
                .body(Mono.just(painting), PaintingJson.class)
                .retrieve()
                .bodyToMono(PaintingJson.class)
                .block();
    }
}
