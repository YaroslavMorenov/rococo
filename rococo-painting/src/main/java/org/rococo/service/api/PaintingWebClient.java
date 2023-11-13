package org.rococo.service.api;

import org.rococo.model.ArtistJson;
import org.rococo.model.MuseumJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class PaintingWebClient {

    private final WebClient webClient;
    private final String rococoMuseumBaseUri;
    private final String rococoArtistBaseUri;

    @Autowired
    public PaintingWebClient(WebClient webClient,
                             @Value("${rococo-museum.base-uri}") String rococoMuseumBaseUri,
                             @Value("${rococo-artist.base-uri}") String rococoArtistBaseUri) {
        this.webClient = webClient;
        this.rococoMuseumBaseUri = rococoMuseumBaseUri;
        this.rococoArtistBaseUri = rococoArtistBaseUri;
    }

    public MuseumJson findMuseumById(String id) {
        URI uri = UriComponentsBuilder.fromHttpUrl(rococoMuseumBaseUri + "/api/museum/").path(id).build().toUri();
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(MuseumJson.class)
                .block();
    }

    public ArtistJson findArtistById(String id) {
        URI uri = UriComponentsBuilder.fromHttpUrl(rococoArtistBaseUri + "/api/artist/").path(id).build().toUri();
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(ArtistJson.class)
                .block();
    }
}
