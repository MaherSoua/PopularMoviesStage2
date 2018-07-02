package com.mahersoua.popularmovies.activities;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.mahersoua.popularmovies.R;
import com.mahersoua.popularmovies.adapters.MovieCatalogAdapter;
import com.mahersoua.popularmovies.adapters.MovieReviewAdapter;
import com.mahersoua.popularmovies.models.MovieModel;
import com.mahersoua.popularmovies.models.MovieReviewModel;

import java.util.List;

public class MovieReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_review);

        List<MovieReviewModel> movieReviewModel = getIntent().getParcelableArrayListExtra(DetailsActivity.MOVIE_REVIEW_EXTRA);
        RecyclerView recyclerView = findViewById(R.id.reviewRecyclerView);
        MovieReviewAdapter movieReviewAdapter = new MovieReviewAdapter(this, movieReviewModel);
        recyclerView.setAdapter(movieReviewAdapter);
        RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
    }
}
