package org.rococo.data.repository;

import jakarta.annotation.Nonnull;
import org.rococo.data.CountryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CountryRepository extends JpaRepository<CountryEntity, UUID> {

  @Nonnull
  Page<CountryEntity> findAllByNameContainsIgnoreCase(
      @Nonnull String name,
      @Nonnull Pageable pageable
  );

  @Nonnull
  Optional<CountryEntity> findByName(@Nonnull String name);
}
