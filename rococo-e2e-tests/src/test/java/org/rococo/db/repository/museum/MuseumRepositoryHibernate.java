package org.rococo.db.repository.museum;

import org.rococo.db.dao.impl.museum.MuseumDAOHibernate;

public class MuseumRepositoryHibernate extends AbstractMuseumRepository {
    public MuseumRepositoryHibernate() {
        super(new MuseumDAOHibernate());
    }
}
