package com.mahersoua.popularmovies.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.mahersoua.popularmovies.models.MovieModel;
import com.mahersoua.popularmovies.repository.MovieRepository;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {
    private MovieRepository mRepository;
    private LiveData<List<MovieModel>> mMovieList;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        mRepository = new MovieRepository(application);
        mMovieList = mRepository.getAllMovieList();
    }

    public LiveData<List<MovieModel>> getmMovieList() {
        return mMovieList;
    }

    public void insert(MovieModel movie){
        mRepository.insert(movie);
    }
}
