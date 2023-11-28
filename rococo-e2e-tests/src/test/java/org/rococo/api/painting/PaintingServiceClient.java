package org.rococo.api.painting;

import io.qameta.allure.Step;
import org.rococo.api.RestService;
import org.rococo.model.PaintingJson;

import java.io.IOException;

public class PaintingServiceClient extends RestService {

    public PaintingServiceClient() {
        super(CFG.rococoPaintingUrl());
    }

    private final PaintingService paintingService = retrofit.create(PaintingService.class);

    @Step("Create painting")
    public PaintingJson createPainting(PaintingJson paintingJson) throws IOException {
        return paintingService.createPainting(paintingJson)
                .execute()
                .body();
    }
}
