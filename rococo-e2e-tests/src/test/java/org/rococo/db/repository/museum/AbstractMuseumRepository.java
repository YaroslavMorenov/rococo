package org.rococo.db.repository.museum;


import org.rococo.db.dao.musem.MuseumDAO;
import org.rococo.db.model.museum.MuseumEntity;

public abstract class AbstractMuseumRepository {
    private final MuseumDAO museumDAO;

    protected AbstractMuseumRepository(MuseumDAO museumDAO) {
        this.museumDAO = museumDAO;
    }

    public void createMuseumForTest(MuseumEntity museum) {
        museumDAO.createMuseum(museum);
    }

    public void removeAfterTest(MuseumEntity museum) {
        museumDAO.deleteMuseum(getArtistById(museum));
    }

    private MuseumEntity getArtistById(MuseumEntity museum) {
        return museumDAO.getMuseumById(museum.getId());
    }
}
