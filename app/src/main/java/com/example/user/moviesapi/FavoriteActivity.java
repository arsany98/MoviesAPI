package com.example.user.moviesapi;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.moviesapi.Adapter.MovieAdapter;
import com.example.user.moviesapi.Adapter.TvShowAdapter;
import com.example.user.moviesapi.Database.DbHelper;
import com.example.user.moviesapi.Model.Movie;
import com.example.user.moviesapi.Model.TvShow;
import com.example.user.moviesapi.R;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    private LinearLayout favorite;
    private RecyclerView favoriteMoviesRecyclerView;
    private RecyclerView favoriteTvShowsRecyclerView;
    private ArrayList<Movie> favoriteMoviesList;
    private ArrayList<TvShow> favoriteTvShowsList;
    private MovieAdapter movieAdapter;
    private TvShowAdapter tvShowAdapter;
    private DbHelper dbHelper;
    private TextView noFav;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        favorite = findViewById(R.id.favorite);

        favoriteMoviesRecyclerView = findViewById(R.id.favorite_movies_recyclerView);
        favoriteTvShowsRecyclerView = findViewById(R.id.favorite_tvshows_recyclerView);

        dbHelper = new DbHelper(this);
        favoriteMoviesList = dbHelper.getMovies();
        favoriteTvShowsList = dbHelper.getTvShows();

        movieAdapter = new MovieAdapter(this, favoriteMoviesList);
        tvShowAdapter = new TvShowAdapter(this, favoriteTvShowsList);

        noFav = findViewById(R.id.no_fav_txt);
        actionBar = getSupportActionBar();

        favoriteMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
        favoriteTvShowsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));

        actionBar.setDisplayHomeAsUpEnabled(true);
        favoriteMoviesRecyclerView.setAdapter(movieAdapter);
        favoriteTvShowsRecyclerView.setAdapter(tvShowAdapter);

        if(favoriteMoviesList.isEmpty()&&favoriteTvShowsList.isEmpty())
        {
            favorite.setVisibility(View.GONE);
            noFav.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        favoriteMoviesList = dbHelper.getMovies();
        favoriteTvShowsList = dbHelper.getTvShows();

        movieAdapter = new MovieAdapter(this, favoriteMoviesList);
        tvShowAdapter = new TvShowAdapter(this, favoriteTvShowsList);

        favoriteMoviesRecyclerView.setAdapter(movieAdapter);
        favoriteTvShowsRecyclerView.setAdapter(tvShowAdapter);

        if(favoriteMoviesList.isEmpty()&&favoriteTvShowsList.isEmpty())
        {
            favorite.setVisibility(View.GONE);
            noFav.setVisibility(View.VISIBLE);
        }
    }
}
