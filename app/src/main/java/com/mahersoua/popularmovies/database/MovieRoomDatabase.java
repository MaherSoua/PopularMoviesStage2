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


    public static MovieRoomDatabase getInstance(final Context context){
        if(sInstance == null){
            synchronized (MovieRoomDatabase.class){
                if(sInstance == null){
                    sInstance = Room.databaseBuilder(context.getApplicationContext() ,
                            MovieRoomDatabase.class ,
                            "movie_database")
                            .build();
                }
            }
        }
        return sInstance;
    }
}