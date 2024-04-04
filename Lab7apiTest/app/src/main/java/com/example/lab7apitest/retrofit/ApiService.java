package com.example.lab7apitest.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("Lab7.php")
    Call<Void> insertProduct(@Body Product product);
}
