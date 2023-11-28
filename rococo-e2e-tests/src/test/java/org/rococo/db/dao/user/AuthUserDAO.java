package org.rococo.db.dao.user;

import org.rococo.db.model.user.UserEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

public interface AuthUserDAO {

    PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    void createUser(UserEntity user);

    UserEntity updateUser(UserEntity user);

    void deleteUser(UserEntity user);
    UserEntity getUserById(UUID userId);
    UserEntity getUserByName(String name);
}
