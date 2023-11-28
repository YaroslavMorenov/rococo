package org.rococo.db.dao.painting;

import org.rococo.db.model.painting.PaintingEntity;

import java.util.UUID;

public interface PaintingDAO {

    void createPainting(PaintingEntity painting);
    void deletePainting(PaintingEntity painting);
    PaintingEntity getPaintingById(UUID paintingId);
    PaintingEntity getPaintingByArtistId(UUID artistId);
}
