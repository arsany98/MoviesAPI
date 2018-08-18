package com.example.user.moviesapi;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.moviesapi.Adapter.MovieAdapter;
import com.example.user.moviesapi.Model.Movie;
import com.example.user.moviesapi.R;
import com.example.user.moviesapi.VolleyManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Movie> movieList;
    MovieAdapter movieAdapter;
    RecyclerView recyclerView;
    Button prev,next;
    TextView pageTextView;
    VolleyManager volleyManager;
    int page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prev = findViewById(R.id.previous_button);
        next = findViewById(R.id.next_button);
        pageTextView = findViewById(R.id.page_textView);
        page=1;
        pageTextView.setText(Integer.toString(page));

        recyclerView = findViewById(R.id.movies_recyclerView);
        movieList = new ArrayList<>();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            recyclerView.setLayoutManager(new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false));
        else
            recyclerView.setLayoutManager(new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false));

        movieAdapter=new MovieAdapter(this,movieList);
        recyclerView.setAdapter(movieAdapter);

        volleyManager= new VolleyManager(this,movieList,movieAdapter);

        volleyManager.getPopularMovies(page);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page--;
                if(page==1)
                    prev.setVisibility(View.INVISIBLE);
                else
                    prev.setVisibility(View.VISIBLE);
                if(page==Movie.totalPages)
                    next.setVisibility(View.INVISIBLE);
                else
                    next.setVisibility(View.VISIBLE);

                volleyManager.getPopularMovies(page);
                pageTextView.setText(Integer.toString(page));
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page++;
                if(page==1)
                    prev.setVisibility(View.INVISIBLE);
                else
                    prev.setVisibility(View.VISIBLE);
                if(page==Movie.totalPages)
                    next.setVisibility(View.INVISIBLE);
                else
                    next.setVisibility(View.VISIBLE);
                volleyManager.getPopularMovies(page);
                pageTextView.setText(Integer.toString(page));
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("page",page);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        page = savedInstanceState.getInt("page");
        volleyManager.getPopularMovies(page);
        pageTextView.setText(Integer.toString(page));
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
        if(item.getItemId() == R.id.favorite_movies)
        {
            Intent intent = new Intent(this, FavoriteActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
