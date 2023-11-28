package org.rococo.jupiter.extension;

import org.junit.jupiter.api.extension.*;
import org.rococo.api.artist.ArtistServiceClient;
import org.rococo.api.country.CountryServiceClient;
import org.rococo.api.museum.MuseumServiceClient;
import org.rococo.api.painting.PaintingServiceClient;
import org.rococo.db.model.painting.PaintingEntity;
import org.rococo.db.repository.artist.ArtistRepositoryHibernate;
import org.rococo.db.repository.museum.MuseumRepositoryHibernate;
import org.rococo.db.repository.painting.PaintingRepositoryHibernate;
import org.rococo.jupiter.annotation.Painting;
import org.rococo.model.*;
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
import static org.rococo.util.FilesPath.*;

public class CreatePaintingExtension implements BeforeEachCallback, ParameterResolver, AfterEachCallback {

    public static ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(CreatePaintingExtension.class);
    private final PaintingServiceClient paintingServiceClient = new PaintingServiceClient();
    private final PaintingRepositoryHibernate paintingRepository = new PaintingRepositoryHibernate();
    private final ArtistServiceClient artistServiceClient = new ArtistServiceClient();
    private final ArtistRepositoryHibernate artistRepository = new ArtistRepositoryHibernate();
    private final CountryServiceClient countryService = new CountryServiceClient();
    private final MuseumServiceClient museumServiceClient = new MuseumServiceClient();
    private final MuseumRepositoryHibernate museum = new MuseumRepositoryHibernate();

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        Painting painting = checkIsAnnotatedPainting(extensionContext);
        PaintingJson paintingJson = new PaintingJson();
        File avatar = new File(FileReaderUtils.getPath(PAINTING_AVATAR.getFileName()));
        createArtistIfPresent(painting, paintingJson);
        createMuseumIfPresent(painting, paintingJson);
        if (painting != null && painting.isNeedToCreatePainting()) {
            paintingJson.setTitle(generateRandomTitle());
            paintingJson.setDescription(generateRandomSentence(30));
            paintingJson.setContent(encodeFileToBase64Binary(avatar));
            paintingJson = paintingServiceClient.createPainting(paintingJson);
        }
        extensionContext.getStore(NAMESPACE).put(getAllureId(extensionContext), paintingJson);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().isAnnotationPresent(Painting.class) &&
                parameterContext.getParameter().getType().isAssignableFrom(PaintingJson.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext
                .getStore(NAMESPACE)
                .get(getAllureId(extensionContext), PaintingJson.class);
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        Painting painting = checkIsAnnotatedPainting(extensionContext);
        PaintingJson paintingJson;
        if (painting != null) {
            paintingJson = extensionContext.getStore(NAMESPACE).get(getAllureId(extensionContext), PaintingJson.class);
            PaintingEntity paintingByArtistId = paintingRepository.getPaintingByArtistId(paintingJson.getArtist().getId());
            artistRepository.removeAfterTest(paintingJson.getArtist().toEntity());
            if (painting.isNeedToCreatePainting() || paintingByArtistId.getId() != null) {
                paintingRepository.removeAfterTest(paintingByArtistId);
            }
            if (painting.isNeedToCreateMuseum()) {
                museum.removeAfterTest(paintingJson.getMuseum().toEntity());
            }
        }
    }

    private void createArtistIfPresent(Painting painting, PaintingJson paintingJson) throws IOException {
        if (painting != null) {
            File avatar = new File(FileReaderUtils.getPath(ARTIST_AVATAR.getFileName()));
            ArtistJson artistJson = new ArtistJson();
            artistJson.setName(generateRandomName());
            artistJson.setBiography(generateRandomSentence(30));
            artistJson.setPhoto(encodeFileToBase64Binary(avatar));
            ArtistJson artist = artistServiceClient.createArtist(artistJson);
            paintingJson.setArtist(artist);
        }
    }

    private void createMuseumIfPresent(Painting painting, PaintingJson paintingJson) throws IOException {
        if (painting != null && painting.isNeedToCreateMuseum()) {
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
            paintingJson.setMuseum(museum);
        }
    }

    private Painting checkIsAnnotatedPainting(ExtensionContext extensionContext) {
        List<Parameter> parameters = Arrays.stream(extensionContext.getRequiredTestMethod().getParameters()).toList();
        for (Parameter parameter : parameters) {
            if (parameter.isAnnotationPresent(Painting.class)) {
                return parameter.getAnnotation(Painting.class);
            }
        }
        return null;
    }

    private UUID getRandomCountryId() throws IOException {
        List<CountryJson> countries = countryService.getCountries();
        Random rand = new Random();
        return countries.get(rand.nextInt(countries.size())).getId();
    }
}
