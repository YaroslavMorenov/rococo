package org.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CountryJson {

    @JsonProperty("id")
    UUID id;
    @JsonProperty("name")
    String name;
}
