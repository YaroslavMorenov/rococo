package org.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.rococo.db.model.artist.ArtistEntity;

import javax.annotation.Nonnull;
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

    public @Nonnull ArtistEntity toEntity() {
        ArtistEntity entity = new ArtistEntity();
        entity.setId(id);
        entity.setName(name);
        entity.setBiography(biography);
        entity.setPhoto(photo.getBytes());
        return entity;
    }
}
