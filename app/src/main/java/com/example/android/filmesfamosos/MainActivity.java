package com.example.android.filmesfamosos;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.android.filmesfamosos.adapter.MoviesAdapter;
import com.example.android.filmesfamosos.model.Movie;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity implements MoviesAdapter.ListItemClickListener {

    public static RecyclerView mRecyclerView;
    private MoviesAdapter mAdapter;
    public MoviesApiService service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new MoviesAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);
        final List<Movie> movies = new ArrayList<>();
        for (Movie mov : movies) {
            movies.add(new Movie());
        }
        mAdapter.setMovieList(movies);
        if (isNetworkConnected(this)) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(this.getString(R.string.api_endpoint))
                    .setRequestInterceptor(new RequestInterceptor() {
                        @Override
                        public void intercept(RequestFacade request) {
                            request.addEncodedQueryParam("api_key","PUT HERE THE API KEY");
                        }
                    })
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();
            service = restAdapter.create(MoviesApiService.class);
            service.getPopularMovies(new Callback<Movie.MovieResult>() {
                @Override
                public void success(Movie.MovieResult movieResult, Response response) {
                    mAdapter.setMovieList(movieResult.getResults());
                }

                @Override
                public void failure(RetrofitError error) {
                    error.printStackTrace();
                }
            });
        } else {
            reload();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.list_popular_movies:
                service.getPopularMovies(new Callback<Movie.MovieResult>() {
                    @Override
                    public void success(Movie.MovieResult movieResult, Response response) {
                        mAdapter.setMovieList(movieResult.getResults());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.printStackTrace();
                    }
                });
                break;
            case R.id.list_top_rated_movies:
                service.getTopRatedMovies(new Callback<Movie.MovieResult>() {
                    @Override
                    public void success(Movie.MovieResult movieResult, Response response) {
                        mAdapter.setMovieList(movieResult.getResults());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.printStackTrace();
                    }
                });
                break;
            case R.id.fav_movies:
                Intent intent = new Intent(MainActivity.this, Favs.class);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    public void onListItemClick (Movie selectedMovie){
        Intent myIntent = new Intent(MainActivity.this, MovieDescription.class);
        myIntent.putExtra(getString(R.string.selected_movie), selectedMovie);
        MainActivity.this.startActivity(myIntent);
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
    public void reload(){
        CoordinatorLayout MainCoordinatorLayout = findViewById(R.id.c_layout);
        Snackbar snackbar = Snackbar.make(MainCoordinatorLayout, getString(R.string.offline_text), Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(getString(R.string.retry), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });
        snackbar.show();
    }

}
