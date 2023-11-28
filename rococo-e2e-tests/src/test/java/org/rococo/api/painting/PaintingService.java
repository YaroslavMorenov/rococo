package org.rococo.api.painting;

import org.rococo.model.PaintingJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PaintingService {

    @POST("/api/painting")
    Call<PaintingJson> createPainting(@Body PaintingJson paintingJson);
}
