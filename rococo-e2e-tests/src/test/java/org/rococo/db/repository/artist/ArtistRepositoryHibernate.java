package org.rococo.db.repository.artist;

import org.rococo.db.dao.impl.artist.ArtistDAOHibernate;

public class ArtistRepositoryHibernate extends AbstractArtistsRepository {
    public ArtistRepositoryHibernate() {
        super(new ArtistDAOHibernate());
    }
}
