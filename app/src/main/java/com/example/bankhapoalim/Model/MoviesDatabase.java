package com.example.bankhapoalim.Model;


import android.content.Context;

import com.example.bankhapoalim.R;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {Movie.class}, version = 1)
public abstract class MoviesDatabase extends RoomDatabase {

    private static MoviesDatabase instance;

    public abstract FavoritesMovieDao favoritesMovieDao();

    public static synchronized MoviesDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MoviesDatabase.class, context.getString(R.string.database_name))
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}