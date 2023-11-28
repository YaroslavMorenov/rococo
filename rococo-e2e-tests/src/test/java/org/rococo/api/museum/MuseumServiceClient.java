package org.rococo.api.museum;

import io.qameta.allure.Step;
import org.rococo.api.RestService;
import org.rococo.model.MuseumJson;

import java.io.IOException;

public class MuseumServiceClient extends RestService {

    public MuseumServiceClient() {
        super(CFG.rococoMuseumUrl());
    }

    private final MuseumService museumService = retrofit.create(MuseumService.class);

    @Step("Create museum")
    public MuseumJson createMuseum(MuseumJson museum) throws IOException {
        return museumService.createMuseum(museum)
                .execute()
                .body();
    }
}
