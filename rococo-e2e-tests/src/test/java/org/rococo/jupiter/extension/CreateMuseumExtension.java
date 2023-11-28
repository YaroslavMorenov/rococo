package org.rococo.jupiter.extension;

import org.junit.jupiter.api.extension.*;
import org.rococo.api.country.CountryServiceClient;
import org.rococo.api.museum.MuseumServiceClient;
import org.rococo.db.repository.museum.MuseumRepositoryHibernate;
import org.rococo.jupiter.annotation.Museum;
import org.rococo.model.CountryJson;
import org.rococo.model.GeoJson;
import org.rococo.model.MuseumJson;
import org.rococo.util.FileReaderUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.rococo.jupiter.extension.DbCreateUserExtension.getAllureId;
import static org.rococo.util.FakerUtils.*;
import static org.rococo.util.FileReaderUtils.encodeFileToBase64Binary;
import static org.rococo.util.FilesPath.MUSEUM_AVATAR;

public class CreateMuseumExtension implements BeforeEachCallback, ParameterResolver, AfterEachCallback {

    public static ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(CreateMuseumExtension.class);
    private final MuseumServiceClient museumServiceClient = new MuseumServiceClient();
    private final CountryServiceClient countryService = new CountryServiceClient();
    private final MuseumRepositoryHibernate museum = new MuseumRepositoryHibernate();

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        if (checkIsAnnotatedMuseum(extensionContext)) {
            File avatar = new File(FileReaderUtils.getPath(MUSEUM_AVATAR.getFileName()));
            MuseumJson museumJson = new MuseumJson();
            GeoJson geoJson = new GeoJson();
            CountryJson countryJson = new CountryJson();
            museumJson.setTitle(generateRandomTitle());
            museumJson.setDescription(generateRandomSentence(30));
            museumJson.setPhoto(encodeFileToBase64Binary(avatar));
            geoJson.setCity(generateRandomCity());
            countryJson.setId(getRandomCountryId());
            geoJson.setCountry(countryJson);
            museumJson.setGeo(geoJson);
            MuseumJson museum = museumServiceClient.createMuseum(museumJson);
            extensionContext.getStore(NAMESPACE).put(getAllureId(extensionContext), museum);
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().isAnnotationPresent(Museum.class) &&
                parameterContext.getParameter().getType().isAssignableFrom(MuseumJson.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext
                .getStore(NAMESPACE)
                .get(getAllureId(extensionContext), MuseumJson.class);
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        if(checkIsAnnotatedMuseum(extensionContext)) {
            MuseumJson museumJson = extensionContext.getStore(NAMESPACE).get(getAllureId(extensionContext), MuseumJson.class);
            museum.removeAfterTest(museumJson.toEntity());
        }
    }

    private boolean checkIsAnnotatedMuseum(ExtensionContext extensionContext) {
        List<Parameter> parameters = Arrays.stream(extensionContext.getRequiredTestMethod().getParameters()).toList();
        for (Parameter parameter : parameters) {
            if (parameter.getType().isAssignableFrom(MuseumJson.class)) {
                return parameter.isAnnotationPresent(Museum.class);
            }
        }
        return false;
    }

    private UUID getRandomCountryId() throws IOException {
        List<CountryJson> countries = countryService.getCountries();
        Random rand = new Random();
        return countries.get(rand.nextInt(countries.size())).getId();
    }
}
