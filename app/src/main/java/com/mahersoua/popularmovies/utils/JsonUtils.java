package com.mahersoua.popularmovies.utils;

import android.util.Log;

import com.mahersoua.popularmovies.models.MovieModel;
import com.mahersoua.popularmovies.models.MovieReviewModel;
import com.mahersoua.popularmovies.models.MovieTrailerInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String TAG = "JsonUtils";

    public static List<MovieModel> getMoviesArray(JSONArray jsonArray){
        List<MovieModel> movieModelList = new ArrayList<>();

        try {
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonItem = jsonArray.getJSONObject(i);
                MovieModel movieModel = new MovieModel();
                movieModel.setBackdropPath(jsonItem.getString("backdrop_path"));
                movieModel.setVoteAverage(jsonItem.getInt("vote_average"));
                movieModel.setAdult(jsonItem.getBoolean("adult"));
                movieModel.setId(jsonItem.getInt("id"));
                movieModel.setTitle(jsonItem.getString("title"));
                movieModel.setOverview(jsonItem.getString("overview"));
                movieModel.setOriginalLanguage(jsonItem.getString("original_language"));
                movieModel.setReleaseDate(jsonItem.getString("release_date"));
                movieModel.setOriginalTitle(jsonItem.getString("original_title"));
                movieModel.setVoteCount(jsonItem.getInt("vote_count"));
                movieModel.setPosterPath(jsonItem.getString("poster_path"));
                movieModel.setVideo(jsonItem.getBoolean("video"));
                movieModel.setPopularity(jsonItem.getDouble("popularity"));

                movieModelList.add(movieModel);
            }
        } catch (JSONException error){
            Log.d(TAG , ">>>>>>>>> "+error.getMessage());
        }
        return movieModelList;
    }

    public static List<MovieTrailerInfo> getMovieTrailerInfo(JSONArray jsonArray){
        List<MovieTrailerInfo> movieReviewList = new ArrayList<>();

        try {
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonItem =  jsonArray.getJSONObject(i);
                MovieTrailerInfo movieTrailerInfo = new MovieTrailerInfo();
                movieTrailerInfo.setId(jsonItem.getString("id"));
                movieTrailerInfo.setKey(jsonItem.getString("key"));
                movieTrailerInfo.setName(jsonItem.getString("name"));
                movieTrailerInfo.setSite(jsonItem.getString("site"));
                movieTrailerInfo.setSize(jsonItem.getInt("size"));
                movieTrailerInfo.setType(jsonItem.getString("type"));

                movieReviewList.add(movieTrailerInfo);

            }
        } catch (JSONException error){
            Log.d(TAG , "getMovieTrailerInfo : "+error.getMessage());
        }
        return movieReviewList;
    }

    public static List< MovieReviewModel > getMovieReviews(JSONArray jsonArray){
        List<MovieReviewModel> movieReviewList = new ArrayList<>();

        try {
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonItem =  jsonArray.getJSONObject(i);
                MovieReviewModel movieReviewModel = new MovieReviewModel();
                movieReviewModel.setId(jsonItem.getString("id"));
                movieReviewModel.setContent(jsonItem.getString("content"));
                movieReviewModel.setAuthor(jsonItem.getString("author"));
                movieReviewModel.setUrl(jsonItem.getString("url"));

                movieReviewList.add(movieReviewModel);

            }
        } catch (JSONException error){
            Log.d(TAG , "getMovieReviews : "+error.getMessage());
        }
        return movieReviewList;
    }
}
