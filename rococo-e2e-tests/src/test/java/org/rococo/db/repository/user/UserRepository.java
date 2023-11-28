package org.rococo.db.repository.user;

import org.rococo.db.model.user.UserEntity;

public interface UserRepository {
    void createUserForTest(UserEntity user);

    void removeAfterTest(UserEntity user);
}
