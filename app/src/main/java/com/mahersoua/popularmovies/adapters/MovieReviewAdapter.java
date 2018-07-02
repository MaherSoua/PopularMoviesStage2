package com.mahersoua.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mahersoua.popularmovies.R;
import com.mahersoua.popularmovies.activities.MovieReviewActivity;
import com.mahersoua.popularmovies.models.MovieReviewModel;

import org.w3c.dom.Text;

import java.util.List;

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.MovieReviewHolder> {

    List<MovieReviewModel> mList;
    Context mContext;

    public MovieReviewAdapter(Context context, List<MovieReviewModel> movieReviewModel) {
        mContext = context;
        mList = movieReviewModel;
    }


    @NonNull
    @Override
    public MovieReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_review_item, parent, false);
        return new MovieReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieReviewHolder holder, int position) {
        holder.movieReview.setText(mList.get(position).getContent());
        holder.movieReviewAuthor.setText(mList.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MovieReviewHolder extends RecyclerView.ViewHolder {

        TextView movieReview;
        TextView movieReviewAuthor;

        public MovieReviewHolder(View itemView) {
            super(itemView);
            movieReview = itemView.findViewById(R.id.movie_review);
            movieReviewAuthor = itemView.findViewById(R.id.movie_review_author);
        }
    }
}
