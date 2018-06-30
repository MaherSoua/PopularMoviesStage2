package com.mahersoua.popularmovies.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mahersoua.popularmovies.R;
import com.mahersoua.popularmovies.adapters.MovieCatalogAdapter;
import com.mahersoua.popularmovies.models.MovieModel;
import com.mahersoua.popularmovies.viewmodels.MovieViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private MovieViewModel mMovieViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (null != getIntent().getExtras()) {
            Parcelable parcelable = getIntent().getExtras().getParcelable(MovieCatalogAdapter.MOVIE_EXTRAS);
            final MovieModel movieModel = (MovieModel) parcelable;

            TextView movieNameTv = findViewById(R.id.movieName);
            RatingBar ratingBar = findViewById(R.id.ratingBar);
            TextView releaseDate = findViewById(R.id.releaseDate);
            TextView synopsisTv = findViewById(R.id.synopsisTv);
            CheckBox mFavMovieBtn = findViewById(R.id.favButton);

            mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
            mMovieViewModel.getMovieList().observe(this, new Observer<List<MovieModel>>() {
                @Override
                public void onChanged(@Nullable List<MovieModel> movieList) {
                    if(isFav(movieList, movieModel.getId())){
                        mFavMovieBtn.setChecked(true);
                    }
                }
            });

            if (null != movieModel) {
                movieNameTv.setText(movieModel.getTitle());
                ratingBar.setRating(movieModel.getVoteAverage());
                releaseDate.setText(movieModel.getReleaseDate());
                synopsisTv.setText(movieModel.getOverview());

                ImageView posterContainer = findViewById(R.id.detailsPosterContainer);
                String URL = getString(R.string.image_url).replace("size", "w500");
                Picasso.get().load(URL + movieModel.getPosterPath()).into(posterContainer);
                mFavMovieBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(((CheckBox)v).isChecked()){
                            mMovieViewModel.insert(movieModel);
                        } else {
                            mMovieViewModel.delete(movieModel);
                        }
                    }
                });
            }
        }
    }

    public boolean isFav(final List<MovieModel> list, final int id){
        for( MovieModel movieModel : list){
            if(movieModel.getId() ==  id){
                return true;
            }
        }
        return false;
    }
}
