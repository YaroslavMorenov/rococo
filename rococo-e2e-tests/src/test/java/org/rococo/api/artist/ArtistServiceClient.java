package org.rococo.api.artist;

import io.qameta.allure.Step;
import org.rococo.api.RestService;
import org.rococo.model.ArtistJson;

import java.io.IOException;

public class ArtistServiceClient extends RestService {

    public ArtistServiceClient() {
        super(CFG.rococoArtistUrl());
    }

    private final ArtistService artistService = retrofit.create(ArtistService.class);

    @Step("Create artist")
    public ArtistJson createArtist(ArtistJson artist) throws IOException {
        return artistService.createArtist(artist)
                .execute()
                .body();
    }
}
