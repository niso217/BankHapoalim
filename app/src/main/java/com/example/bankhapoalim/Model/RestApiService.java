package com.example.bankhapoalim.Model;

import com.example.bankhapoalim.Model.TMDBResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface RestApiService {

    @GET("discover/movie")
    Call<TMDBResponse> getMovies(@QueryMap Map<String, String> params);

}
