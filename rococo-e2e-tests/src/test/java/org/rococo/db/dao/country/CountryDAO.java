package org.rococo.db.dao.country;

import org.rococo.db.model.country.CountryEntity;

import java.util.List;

public interface CountryDAO {

    List<CountryEntity> getAllCountries();
}
