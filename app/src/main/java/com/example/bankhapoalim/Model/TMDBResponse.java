package com.example.bankhapoalim.Model;

import com.google.gson.annotations.SerializedName;

public class TMDBResponse {

    @SerializedName("results")
    private Movie[] results;

    public Movie[] getMovies() {
        return results;
    }

}
