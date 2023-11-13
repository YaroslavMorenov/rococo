package org.rococo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.Setter;
import org.rococo.data.PaintingEntity;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Getter
@Setter
public class PaintingJson {

    @JsonProperty("id")
    UUID id;
    @JsonProperty("title")
    String title;
    @JsonProperty("description")
    String description;
    @JsonProperty("content")
    String content;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("museum")
    MuseumJson museum;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("artist")
    ArtistJson artist;

    public static @Nonnull PaintingJson fromEntity(@Nonnull PaintingEntity entity, MuseumJson museumJson, ArtistJson artist) {
        PaintingJson paintingJson = new PaintingJson();
        paintingJson.setId(entity.getId());
        paintingJson.setTitle(entity.getTitle());
        paintingJson.setDescription(entity.getDescription());
        byte[] photo = entity.getContent();
        paintingJson.setContent(photo != null && photo.length > 0 ? new String(entity.getContent(), StandardCharsets.UTF_8) : null);
        paintingJson.setMuseum(museumJson);
        paintingJson.setArtist(artist);
        return paintingJson;
    }

    public @Nonnull PaintingEntity toEntity() {
        PaintingEntity entity = new PaintingEntity();
        entity.setTitle(title);
        entity.setDescription(description);
        entity.setContent(content != null ? content.getBytes(StandardCharsets.UTF_8) : null);
        return entity;
    }
}
