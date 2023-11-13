package org.rococo.data.repository;

import jakarta.annotation.Nonnull;
import org.rococo.data.MuseumEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MuseumRepository extends JpaRepository<MuseumEntity, UUID> {

  @Nonnull
  Page<MuseumEntity> findAllByTitleContainsIgnoreCase(
      @Nonnull String title,
      @Nonnull Pageable pageable
  );
}
