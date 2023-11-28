package org.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.rococo.db.model.museum.MuseumEntity;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Getter
@Setter
public class MuseumJson {

    @JsonProperty("id")
    UUID id;
    @JsonProperty("title")
    String title;
    @JsonProperty("description")
    String description;
    @JsonProperty("photo")
    String photo;
    @JsonProperty("geo")
    GeoJson geo;

    public @Nonnull MuseumEntity toEntity() {
        MuseumEntity entity = new MuseumEntity();
        entity.setId(id);
        entity.setTitle(title);
        entity.setDescription(description);
        entity.setPhoto(photo != null ? photo.getBytes(StandardCharsets.UTF_8) : null);
        entity.setCity(geo.city);
        entity.setCountryId(geo.country.getId());
        return entity;
    }
}
