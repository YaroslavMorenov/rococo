package org.rococo.db.repository.painting;

import org.rococo.db.dao.impl.painting.PaintingDAOHibernate;

public class PaintingRepositoryHibernate extends AbstractPaintingRepository {
    public PaintingRepositoryHibernate() {
        super(new PaintingDAOHibernate());
    }
}
