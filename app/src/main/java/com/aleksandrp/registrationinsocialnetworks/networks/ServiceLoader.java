package com.aleksandrp.registrationinsocialnetworks.networks;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by AleksandrP on 11.06.2016.
 */
public interface ServiceLoader {

    @GET("{id}/picture")
    Call<Object> getUrlPhoto(
            @Path("id") String id,
            @Query("size") String size);
}
