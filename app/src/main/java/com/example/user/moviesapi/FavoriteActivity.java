package com.example.user.moviesapi;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.moviesapi.Adapter.MovieAdapter;
import com.example.user.moviesapi.Database.DbHelper;
import com.example.user.moviesapi.Model.Movie;
import com.example.user.moviesapi.R;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    RecyclerView favoriteRecyclerView;
    ArrayList<Movie> favoriteList;
    MovieAdapter movieAdapter;
    DbHelper dbHelper;
    TextView noFav;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        favoriteRecyclerView = findViewById(R.id.favorite_recyclerView);
        dbHelper = new DbHelper(this);
        favoriteList = dbHelper.getMovies();
        movieAdapter = new MovieAdapter(this, favoriteList);
        noFav = findViewById(R.id.no_fav_txt);
        actionBar = getSupportActionBar();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            favoriteRecyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        else
            favoriteRecyclerView.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));

        actionBar.setDisplayHomeAsUpEnabled(true);
        favoriteRecyclerView.setAdapter(movieAdapter);

        if(favoriteList.isEmpty())
        {
            noFav.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        favoriteList = dbHelper.getMovies();
        movieAdapter = new MovieAdapter(this, favoriteList);
        favoriteRecyclerView.setAdapter(movieAdapter);
        if(favoriteList.isEmpty())
        {
            noFav.setVisibility(View.VISIBLE);
        }
    }
}
