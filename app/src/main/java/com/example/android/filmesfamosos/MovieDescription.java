package com.example.android.filmesfamosos;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.android.filmesfamosos.adapter.ReviewAdapter;
import com.example.android.filmesfamosos.adapter.TrailerAdapter;
import com.example.android.filmesfamosos.data.TaskContract;
import com.example.android.filmesfamosos.model.Movie;
import com.example.android.filmesfamosos.model.Review;
import com.example.android.filmesfamosos.model.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MovieDescription extends AppCompatActivity implements TrailerAdapter.ListTrailerClickListener{

    @BindView(R.id.movie_title)
    TextView title;

    @BindView(R.id.movie_release)
    TextView release;

    @BindView(R.id.movie_description)
    TextView description;

    @BindView(R.id.movie_poster)
    ImageView poster;

    @BindView(R.id.rating)
    TextView ratingText;

    @BindView(R.id.movie_rating)
    RatingBar rating;

    @BindView(R.id.add_fav_btn)
    Button btn;

    private boolean isBookmarked = false;

    public MoviesApiService service;
    private TrailerAdapter mAdapter;
    private ReviewAdapter reviewAdapter;
    private Movie selectedMovie = new Movie();
    private CustomCursorAdapter cursorAdapter;

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_film_description);
        Intent i = getIntent();
        ButterKnife.bind(this);
        setMovieData(i);
        setMovieTrailerList();
        setMovieReviewList();
        Cursor c = getContentResolver().query(
                TaskContract.TaskEntry.CONTENT_URI,
                null,
                " id=" + selectedMovie.getId(),
                null,
                null);
        if(c.getCount() > 0){
            btn.setText(getString(R.string.fav));
            btn.setTextColor(R.color.colorPrimary);
            isBookmarked = true;
        }

    }

    public void setMovieData(Intent i){
        Movie movie = (Movie) i.getSerializableExtra(this.getString(R.string.selected_movie));
        selectedMovie = movie;
        title.setText(movie.getTitle());
        release.setText(movie.getReleaseDate());
        description.setText(movie.getDescription());
        ratingText.setText(movie.getRating());
        rating.setRating(Float.parseFloat(movie.getRating()));
        rating.setRating(rating.getRating());
        Picasso.with(this)
                .load(movie.getPoster())
                .placeholder(R.color.colorAccent)
                .into(poster);
    }

    public void setMovieTrailerList() {

        RecyclerView mTrailersRecyclerView;

        mTrailersRecyclerView = findViewById(R.id.rv_trailers);
        mTrailersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        final List<Trailer> trailers = new ArrayList<>();
        mAdapter = new TrailerAdapter(this, this);
        mTrailersRecyclerView.setAdapter(mAdapter);
        mAdapter.setTrailerList(trailers);

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(this.getString(R.string.url_movie) + selectedMovie.getId())
                    .setRequestInterceptor(new RequestInterceptor() {
                        @Override
                        public void intercept(RequestFacade request) {
                            request.addEncodedQueryParam("api_key","PUT HERE THE API KEY");
                        }
                    })
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();
            service = restAdapter.create(MoviesApiService.class);
            service.getTrailer(new Callback<Trailer.TrailerResult>() {

                @Override
                public void success(Trailer.TrailerResult trailerResult, Response response) {
                    mAdapter.setTrailerList(trailerResult.getResults());
                    for (Trailer mov : trailers) {
                        trailers.add(new Trailer());
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e(getString(R.string.error), error.toString());
                }
            });

    }

    public void setMovieReviewList() {

        RecyclerView mReviewsRecyclerView;

        mReviewsRecyclerView = findViewById(R.id.rv_reviews);
        mReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        final List<Review> reviews = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(this);
        mReviewsRecyclerView.setAdapter(reviewAdapter);
        reviewAdapter.setReviewList(reviews);

            RestAdapter restAdapterReviews = new RestAdapter.Builder()
                    .setEndpoint(this.getString(R.string.url_movie) + selectedMovie.getId())
                    .setRequestInterceptor(new RequestInterceptor() {
                        @Override
                        public void intercept(RequestFacade request) {
                            request.addEncodedQueryParam("api_key","PUT HERE THE API KEY");
                        }
                    })
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();
            service = restAdapterReviews.create(MoviesApiService.class);
            service.getReview(new Callback<Review.ReviewResult>() {
                @Override
                public void success(Review.ReviewResult reviewResult, Response response) {
                    reviewAdapter.setReviewList(reviewResult.getResults());
                    for (Review mov : reviews) {
                        reviews.add(new Review());
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e(getString(R.string.error), error.toString());
                }
            });

    }

    @Override
    public void onListTrailerClick(Trailer selected) {
        String url = this.getString(R.string.url_yt)+selected.getKey();
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        PackageManager packageManager = this.getPackageManager();
        if (webIntent.resolveActivity(packageManager) != null) {
            this.startActivity(webIntent);
        }
    }

    @SuppressLint("ResourceType")
    @OnClick(R.id.add_fav_btn)
    public void onClick() {
        ContentValues contentValues = new ContentValues();
        if (isBookmarked) {
            finish();

        } else {
            String id = selectedMovie.getId();
            String name = selectedMovie.getTitle();
            String description = selectedMovie.getDescription();
            contentValues.put(TaskContract.TaskEntry.COLUMN_ID, id);
            contentValues.put(TaskContract.TaskEntry.COLUMN_NAME, name);
            contentValues.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, description);
            getContentResolver().insert(TaskContract.TaskEntry.CONTENT_URI, contentValues);
            finish();
        }
            Intent intent = new Intent(MovieDescription.this, Favs.class);
            startActivity(intent);
        }
    }




