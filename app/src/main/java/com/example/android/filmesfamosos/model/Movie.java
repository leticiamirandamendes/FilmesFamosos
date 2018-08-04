package com.example.android.filmesfamosos.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class Movie implements Serializable {

    public static final String TMDB_IMAGE_PATH = "http://image.tmdb.org/t/p/w185";
    private String title;

    @SerializedName("poster_path")
    private String poster;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("overview")
    private String description;

    @SerializedName("backdrop_path")
    private String backdrop;

    @SerializedName("vote_average")
    private String reviews;

    @SerializedName("id")
    private String id;

    public String getTitle() {
        return title;
    }

    public String getReleaseDate () { return releaseDate; }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return TMDB_IMAGE_PATH + poster;
    }

    public String getRating() { return reviews; }

    public String getDescription() {
        return description;
    }

    public static class MovieResult {
        private List<Movie> results;

        public List<Movie> getResults() {
            return results;
        }
    }

    public String getId() {
        return id;
    }
}
