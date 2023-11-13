package org.rococo.service;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.rococo.data.MuseumEntity;
import org.rococo.data.repository.MuseumRepository;
import org.rococo.ex.NotFoundException;
import org.rococo.model.CountryJson;
import org.rococo.model.MuseumJson;
import org.rococo.service.api.MuseumWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MuseumService {
    private final MuseumRepository museumRepository;
    private final MuseumWebClient museumWebClient;

    @Autowired
    public MuseumService(MuseumRepository museumRepository, MuseumWebClient museumWebClient) {
        this.museumRepository = museumRepository;
        this.museumWebClient = museumWebClient;
    }

    public @Nonnull Page<MuseumJson> getAll(@Nullable String title, @Nonnull Pageable pageable) {
        Page<MuseumEntity> museums = (title == null)
                ? museumRepository.findAll(pageable)
                : museumRepository.findAllByTitleContainsIgnoreCase(title, pageable);
        return museums.map(museumEntity -> MuseumJson.fromEntity(museumEntity, getRequiredCountry(museumEntity.getCountryId())));
    }

    public @Nonnull MuseumJson findMuseumById(@Nonnull String id) {
        MuseumEntity requiredMuseum = getRequiredMuseum(UUID.fromString(id));
        return MuseumJson.fromEntity(requiredMuseum, getRequiredCountry(requiredMuseum.getCountryId()));
    }

    public @Nonnull MuseumJson update(@Nonnull MuseumJson museum) {
        MuseumEntity museumEntity = getRequiredMuseum(museum.getId());
        CountryJson requiredCountry = getRequiredCountry(museum.getGeo().getCountry().getId());
        museumEntity.setTitle(museum.getTitle());
        museumEntity.setCity(museum.getGeo().getCity());
        museumEntity.setDescription(museum.getDescription());
        museumEntity.setPhoto(museum.getPhoto().getBytes());
        museumEntity.setCountryId(requiredCountry.getId());
        return MuseumJson.fromEntity(
                museumRepository.save(museumEntity), requiredCountry);
    }

    public MuseumJson add(MuseumJson museum) {
        MuseumEntity museumEntity = museum.toEntity();
        CountryJson requiredCountry = getRequiredCountry(museum.getGeo().getCountry().getId());
        museumEntity.setCountryId(requiredCountry.getId());
        museumRepository.save(museumEntity);
        return MuseumJson.fromEntity(
                museumRepository.save(museumEntity), requiredCountry);
    }

    private @Nonnull MuseumEntity getRequiredMuseum(@Nonnull UUID id) {
        return museumRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Музей не найден по id: " + id)
        );
    }

    private @Nonnull CountryJson getRequiredCountry(@Nonnull UUID id) {
        return museumWebClient.findCountryById(String.valueOf(id));
    }
}
