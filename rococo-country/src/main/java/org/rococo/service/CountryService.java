package org.rococo.service;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.rococo.data.CountryEntity;
import org.rococo.data.repository.CountryRepository;
import org.rococo.ex.NotFoundException;
import org.rococo.model.CountryJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CountryService {
    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }


    public @Nonnull Page<CountryJson> getAll(@Nullable String name, @Nonnull Pageable pageable) {
        Page<CountryEntity> countries = (name == null)
                ? countryRepository.findAll(pageable)
                : countryRepository.findAllByNameContainsIgnoreCase(name, pageable);
        return countries.map(CountryJson::fromEntity);
    }

    public @Nonnull CountryJson findCountryById(@Nonnull String id) {
        return CountryJson.fromEntity(
                countryRepository.findById(
                        UUID.fromString(id)
                ).orElseThrow(
                        () -> new NotFoundException("Страна не найдена по id: " + id)
                )
        );
    }
}
