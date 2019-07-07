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
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.user.moviesapi.Adapter.MovieAdapter;
import com.example.user.moviesapi.Adapter.TvShowAdapter;
import com.example.user.moviesapi.Model.Movie;
import com.example.user.moviesapi.Model.TvShow;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {

    private RecyclerView movieRecyclerView;
    private MovieAdapter movieAdapter;
    private ArrayList<Movie> movieList;

    private RecyclerView tvShowRecyclerView;
    private TvShowAdapter tvShowAdapter;
    private ArrayList<TvShow> tvShowList;

    private LinearLayout searchResults;
    private VolleyManager volleyManager;
    private TextView noRes;
    private ActionBar actionBar;
    private ProgressBar searchProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        movieRecyclerView = findViewById(R.id.movies_recyclerView);
        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(this,movieList);

        tvShowRecyclerView = findViewById(R.id.tv_shows_recyclerView);
        tvShowList = new ArrayList<>();
        tvShowAdapter = new TvShowAdapter(this,tvShowList);

        searchResults = findViewById(R.id.search_results);
        volleyManager = new VolleyManager(this);
        noRes = findViewById(R.id.no_res_txt);
        actionBar = getSupportActionBar();
        searchProgress = findViewById(R.id.search_progress);

        movieRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        tvShowRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));

        actionBar.setDisplayHomeAsUpEnabled(true);
        movieRecyclerView.setAdapter(movieAdapter);
        tvShowRecyclerView.setAdapter(tvShowAdapter);
        searchProgress.setVisibility(View.VISIBLE);
        handleIntent(getIntent());
    }
    public void handleIntent(Intent intent)
    {
        if(Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            String query = intent.getStringExtra(SearchManager.QUERY);
            volleyManager.Search(query, movieList, tvShowList);
        }
    }

    public void onLoad()
    {
        movieAdapter.notifyDataSetChanged();
        tvShowAdapter.notifyDataSetChanged();

        searchProgress.setVisibility(View.GONE);
        if(movieList.isEmpty())
        {
            searchResults.setVisibility(View.GONE);
            noRes.setVisibility(View.VISIBLE);
        }
    }

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.favorite)
        {
            Intent intent = new Intent(this, FavoriteActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
