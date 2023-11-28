package org.rococo.api.artist;

import org.rococo.model.ArtistJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ArtistService {

    @POST("/api/artist")
    Call<ArtistJson> createArtist(@Body ArtistJson artist);
}
