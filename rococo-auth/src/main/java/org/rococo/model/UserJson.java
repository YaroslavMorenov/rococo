package org.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class UserJson {
    @Getter
    @Setter
    @JsonProperty("username")
    private String username;

    public UserJson() {
    }
}
