package com.example.bankhapoalim.Repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.bankhapoalim.Model.FavoritesMovieDao;
import com.example.bankhapoalim.Model.Movie;
import com.example.bankhapoalim.Model.MoviesDatabase;
import com.example.bankhapoalim.Model.TMDBResponse;
import com.example.bankhapoalim.R;
import com.example.bankhapoalim.Model.RestApiService;
import com.example.bankhapoalim.Model.RetrofitInstance;
import com.example.bankhapoalim.Utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieRepository {
    private MutableLiveData<List<Movie>> mutableMoviesLiveData = new MutableLiveData<>();
    private LiveData<List<Movie>> allFavoritesMovies;
    private Application application;
    private FavoritesMovieDao favoritesMovieDao;
    private String API_KEY = "api_key";
    private String START_DATA = "primary_release_date.gte";
    private String END_DATE = "primary_release_date.lte";
    private String SORT_BY = "sort_by";

    public MovieRepository(Application application) {
        this.application = application;
        MoviesDatabase moviesDatabase = MoviesDatabase.getInstance(application);
        favoritesMovieDao = moviesDatabase.favoritesMovieDao();
        allFavoritesMovies = favoritesMovieDao.getAllFavMovies();
    }

    public MutableLiveData<List<Movie>> getMutableMoviesLiveData() {

        RestApiService apiService = RetrofitInstance.getApiService();

        Map<String, String> data = new HashMap<>();
        data.put(START_DATA, Utils.getOneMonthAgoInTMDBFormat());
        data.put(END_DATE, Utils.getCurrentTimeInTMDBFormat());
        data.put(API_KEY, application.getString(R.string.api_key));
        data.put(SORT_BY, "popularity.desc");
        Call<TMDBResponse> call = apiService.getMovies(data);

        call.enqueue(new Callback<TMDBResponse>() {
            @Override
            public void onResponse(Call<TMDBResponse> call, Response<TMDBResponse> response) {
                TMDBResponse jsonResponse = response.body();
                List<Movie> data = new ArrayList<>(Arrays.asList(jsonResponse.getMovies()));
                mutableMoviesLiveData.setValue(data);
            }

            @Override
            public void onFailure(Call<TMDBResponse> call, Throwable t) {

            }
        });
        return mutableMoviesLiveData;
    }

    public void insert(Movie movie) {
        new InsertFavoriteAsyncTask(favoritesMovieDao).execute(movie);
    }

    public void delete(Movie movie) {
        new DeleteFavoriteAsyncTask(favoritesMovieDao).execute(movie);
    }

    public boolean find(long movieId) {
        try {
            return new FindFavoriteAsyncTask(favoritesMovieDao).execute(movieId).get();
        } catch (ExecutionException e) {
        } catch (InterruptedException e) {
        }
        return false;
    }

    public LiveData<List<Movie>> getAllFavoritesMoviesFromDB() {
        return allFavoritesMovies;
    }

    private static class InsertFavoriteAsyncTask extends AsyncTask<Movie, Void, Void> {
        private FavoritesMovieDao favoritesMovieDao;

        private InsertFavoriteAsyncTask(FavoritesMovieDao favoritesMovieDao) {
            this.favoritesMovieDao = favoritesMovieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            favoritesMovieDao.insert(movies[0]);
            return null;
        }
    }

    private static class DeleteFavoriteAsyncTask extends AsyncTask<Movie, Void, Void> {
        private FavoritesMovieDao favoritesMovieDao;

        private DeleteFavoriteAsyncTask(FavoritesMovieDao favoritesMovieDao) {
            this.favoritesMovieDao = favoritesMovieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            favoritesMovieDao.delete(movies[0]);
            return null;
        }
    }

    private static class FindFavoriteAsyncTask extends AsyncTask<Long, Void, Boolean> {
        private FavoritesMovieDao favoritesMovieDao;

        private FindFavoriteAsyncTask(FavoritesMovieDao favoritesMovieDao) {
            this.favoritesMovieDao = favoritesMovieDao;
        }

        @Override
        protected Boolean doInBackground(Long... longs) {

            return favoritesMovieDao.isRecordExist(longs[0]);
        }
    }
}
