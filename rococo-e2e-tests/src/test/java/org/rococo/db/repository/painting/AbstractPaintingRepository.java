package org.rococo.db.repository.painting;


import org.rococo.db.dao.painting.PaintingDAO;
import org.rococo.db.model.painting.PaintingEntity;

import java.util.UUID;

public abstract class AbstractPaintingRepository {
    private final PaintingDAO paintingDAO;

    protected AbstractPaintingRepository(PaintingDAO paintingDAO) {
        this.paintingDAO = paintingDAO;
    }

    public void createPaintingForTest(PaintingEntity painting) {
        paintingDAO.createPainting(painting);
    }

    public void removeAfterTest(PaintingEntity painting) {
        paintingDAO.deletePainting(getPaintingById(painting));
    }

    public PaintingEntity getPaintingByArtistId(UUID artistId) {
        return paintingDAO.getPaintingByArtistId(artistId);
    }

    private PaintingEntity getPaintingById(PaintingEntity painting) {
        return paintingDAO.getPaintingById(painting.getId());
    }
}
