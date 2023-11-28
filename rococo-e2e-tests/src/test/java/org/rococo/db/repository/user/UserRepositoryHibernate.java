package org.rococo.db.repository.user;


import org.rococo.db.dao.impl.user.AuthUserDAOHibernate;
import org.rococo.db.dao.impl.user.UserdataUserDAOHibernate;

public class UserRepositoryHibernate extends AbstractUserRepository {
    public UserRepositoryHibernate() {
        super(new AuthUserDAOHibernate(), new UserdataUserDAOHibernate());
    }
}
