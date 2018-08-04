package com.example.android.filmesfamosos;

import com.example.android.filmesfamosos.model.Movie;
import com.example.android.filmesfamosos.model.Review;
import com.example.android.filmesfamosos.model.Trailer;

import retrofit.Callback;
import retrofit.http.GET;

public interface MoviesApiService {
    @GET("/movie/popular")
    void getPopularMovies(Callback<Movie.MovieResult> cb);
    @GET("/movie/top_rated")
    void getTopRatedMovies(Callback<Movie.MovieResult> cb);
    @GET("/videos")
    void getTrailer(Callback<Trailer.TrailerResult> cb);
    @GET("/reviews")
    void getReview(Callback<Review.ReviewResult> cb);
}
