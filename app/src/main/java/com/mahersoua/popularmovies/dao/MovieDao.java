package com.mahersoua.popularmovies.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mahersoua.popularmovies.models.MovieModel;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieModel word);

    @Query("DELETE FROM movie_table")
    void deleteAll();

    @Query("SELECT * from movie_table ORDER BY movie_name ASC")
    LiveData<List<MovieModel>> getAllFavMovie();

    @Query("SELECT * from movie_table WHERE id LIKE :id")
    LiveData<List<MovieModel>> findFavMovieById(int id);

    @Delete
    void deleteFavMovie(MovieModel movieModel);
}