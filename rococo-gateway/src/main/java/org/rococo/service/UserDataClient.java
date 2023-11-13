package org.rococo.service;

import jakarta.annotation.Nonnull;
import org.rococo.model.UserJson;

public interface UserDataClient {
    @Nonnull
    UserJson updateUserInfo(@Nonnull UserJson user);

    @Nonnull
    UserJson currentUser(@Nonnull String username);
}
