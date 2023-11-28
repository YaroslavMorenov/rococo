package org.rococo.db.repository.artist;


import org.rococo.db.dao.artist.ArtistDAO;
import org.rococo.db.model.artist.ArtistEntity;

public abstract class AbstractArtistsRepository {
    private final ArtistDAO artistDAO;

    protected AbstractArtistsRepository(ArtistDAO artistDAO) {
        this.artistDAO = artistDAO;
    }

    public void createArtistForTest(ArtistEntity artist) {
        artistDAO.createArtist(artist);
    }

    public void removeAfterTest(ArtistEntity artist) {
        artistDAO.deleteArtist(getArtistById(artist));
    }

    private ArtistEntity getArtistById(ArtistEntity ar) {
        return artistDAO.getArtistById(ar.getId());
    }
}
