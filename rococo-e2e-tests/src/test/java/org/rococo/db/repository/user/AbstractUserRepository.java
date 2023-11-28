package org.rococo.db.repository.user;


import org.rococo.db.dao.user.AuthUserDAO;
import org.rococo.db.dao.user.UserdataUserDAO;
import org.rococo.db.model.user.UserDataUserEntity;
import org.rococo.db.model.user.UserEntity;

public abstract class AbstractUserRepository implements UserRepository {
    private final AuthUserDAO authUserDAO;
    private final UserdataUserDAO udUserDAO;

    protected AbstractUserRepository(AuthUserDAO authUserDAO, UserdataUserDAO udUserDAO) {
        this.authUserDAO = authUserDAO;
        this.udUserDAO = udUserDAO;
    }

    @Override
    public void createUserForTest(UserEntity user) {
        authUserDAO.createUser(user);
        udUserDAO.createUserInUserData(fromAuthUser(user));
    }

    @Override
    public void removeAfterTest(UserEntity user) {
        UserDataUserEntity userInUd = udUserDAO.getUserInUserDataByUsername(user.getUsername());
        user = authUserDAO.getUserByName(user.getUsername());
        udUserDAO.deleteUserInUserData(userInUd);
        authUserDAO.deleteUser(user);
    }

    private UserDataUserEntity fromAuthUser(UserEntity user) {
        UserDataUserEntity userdataUser = new UserDataUserEntity();
        userdataUser.setUsername(user.getUsername());
        return userdataUser;
    }
}
