package org.rococo.db.dao.impl.user;


import org.rococo.db.ServiceDB;
import org.rococo.db.dao.user.UserdataUserDAO;
import org.rococo.db.jpa.EntityManagerFactoryProvider;
import org.rococo.db.jpa.JpaService;
import org.rococo.db.model.user.UserDataUserEntity;

public class UserdataUserDAOHibernate extends JpaService implements UserdataUserDAO {

    public UserdataUserDAOHibernate() {
        super(EntityManagerFactoryProvider.INSTANCE.getDataSource(ServiceDB.USERDATA).createEntityManager());
    }

    @Override
    public int createUserInUserData(UserDataUserEntity user) {
        persist(user);
        return 0;
    }

    @Override
    public void deleteUserInUserData(UserDataUserEntity user) {
        remove(user);
    }

    @Override
    public UserDataUserEntity getUserInUserDataByUsername(String username) {
        return em.createQuery("select u from UserDataUserEntity u where u.username=:username", UserDataUserEntity.class)
                .setParameter("username", username)
                .getSingleResult();
    }
}
