package com.example.bankhapoalim.ViewModel;

import android.app.Application;

import com.example.bankhapoalim.Model.Movie;
import com.example.bankhapoalim.Repository.MovieRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainViewModel extends AndroidViewModel {
    private MovieRepository movieRepository;
    private LiveData<List<Movie>> allFavoritesMovies;

    public MainViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
        allFavoritesMovies = movieRepository.getAllFavoritesMoviesFromDB();

    }

    public LiveData<List<Movie>> fetchMovies() {
        return movieRepository.getMutableMoviesLiveData();
    }

    public void insert(Movie movie) {
        movieRepository.insert(movie);
    }

    public void delete(Movie movie) {
        movieRepository.delete(movie);
    }

    public boolean find(long movieId) {
        return movieRepository.find(movieId);
    }

    public LiveData<List<Movie>> fetchFavoritesFromDB() {
        return allFavoritesMovies;
    }

}
