package org.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryJson {

    @JsonProperty("id")
    UUID id;
    @JsonProperty("name")
    String name;
}
