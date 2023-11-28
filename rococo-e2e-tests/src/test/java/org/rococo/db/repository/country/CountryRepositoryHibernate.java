package org.rococo.db.repository.country;

import org.rococo.db.dao.impl.country.CountryDAOHibernate;

public class CountryRepositoryHibernate extends AbstractCountryRepository {
    public CountryRepositoryHibernate() {
        super(new CountryDAOHibernate());
    }
}
