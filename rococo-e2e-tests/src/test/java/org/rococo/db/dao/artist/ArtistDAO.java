package org.rococo.db.dao.artist;

import org.rococo.db.model.artist.ArtistEntity;

import java.util.UUID;

public interface ArtistDAO {

    void createArtist(ArtistEntity artist);
    void deleteArtist(ArtistEntity artist);
    ArtistEntity getArtistById(UUID artistId);
}
