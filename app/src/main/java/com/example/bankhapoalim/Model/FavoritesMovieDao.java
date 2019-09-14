package com.example.bankhapoalim.Model;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface FavoritesMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie movie);

    @Delete
    void delete(Movie movie);

    @Query("SELECT * FROM favorites_movie_table ORDER BY title DESC")
    LiveData<List<Movie>> getAllFavMovies();

    @Query("SELECT * FROM favorites_movie_table WHERE id == :id LIMIT 1")
    boolean isRecordExist(long id);


}
