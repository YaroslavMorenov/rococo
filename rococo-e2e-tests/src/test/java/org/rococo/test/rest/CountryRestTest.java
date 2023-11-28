package org.rococo.test.rest;

import io.qameta.allure.AllureId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.rococo.api.country.CountryServiceClient;
import org.rococo.db.model.country.CountryEntity;
import org.rococo.db.repository.country.CountryRepositoryHibernate;
import org.rococo.model.CountryJson;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static io.qameta.allure.Allure.step;

@DisplayName("[REST]: Страны")
public class CountryRestTest extends BaseRestTest {

    private final CountryServiceClient countryService = new CountryServiceClient();
    private final CountryRepositoryHibernate country = new CountryRepositoryHibernate();

    @AllureId("10001")
    @DisplayName("REST: При запросе countries возвращаются все страны из rococo-country")
    @Test
    void apiShouldReturnAllCountries() throws IOException {
        List<CountryJson> apiCountriesResponse = countryService.getCountries("300");
        List<CountryEntity> dbCountries = country.getAllCountries();

        step("Проверить, что ответ /countries содержит все страны из БД", () -> Assertions
                .assertThat(apiCountriesResponse)
                .containsExactlyInAnyOrderElementsOf(dbCountries.stream()
                        .map(c -> new CountryJson(c.getId(), c.getName())).collect(Collectors.toList())));
    }
}
