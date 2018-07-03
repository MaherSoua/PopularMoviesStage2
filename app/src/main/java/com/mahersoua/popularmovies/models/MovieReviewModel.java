package com.mahersoua.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieReviewModel implements Parcelable {

    private String id;
    private String content;
    private String author;
    private String url;

    public MovieReviewModel(){

    }

    private MovieReviewModel(Parcel in) {
        id = in.readString();
        content = in.readString();
        author = in.readString();
        url = in.readString();
    }

    public static final Creator<MovieReviewModel> CREATOR = new Creator<MovieReviewModel>() {
        @Override
        public MovieReviewModel createFromParcel(Parcel in) {
            return new MovieReviewModel(in);
        }

        @Override
        public MovieReviewModel[] newArray(int size) {
            return new MovieReviewModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(content);
        dest.writeString(author);
        dest.writeString(url);
    }
}
