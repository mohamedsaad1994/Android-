package com.softxpert.SoftXpertTask.data.webservice;

import com.softxpert.SoftXpertTask.data.models.Cars;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("cars")
    Call<Cars> getCars(
            @Query("page") int page);

}
