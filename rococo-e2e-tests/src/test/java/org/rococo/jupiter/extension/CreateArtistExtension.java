package org.rococo.jupiter.extension;

import org.junit.jupiter.api.extension.*;
import org.rococo.api.artist.ArtistServiceClient;
import org.rococo.db.repository.artist.ArtistRepositoryHibernate;
import org.rococo.jupiter.annotation.Artist;
import org.rococo.model.ArtistJson;
import org.rococo.util.FileReaderUtils;

import java.io.File;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

import static org.rococo.jupiter.extension.DbCreateUserExtension.getAllureId;
import static org.rococo.util.FakerUtils.generateRandomName;
import static org.rococo.util.FakerUtils.generateRandomSentence;
import static org.rococo.util.FileReaderUtils.encodeFileToBase64Binary;
import static org.rococo.util.FilesPath.ARTIST_AVATAR;

public class CreateArtistExtension implements BeforeEachCallback, ParameterResolver, AfterEachCallback {

    public static ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(CreateArtistExtension.class);
    private final ArtistServiceClient artistServiceClient = new ArtistServiceClient();
    private final ArtistRepositoryHibernate artistRepository = new ArtistRepositoryHibernate();


    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        if (checkIsAnnotatedArtist(extensionContext)) {
            File avatar = new File(FileReaderUtils.getPath(ARTIST_AVATAR.getFileName()));
            ArtistJson artistJson = new ArtistJson();
            artistJson.setName(generateRandomName());
            artistJson.setBiography(generateRandomSentence(30));
            artistJson.setPhoto(encodeFileToBase64Binary(avatar));
            ArtistJson artist = artistServiceClient.createArtist(artistJson);
            extensionContext.getStore(NAMESPACE).put(getAllureId(extensionContext), artist);
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().isAnnotationPresent(Artist.class) &&
                parameterContext.getParameter().getType().isAssignableFrom(ArtistJson.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext
                .getStore(NAMESPACE)
                .get(getAllureId(extensionContext), ArtistJson.class);
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        if (checkIsAnnotatedArtist(extensionContext)) {
            ArtistJson artistJson = extensionContext.getStore(NAMESPACE).get(getAllureId(extensionContext), ArtistJson.class);
            artistRepository.removeAfterTest(artistJson.toEntity());
        }
    }

    private boolean checkIsAnnotatedArtist(ExtensionContext extensionContext) {
        List<Parameter> parameters = Arrays.stream(extensionContext.getRequiredTestMethod().getParameters()).toList();
        for (Parameter parameter : parameters) {
            if (parameter.getType().isAssignableFrom(ArtistJson.class)) {
                return parameter.isAnnotationPresent(Artist.class);
            }
        }
        return false;
    }
}
