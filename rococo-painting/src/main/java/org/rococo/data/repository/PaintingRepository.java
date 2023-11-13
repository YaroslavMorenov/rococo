package org.rococo.data.repository;

import jakarta.annotation.Nonnull;
import org.rococo.data.PaintingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaintingRepository extends JpaRepository<PaintingEntity, UUID> {

  @Nonnull
  Page<PaintingEntity> findAllByTitleContainsIgnoreCase(
      @Nonnull String title,
      @Nonnull Pageable pageable
  );

  @Nonnull
  Page<PaintingEntity> findAllByArtistId(
          @Nonnull UUID uuid,
          @Nonnull Pageable pageable
  );
}
