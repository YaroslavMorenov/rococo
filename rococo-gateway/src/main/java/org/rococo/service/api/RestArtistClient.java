package org.rococo.service.api;

import io.micrometer.common.lang.Nullable;
import org.rococo.model.ArtistJson;
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
public class RestArtistClient {

    private final WebClient webClient;
    private final String rococoArtistBaseUri;

    @Autowired
    public RestArtistClient(WebClient webClient,
                            @Value("${rococo-artist.base-uri}") String rococoArtistBaseUri) {
        this.webClient = webClient;
        this.rococoArtistBaseUri = rococoArtistBaseUri;
    }

    public Page<ArtistJson> getAll(@Nullable String name, Pageable pageable) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        if (name != null)
            params.add("name", name);
        params.add("size", String.valueOf(pageable.getPageSize()));
        params.add("page", String.valueOf(pageable.getPageNumber()));
        URI uri = UriComponentsBuilder.fromHttpUrl(rococoArtistBaseUri + "/api/artist").queryParams(params).encode(UTF_8).build().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<RestResponsePage<ArtistJson>>() {
                }).block();
    }

    public ArtistJson findArtistById(String id) {
        URI uri = UriComponentsBuilder.fromHttpUrl(rococoArtistBaseUri + "/api/artist/").path(id).build().toUri();
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(ArtistJson.class)
                .block();
    }

    public ArtistJson update(ArtistJson artist) {
        return webClient.patch()
                .uri(rococoArtistBaseUri + "/api/artist")
                .body(Mono.just(artist), ArtistJson.class)
                .retrieve()
                .bodyToMono(ArtistJson.class)
                .block();
    }

    public ArtistJson add(ArtistJson artist) {
        return webClient.post()
                .uri(rococoArtistBaseUri + "/api/artist")
                .body(Mono.just(artist), ArtistJson.class)
                .retrieve()
                .bodyToMono(ArtistJson.class)
                .block();
    }
}
