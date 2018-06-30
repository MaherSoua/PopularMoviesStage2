package com.mahersoua.popularmovies.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.mahersoua.popularmovies.dao.MovieDao;
import com.mahersoua.popularmovies.models.MovieModel;

@Database(entities = {MovieModel.class}, version = 1, exportSchema = false)
public abstract class MovieRoomDatabase extends RoomDatabase {
    static MovieRoomDatabase sInstance;
    public abstract MovieDao movieDao();

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(sInstance);
                }
            };

    public static MovieRoomDatabase getInstance(final Context context){
        if(sInstance == null){
            synchronized (MovieRoomDatabase.class){
                if(sInstance == null){
                    sInstance = Room.databaseBuilder(context.getApplicationContext() ,
                            MovieRoomDatabase.class ,
                            "movie_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return sInstance;
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final MovieDao mDao;
        public PopulateDbAsync(MovieRoomDatabase db) {
            mDao = db.movieDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDao.deleteAll();
            return null;
        }
    }
}