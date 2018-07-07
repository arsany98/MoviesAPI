package com.example.user.moviesapi;

import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.user.moviesapi.Adapter.MovieAdapter;
import com.example.user.moviesapi.Model.Movie;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    ArrayList<Movie> movieList;
    VolleyManager volleyManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        recyclerView = findViewById(R.id.results_recyclerView);
        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(this,movieList);
        volleyManager = new VolleyManager(this,movieList,movieAdapter);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            recyclerView.setLayoutManager(new GridLayoutManager(this,2, GridLayoutManager.VERTICAL,false));
        else
            recyclerView.setLayoutManager(new GridLayoutManager(this,3, GridLayoutManager.VERTICAL,false));

        recyclerView.setAdapter(movieAdapter);
        handleIntent(getIntent());
    }
    void handleIntent(Intent intent)
    {
        if(Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            String query = intent.getStringExtra(SearchManager.QUERY);
            volleyManager.Search(query);
        }
    }
}
