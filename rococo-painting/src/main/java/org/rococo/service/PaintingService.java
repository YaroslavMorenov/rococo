package org.rococo.service;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.rococo.data.PaintingEntity;
import org.rococo.data.repository.PaintingRepository;
import org.rococo.ex.NotFoundException;
import org.rococo.model.ArtistJson;
import org.rococo.model.MuseumJson;
import org.rococo.model.PaintingJson;
import org.rococo.service.api.PaintingWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaintingService {
    private final PaintingRepository paintingRepository;
    private final PaintingWebClient paintingWebClient;

    @Autowired
    public PaintingService(PaintingRepository paintingRepository, PaintingWebClient paintingWebClient) {
        this.paintingRepository = paintingRepository;
        this.paintingWebClient = paintingWebClient;
    }

    public @Nonnull Page<PaintingJson> getAll(@Nullable String title, @Nonnull Pageable pageable) {
        Page<PaintingEntity> paintings = (title == null)
                ? paintingRepository.findAll(pageable)
                : paintingRepository.findAllByTitleContainsIgnoreCase(title, pageable);
        return paintings.map(paintingEntity -> PaintingJson.fromEntity(paintingEntity,
                getRequiredMuseum(paintingEntity.getMuseumId()),
                getRequiredArtist(paintingEntity.getArtistId())));
    }

    public @Nonnull PaintingJson findPaintingById(@Nonnull String id) {
        PaintingEntity paintingEntity = paintingRepository.findById(
                UUID.fromString(id)).orElseThrow(
                () -> new NotFoundException("Картина не найдена по id: " + id));
        return PaintingJson.fromEntity(paintingEntity, getRequiredMuseum(paintingEntity.getMuseumId()), getRequiredArtist(paintingEntity.getArtistId()));
    }

    public Page<PaintingJson> findPaintingByAuthorId(@Nonnull String id, Pageable pageable) {
        ArtistJson requiredArtist = getRequiredArtist(UUID.fromString(id));
        if (requiredArtist.getId() == null) {
            throw new NotFoundException("Художник не найден по id: " + id);
        }
        Page<PaintingEntity> allByArtistId = paintingRepository.findAllByArtistId(UUID.fromString(id), pageable);
        return allByArtistId.map(paintingEntity -> PaintingJson.fromEntity(paintingEntity,
                getRequiredMuseum(paintingEntity.getMuseumId()),
                getRequiredArtist(paintingEntity.getArtistId())));
    }

    public @Nonnull PaintingJson update(@Nonnull PaintingJson painting) {
        PaintingEntity paintingEntity = getRequiredPainting(painting.getId());
        paintingEntity.setTitle(painting.getTitle());
        paintingEntity.setDescription(painting.getDescription());
        paintingEntity.setContent(painting.getContent().getBytes());
        MuseumJson museumJson = new MuseumJson();
        ArtistJson artistJson = new ArtistJson();
        if (painting.getMuseum() != null) {
            final UUID museumIdFromJson = painting.getMuseum().getId();
            paintingEntity.setMuseumId(museumIdFromJson);
            museumJson = getRequiredMuseum(museumIdFromJson);
        }
        if (painting.getArtist() != null) {
            final UUID artistIdFromJson = painting.getArtist().getId();
            paintingEntity.setArtistId(artistIdFromJson);
            artistJson = getRequiredArtist(artistIdFromJson);
        }
        return PaintingJson.fromEntity(
                paintingRepository.save(paintingEntity), museumJson, artistJson);
    }

    public PaintingJson add(PaintingJson painting) {
        PaintingEntity paintingEntity = painting.toEntity();
        ArtistJson requiredArtist = getRequiredArtist(painting.getArtist().getId());
        paintingEntity.setArtistId(requiredArtist.getId());
        MuseumJson museumJson = new MuseumJson();
        if (painting.getMuseum() != null && painting.getMuseum().getId() != null) {
            museumJson = getRequiredMuseum(painting.getMuseum().getId());
            paintingEntity.setMuseumId(museumJson.getId());
        }
        return PaintingJson.fromEntity(
                paintingRepository.save(paintingEntity), museumJson, requiredArtist);
    }

    private @Nonnull PaintingEntity getRequiredPainting(@Nonnull UUID id) {
        return paintingRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Картина не найдена по id: " + id)
        );
    }

    private @Nonnull MuseumJson getRequiredMuseum(@Nonnull UUID id) {
        if (id == null) {
            return new MuseumJson();
        }
        return paintingWebClient.findMuseumById(String.valueOf(id));
    }

    private @Nonnull ArtistJson getRequiredArtist(@Nonnull UUID id) {
        return paintingWebClient.findArtistById(String.valueOf(id));
    }
}
