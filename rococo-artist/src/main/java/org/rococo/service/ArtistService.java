package org.rococo.service;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.rococo.data.ArtistEntity;
import org.rococo.data.repository.ArtistRepository;
import org.rococo.ex.NotFoundException;
import org.rococo.model.ArtistJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class ArtistService {
    private static final Logger LOG = LoggerFactory.getLogger(ArtistService.class);
    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public @Nonnull Page<ArtistJson> getAll(@Nullable String name, @Nonnull Pageable pageable) {
        Page<ArtistEntity> artists = (name == null)
                ? artistRepository.findAll(pageable)
                : artistRepository.findAllByNameContainsIgnoreCase(name, pageable);
        return artists.map(ArtistJson::fromEntity);
    }

    public @Nonnull ArtistJson findArtistById(@Nonnull String id) {
        return ArtistJson.fromEntity(
                artistRepository.findById(
                        UUID.fromString(id)
                ).orElseThrow(
                        () -> new NotFoundException("Художник не найден по id: " + id)));
    }

    public @Nonnull ArtistJson update(@Nonnull ArtistJson artist) {
        ArtistEntity artistEntity = getRequiredArtist(artist.getId());
        artistEntity.setName(artist.getName());
        artistEntity.setBiography(artist.getBiography());
        artistEntity.setPhoto(artist.getPhoto().getBytes());
        try {
            return ArtistJson.fromEntity(artistRepository.save(artistEntity));
        } catch (DataIntegrityViolationException e) {
            LOG.error("### Error while creating artist: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Artist with name '" + artist.getName() + "' already exists", e);
        }
    }

    public ArtistJson add(ArtistJson artist) {
        ArtistEntity artistEntity = artist.toEntity();
        try {
            return ArtistJson.fromEntity(artistRepository.save(artistEntity));
        } catch (DataIntegrityViolationException e) {
            LOG.error("### Error while creating artist: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Artist with name '" + artist.getName() + "' already exists", e);
        }
    }

    private @Nonnull ArtistEntity getRequiredArtist(@Nonnull UUID id) {
        return artistRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Художник не найден по id: " + id)
        );
    }
}
