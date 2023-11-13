package org.rococo.service.cors;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Component
public class CorsCustomizer {

    private final String rococoFrontUri;

    @Autowired
    public CorsCustomizer(@Value("${rococo-front.base-uri}") String rococoFrontUri) {
        this.rococoFrontUri = rococoFrontUri;
    }

    public void corsCustomizer(@Nonnull HttpSecurity http) throws Exception {
        http.cors(c -> {
            CorsConfigurationSource source = s -> {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.setAllowCredentials(true);
                corsConfiguration.setAllowedOrigins(List.of(rococoFrontUri));
                corsConfiguration.setAllowedHeaders(List.of("*"));
                corsConfiguration.setAllowedMethods(List.of("*"));
                return corsConfiguration;
            };
            c.configurationSource(source);
        });
    }
}
