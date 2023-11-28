package org.rococo.data.repository;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.rococo.data.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Nullable
    UserEntity findByUsername(@Nonnull String username);
}
