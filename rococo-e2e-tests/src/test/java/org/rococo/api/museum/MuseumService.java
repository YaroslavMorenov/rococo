package org.rococo.api.museum;

import org.rococo.model.MuseumJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MuseumService {

    @POST("/api/museum")
    Call<MuseumJson> createMuseum(@Body MuseumJson museumJson);
}
