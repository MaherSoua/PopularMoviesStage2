package com.mahersoua.popularmovies.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.mahersoua.popularmovies.dao.MovieDao;
import com.mahersoua.popularmovies.database.MovieRoomDatabase;
import com.mahersoua.popularmovies.models.MovieModel;

import java.util.List;

public class MovieRepository {
    private final MovieDao mMovieDao;
    private final LiveData<List<MovieModel>> mFavMovieList;
    private String mCurrentQueryType;

    public MovieRepository(Application application){
        MovieRoomDatabase db = MovieRoomDatabase.getInstance(application);
        mMovieDao = db.movieDao();
        mFavMovieList = mMovieDao.getAllFavMovie();
    }

    public LiveData<List<MovieModel>> getAllMovieList(){
        return mFavMovieList;
    }

    public void insert(MovieModel movie){
        mCurrentQueryType = "insert";
        new InsertAsyncTask(mMovieDao).execute(movie);
    }
    public void delete(MovieModel movie) {
        mCurrentQueryType = "delete";
        new InsertAsyncTask(mMovieDao).execute(movie);
    }

    public void findFavMovieById(MovieModel movie){
        mCurrentQueryType = "find";
        new InsertAsyncTask(mMovieDao).execute(movie);
    }

    private class InsertAsyncTask extends AsyncTask<MovieModel, Void, Void> {
        private final MovieDao  mAsyncTaskDao;
        private String mIsInsertion;
        InsertAsyncTask(MovieDao movieDao) {
            mAsyncTaskDao = movieDao;
        }

        @Override
        protected Void doInBackground(MovieModel... simpleMovieModels) {
            switch (mCurrentQueryType){
                case "insert":
                    mAsyncTaskDao.insert(simpleMovieModels[0]);
                    break;
                case "delete":
                    mAsyncTaskDao.deleteFavMovie(simpleMovieModels[0]);
                    break;
                case "find":
                    mAsyncTaskDao.findFavMovieById(simpleMovieModels[0].getId());
                    break;
            }
            return null;
        }
    }
}
