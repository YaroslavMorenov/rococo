package org.rococo.db.dao.impl.museum;


import org.rococo.db.ServiceDB;
import org.rococo.db.dao.musem.MuseumDAO;
import org.rococo.db.jpa.EntityManagerFactoryProvider;
import org.rococo.db.jpa.JpaService;
import org.rococo.db.model.museum.MuseumEntity;

import java.util.UUID;

public class MuseumDAOHibernate extends JpaService implements MuseumDAO {

    public MuseumDAOHibernate() {
        super(EntityManagerFactoryProvider.INSTANCE.getDataSource(ServiceDB.MUSEUM).createEntityManager());
    }

    @Override
    public void createMuseum(MuseumEntity museum) {
        persist(museum);
    }

    @Override
    public void deleteMuseum(MuseumEntity museum) {
        remove(museum);
    }

    @Override
    public MuseumEntity getMuseumById(UUID museumId) {
        return em.createQuery("select u from MuseumEntity u where u.id=:id", MuseumEntity.class)
                .setParameter("id", museumId)
                .getSingleResult();
    }
}
