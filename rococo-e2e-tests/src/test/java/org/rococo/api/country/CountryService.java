package org.rococo.api.country;

import org.rococo.model.CountryJson;
import org.rococo.util.RestResponsePage;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CountryService {

    @GET("/api/country")
    Call<RestResponsePage<CountryJson>> getCountries(@Query("size") String size, @Query("page") String page);
}
