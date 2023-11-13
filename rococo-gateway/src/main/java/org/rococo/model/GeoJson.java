package org.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeoJson {
    @JsonProperty("city")
    String city;
    @JsonProperty("country")
    CountryJson country;
}
