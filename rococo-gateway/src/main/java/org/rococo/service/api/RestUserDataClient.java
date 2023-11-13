package org.rococo.service.api;

import jakarta.annotation.Nonnull;
import org.rococo.model.UserJson;
import org.rococo.service.UserDataClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class RestUserDataClient implements UserDataClient {

    private final WebClient webClient;
    private final String rococoUserdataBaseUri;

    @Autowired
    public RestUserDataClient(WebClient webClient,
                              @Value("${rococo-userdata.base-uri}") String rococoUserdataBaseUri) {
        this.webClient = webClient;
        this.rococoUserdataBaseUri = rococoUserdataBaseUri;
    }

    @Override
    public @Nonnull
    UserJson currentUser(@Nonnull String username) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        URI uri = UriComponentsBuilder.fromHttpUrl(rococoUserdataBaseUri + "/api/user").queryParams(params).build().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(UserJson.class)
                .block();
    }

    @Override
    public @Nonnull
    UserJson updateUserInfo(@Nonnull UserJson user) {
        return webClient.patch()
                .uri(rococoUserdataBaseUri + "/api/user")
                .body(Mono.just(user), UserJson.class)
                .retrieve()
                .bodyToMono(UserJson.class)
                .block();
    }
}
