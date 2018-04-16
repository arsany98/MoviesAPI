package com.example.user.moviesapi;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.moviesapi.Adapter.MovieAdapter;
import com.example.user.moviesapi.Model.Movie;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Movie> movieList;
    MovieAdapter movieAdapter;
    RecyclerView recyclerView;
    Button prev,next;
    TextView pageTextView;
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
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        else
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        movieAdapter=new MovieAdapter(this,movieList);
        recyclerView.setAdapter(movieAdapter);
        downloadFromInternet(page);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(page>1)
                    page--;
                downloadFromInternet(page);
                pageTextView.setText(Integer.toString(page));
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(page<998)
                    page++;
                downloadFromInternet(page);
                pageTextView.setText(Integer.toString(page));
            }
        });
    }
    void downloadFromInternet(int page)
    {
        String URL = getString(R.string.Base_URL) + getString(R.string.Key)+"&page="+page;
        Ion.with(this).load(URL).asJsonObject().setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                JsonArray arr = result.getAsJsonArray("results");
                movieList.clear();
                for (int i = 0; i < arr.size(); i++)
                {
                    Movie movie = new Movie();
                    JsonObject item = arr.get(i).getAsJsonObject();
                    movie.setTitle(item.get("title").toString().replaceAll("\"",""));
                    movie.setYear(item.get("release_date").toString().replaceAll("\"",""));
                    String imagePath = item.get("poster_path").toString().replaceAll("\"","");
                    movie.setImage(getString(R.string.Image_URL)+getString(R.string.Image_size)+imagePath);
                    movieList.add(movie);
                }
                movieAdapter.notifyDataSetChanged();
            }
        });

        /*String URL = getString(R.string.Base_URL) + getString(R.string.Key)+"&page="+page;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    JSONArray arr = response.getJSONArray("results");
                    movieList.clear();
                    for (int i = 0; i < arr.length(); i++)
                    {
                        Movie movie = new Movie();
                        JSONObject item = arr.getJSONObject(i);
                        movie.setTitle(item.get("title").toString());
                        movie.setYear(item.get("release_date").toString());
                        movie.setImage(getString(R.string.Image_URL)+getString(R.string.Image_size)+item.get("poster_path").toString());
                        movieList.add(movie);
                    }
                    movieAdapter.notifyDataSetChanged();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        },
        new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

                Log.i("error","an error occured");
            }
        });
        requestQueue.add(jsonObjectRequest);*/
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
        downloadFromInternet(page);
        pageTextView.setText(Integer.toString(page));
    }
}
