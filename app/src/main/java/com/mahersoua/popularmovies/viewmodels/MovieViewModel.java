package com.mahersoua.popularmovies.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.mahersoua.popularmovies.models.MovieModel;
import com.mahersoua.popularmovies.repository.MovieRepository;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {
    private final MovieRepository mRepository;
    private final LiveData<List<MovieModel>> mMovieList;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        mRepository = new MovieRepository(application);
        mMovieList = mRepository.getAllMovieList();
    }

    public LiveData<List<MovieModel>> getMovieList() {
        return mMovieList;
    }

    public void insert(MovieModel movie){
        mRepository.insert(movie);
    }
    public void delete(MovieModel movie) { mRepository.delete(movie);}
    public void findById(MovieModel movie) { mRepository.findFavMovieById(movie);}
}
