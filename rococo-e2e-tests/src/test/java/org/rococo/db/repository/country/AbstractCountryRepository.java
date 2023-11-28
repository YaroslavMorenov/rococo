package org.rococo.db.repository.country;


import org.rococo.db.dao.country.CountryDAO;
import org.rococo.db.model.country.CountryEntity;

import java.util.List;

public abstract class AbstractCountryRepository {
    private final CountryDAO countryDAO;

    protected AbstractCountryRepository(CountryDAO countryDAO) {
        this.countryDAO = countryDAO;
    }

    public List<CountryEntity> getAllCountries() {
        return countryDAO.getAllCountries();
    }
}
