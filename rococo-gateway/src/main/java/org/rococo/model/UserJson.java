package org.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import org.rococo.config.RococoGatewayServiceConfig;

import java.util.Objects;
import java.util.UUID;

public record UserJson(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("username")
        String username,
        @JsonProperty("firstname")
        String firstname,
        @JsonProperty("lastname")
        String lastname,
        @JsonProperty("avatar")
        @Size(max = RococoGatewayServiceConfig.THREE_MB)
        String avatar) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserJson userJson = (UserJson) o;
        return Objects.equals(id, userJson.id) && Objects.equals(username, userJson.username) && Objects.equals(firstname, userJson.firstname) && Objects.equals(lastname, userJson.lastname) && Objects.equals(avatar, userJson.avatar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, firstname, lastname, avatar);
    }
}

