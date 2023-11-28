package org.rococo.db.dao.impl.painting;


import org.rococo.db.ServiceDB;
import org.rococo.db.dao.painting.PaintingDAO;
import org.rococo.db.jpa.EntityManagerFactoryProvider;
import org.rococo.db.jpa.JpaService;
import org.rococo.db.model.painting.PaintingEntity;

import java.util.Optional;
import java.util.UUID;

public class PaintingDAOHibernate extends JpaService implements PaintingDAO {

    public PaintingDAOHibernate() {
        super(EntityManagerFactoryProvider.INSTANCE.getDataSource(ServiceDB.PAINTING).createEntityManager());
    }

    @Override
    public void createPainting(PaintingEntity painting) {
        persist(painting);
    }

    @Override
    public void deletePainting(PaintingEntity painting) {
        remove(painting);
    }

    @Override
    public PaintingEntity getPaintingById(UUID paintingId) {
        return em.createQuery("select u from PaintingEntity u where u.id=:id", PaintingEntity.class)
                .setParameter("id", paintingId)
                .getSingleResult();
    }

    @Override
    public PaintingEntity getPaintingByArtistId(UUID artistId) {
        Optional<PaintingEntity> paintingEntity = em.createQuery("select u from PaintingEntity u where u.artistId=:artistId", PaintingEntity.class)
                .setParameter("artistId", artistId)
                .getResultStream()
                .findFirst();
        return paintingEntity.orElseGet(PaintingEntity::new);
    }
}
