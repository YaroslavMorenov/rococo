package org.rococo.db.dao.impl.user;


import org.rococo.db.ServiceDB;
import org.rococo.db.dao.user.AuthUserDAO;
import org.rococo.db.jpa.EntityManagerFactoryProvider;
import org.rococo.db.jpa.JpaService;
import org.rococo.db.model.user.UserEntity;

import java.util.UUID;

public class AuthUserDAOHibernate extends JpaService implements AuthUserDAO {

    public AuthUserDAOHibernate() {
        super(EntityManagerFactoryProvider.INSTANCE.getDataSource(ServiceDB.AUTH).createEntityManager());
    }

    @Override
    public void createUser(UserEntity user) {
        user.setPassword(pe.encode(user.getPassword()));
        persist(user);
    }

    @Override
    public UserEntity updateUser(UserEntity user) {
        return merge(user);
    }

    @Override
    public void deleteUser(UserEntity user) {
        remove(user);
    }

    @Override
    public UserEntity getUserById(UUID userId) {
        return em.createQuery("select u from UserEntity u where u.id=:userId", UserEntity.class)
                .setParameter("id", userId)
                .getSingleResult();
    }

    @Override
    public UserEntity getUserByName(String name) {
        return em.createQuery("select u from UserEntity u where u.username=:username", UserEntity.class)
                .setParameter("username", name)
                .getSingleResult();
    }
}
