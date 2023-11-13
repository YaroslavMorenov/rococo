package org.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.rococo.data.ArtistEntity;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Getter
@Setter
public class ArtistJson {

    @JsonProperty("id")
    UUID id;
    @JsonProperty("name")
    String name;
    @JsonProperty("biography")
    String biography;
    @JsonProperty("photo")
    String photo;

    public static @Nullable ArtistJson fromEntity(@Nullable ArtistEntity entity) {
        ArtistJson artist = new ArtistJson();
        artist.setId(entity.getId());
        artist.setName(entity.getName());
        artist.setBiography(entity.getBiography());
        byte[] photo = entity.getPhoto();
        artist.setPhoto(photo != null && photo.length > 0 ? new String(entity.getPhoto(), StandardCharsets.UTF_8) : null);
        return artist;
    }

    public @Nonnull ArtistEntity toEntity() {
        ArtistEntity entity = new ArtistEntity();
        entity.setName(name);
        entity.setBiography(biography);
        entity.setPhoto(photo != null ? photo.getBytes(StandardCharsets.UTF_8) : null);
        return entity;
    }
}
