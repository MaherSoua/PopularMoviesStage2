package com.mahersoua.popularmovies.activities;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mahersoua.popularmovies.R;
import com.mahersoua.popularmovies.adapters.MovieCatalogAdapter;
import com.mahersoua.popularmovies.adapters.MovieReviewAdapter;
import com.mahersoua.popularmovies.data.MoviesDataLoader;
import com.mahersoua.popularmovies.models.MovieModel;
import com.mahersoua.popularmovies.models.MovieReviewModel;
import com.mahersoua.popularmovies.models.MovieTrailerInfo;
import com.mahersoua.popularmovies.utils.JsonUtils;
import com.mahersoua.popularmovies.viewmodels.MovieViewModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener , MoviesDataLoader.IMoviesCallback {

    public static String MOVIE_REVIEW_EXTRA = "movie_overview_extra";
    private MovieViewModel mMovieViewModel;
    private MovieModel mMovieModel;
    private int mCurrentSelectedBtnId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (null != getIntent().getExtras()) {
            Parcelable parcelable = getIntent().getExtras().getParcelable(MovieCatalogAdapter.MOVIE_EXTRAS);
            mMovieModel = (MovieModel) parcelable;

            TextView movieNameTv = findViewById(R.id.movieName);
            RatingBar ratingBar = findViewById(R.id.ratingBar);
            TextView releaseDate = findViewById(R.id.releaseDate);
            TextView synopsisTv = findViewById(R.id.synopsisTv);
            CheckBox favMovieBtn = findViewById(R.id.favButton);
            Button trailerBtn = findViewById(R.id.trailerButton);
            Button reviewBtn = findViewById(R.id.reviewButton);


            mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
            mMovieViewModel.getMovieList().observe(this, new Observer<List<MovieModel>>() {
                @Override
                public void onChanged(@Nullable List<MovieModel> movieList) {
                    if(isFav(movieList, mMovieModel.getId())){
                        favMovieBtn.setChecked(true);
                    }
                }
            });

            if (null != mMovieModel) {
                movieNameTv.setText(mMovieModel.getTitle());
                ratingBar.setRating(mMovieModel.getVoteAverage());
                releaseDate.setText(mMovieModel.getReleaseDate());
                synopsisTv.setText(mMovieModel.getOverview());

                ImageView posterContainer = findViewById(R.id.detailsPosterContainer);
                String URL = getString(R.string.image_url).replace("size", "w500");
                Picasso.get().load(URL + mMovieModel.getPosterPath()).into(posterContainer);
                favMovieBtn.setOnClickListener(this);
                trailerBtn.setOnClickListener(this);
                reviewBtn.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.favButton:
                if(((CheckBox)v).isChecked()){
                    mMovieViewModel.insert(mMovieModel);
                } else {
                    mMovieViewModel.delete(mMovieModel);
                }
                break;

            case R.id.trailerButton:
                MoviesDataLoader.getInstance().requestMovieList(this, MoviesDataLoader.getVideoUrl(mMovieModel.getId()));
                break;

            case R.id.reviewButton:
                MoviesDataLoader.getInstance().requestMovieList(this, MoviesDataLoader.getReviewUrl(mMovieModel.getId()));
                break;
        }
        mCurrentSelectedBtnId = v.getId();
    }

    @Override
    public void onLoadFinished(JSONArray jsonArray) {

        switch (mCurrentSelectedBtnId){
            case R.id.trailerButton:
                List<MovieTrailerInfo> movieTrailerInfos = JsonUtils.getMovieTrailerInfo(jsonArray);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                String[] movieTrailerTypes = new String[movieTrailerInfos.size()];
                for(int i = 0 ; i < movieTrailerInfos.size(); i++){
                    movieTrailerTypes[i] = getString(R.string.alert_item_label)
                            .replace("item_index", ""+(i + 1 ) )
                            .replace("item_site", movieTrailerInfos.get(i).getSite());
                }

                builder.setTitle(getString(R.string.alert_trailer_title))
                        .setItems(movieTrailerTypes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                MovieTrailerInfo movieTrailerInfo = movieTrailerInfos.get(which);

                                String url = "";
                                if(movieTrailerInfo.getSite().equals("YouTube")){
                                    url = "https://www.youtube.com/watch?v="+movieTrailerInfo.getKey();
                                }
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(url));
                                startActivity(intent);
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.reviewButton:
                List<MovieReviewModel> movieReviewList = JsonUtils.getMovieReviews(jsonArray);
                Intent intent = new Intent(this, MovieReviewActivity.class);
                intent.putParcelableArrayListExtra(MOVIE_REVIEW_EXTRA, (ArrayList<? extends Parcelable>) movieReviewList);
                startActivity(intent);
                Log.d("DetailsActivity" , "Launch Review ");
                break;
        }
        mCurrentSelectedBtnId = 0;
    }

    @Override
    public void onStartLoading() {

    }

    @Override
    public void onLoadError(String error) {

    }
}
