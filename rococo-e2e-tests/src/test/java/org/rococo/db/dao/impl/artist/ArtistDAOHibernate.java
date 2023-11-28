package org.rococo.db.dao.impl.artist;


import org.rococo.db.ServiceDB;
import org.rococo.db.dao.artist.ArtistDAO;
import org.rococo.db.jpa.EntityManagerFactoryProvider;
import org.rococo.db.jpa.JpaService;
import org.rococo.db.model.artist.ArtistEntity;

import java.util.UUID;

public class ArtistDAOHibernate extends JpaService implements ArtistDAO {

    public ArtistDAOHibernate() {
        super(EntityManagerFactoryProvider.INSTANCE.getDataSource(ServiceDB.ARTIST).createEntityManager());
    }

    @Override
    public void createArtist(ArtistEntity artist) {
        persist(artist);
    }

    @Override
    public void deleteArtist(ArtistEntity artist) {
        remove(artist);
    }

    @Override
    public ArtistEntity getArtistById(UUID artistId) {
        return em.createQuery("select u from ArtistEntity u where u.id=:id", ArtistEntity.class)
                .setParameter("id", artistId)
                .getSingleResult();
    }
}
