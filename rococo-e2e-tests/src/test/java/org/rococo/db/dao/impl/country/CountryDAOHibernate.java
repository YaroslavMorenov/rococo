package org.rococo.db.dao.impl.country;


import org.rococo.db.ServiceDB;
import org.rococo.db.dao.country.CountryDAO;
import org.rococo.db.jpa.EntityManagerFactoryProvider;
import org.rococo.db.jpa.JpaService;
import org.rococo.db.model.country.CountryEntity;

import java.util.List;

public class CountryDAOHibernate extends JpaService implements CountryDAO {

    public CountryDAOHibernate() {
        super(EntityManagerFactoryProvider.INSTANCE.getDataSource(ServiceDB.COUNTRY).createEntityManager());
    }

    @Override
    public List<CountryEntity> getAllCountries() {
        return em.createQuery("select u from CountryEntity u", CountryEntity.class)
                .getResultList();
    }
}
