package org.rococo.db.dao.user;


import org.rococo.db.model.user.UserDataUserEntity;

public interface UserdataUserDAO {

    int createUserInUserData(UserDataUserEntity user);

    void deleteUserInUserData(UserDataUserEntity userId);

    UserDataUserEntity getUserInUserDataByUsername(String username);
}
