package org.rococo.db.dao.musem;

import org.rococo.db.model.museum.MuseumEntity;

import java.util.UUID;

public interface MuseumDAO {

    void createMuseum(MuseumEntity museum);
    void deleteMuseum(MuseumEntity museum);
    MuseumEntity getMuseumById(UUID museumId);
}
