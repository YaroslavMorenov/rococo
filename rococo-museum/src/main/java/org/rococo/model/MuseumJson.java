package org.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.rococo.data.MuseumEntity;

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

    public static @Nullable MuseumJson fromEntity(@Nullable MuseumEntity entity, CountryJson countryJson) {
        MuseumJson museumJson = new MuseumJson();
        museumJson.setId(entity.getId());
        museumJson.setTitle(entity.getTitle());
        museumJson.setDescription(entity.getDescription());
        byte[] photo = entity.getPhoto();
        museumJson.setPhoto(photo != null && photo.length > 0 ? new String(entity.getPhoto(), StandardCharsets.UTF_8) : null);
        GeoJson geoJson = new GeoJson();
        geoJson.setCity(entity.getCity());
        geoJson.setCountry(countryJson);
        museumJson.setGeo(geoJson);
        return museumJson;
    }

    public @Nonnull MuseumEntity toEntity() {
        MuseumEntity entity = new MuseumEntity();
        entity.setTitle(title);
        entity.setDescription(description);
        entity.setPhoto(photo != null ? photo.getBytes(StandardCharsets.UTF_8) : null);
        entity.setCity(geo.city);
        entity.setCountryId(geo.country.getId());
        return entity;
    }
}
