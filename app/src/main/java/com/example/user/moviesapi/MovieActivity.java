package com.example.user.moviesapi;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.moviesapi.Adapter.MovieAdapter;
import com.example.user.moviesapi.Adapter.PersonAdapter;
import com.example.user.moviesapi.Database.DbHelper;
import com.example.user.moviesapi.Model.Movie;
import com.squareup.picasso.Picasso;

public class MovieActivity extends AppCompatActivity {
    TextView overview;
    FrameLayout trailer;
    VolleyManager volleyManager;
    ImageView thumbnail;
    ImageView poster;
    TextView title;
    TextView rating;
    TextView voteCount;
    TextView genres;
    TextView runtime;
    TextView date;
    TextView directors;
    TextView writers;
    TextView budget;
    TextView revenue;
    RecyclerView recyclerView;
    PersonAdapter personAdapter;
    MovieAdapter movieAdapter;
    RecyclerView recommendations;
    Movie movie;
    ProgressBar progressBar;
    android.support.v7.app.ActionBar actionBar;
    Button addToFavorite;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        movie = new Movie();

        Intent intent = getIntent();
        thumbnail = findViewById(R.id.thumbnnail);
        trailer = findViewById(R.id.trailer_video);
        overview = findViewById(R.id.overview);
        poster = findViewById(R.id.poster);
        title = findViewById(R.id.title);
        rating = findViewById(R.id.rating);
        voteCount = findViewById(R.id.vote_count);
        genres = findViewById(R.id.genres);
        runtime = findViewById(R.id.runtime);
        date = findViewById(R.id.release_date);
        recyclerView = findViewById(R.id.cast);
        directors = findViewById(R.id.directors);
        writers = findViewById(R.id.writers);
        budget = findViewById(R.id.budget);
        revenue = findViewById(R.id.revenue);
        recommendations = findViewById(R.id.recommendations);
        progressBar = findViewById(R.id.progress);
        actionBar = getSupportActionBar();
        addToFavorite = findViewById(R.id.add_to_favorite);
        dbHelper = new DbHelper(this);

        personAdapter = new PersonAdapter(this,movie.CastAndCrew);
        movieAdapter = new MovieAdapter(this,movie.Recommendations);
        volleyManager = new VolleyManager(this,movie,personAdapter,movieAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recommendations.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(personAdapter);
        recommendations.setAdapter(movieAdapter);

        progressBar.setVisibility(View.VISIBLE);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        String movieID = intent.getStringExtra("ID");
        movie.setID(movieID);
        if(dbHelper.search(movie))
        {
            movie.setFavorite(true);
        }
        volleyManager.getDetails(movieID);
    }

    public void onLoad()
    {
        if(movie.isFavorite())
        {
            addToFavorite.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.baseline_favorite_white_36, 0, 0);
        }
        progressBar.setVisibility(View.GONE);
        overview.setText(movie.getOverview());
        if(movie.getTrailer()==null)
            trailer.setVisibility(View.GONE);

        Picasso.with(this).load("https://img.youtube.com/vi/"+ movie.getTrailer() +"/hqdefault.jpg").into(thumbnail);

        trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://www.youtube.com/watch?v=" + movie.getTrailer()));
                startActivity(i);
            }
        });

        addToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(movie.isFavorite())
                {
                    dbHelper.deleteMovie(movie);
                    Toast.makeText(MovieActivity.this , "Removed From Favorite", Toast.LENGTH_SHORT).show();
                    addToFavorite.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.baseline_favorite_border_white_36, 0, 0);
                    movie.setFavorite(false);
                }
                else
                {
                    dbHelper.addMovie(movie);
                    Toast.makeText(MovieActivity.this , "Added To Favorite", Toast.LENGTH_SHORT).show();
                    addToFavorite.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.baseline_favorite_white_36, 0, 0);
                    movie.setFavorite(true);
                }

            }
        });
        Picasso.with(this).load(movie.getImage()).into(poster);

        title.setText(movie.getTitle());
        rating.setText(movie.getRating() + "/10");
        voteCount.setText(String.format("%,d",movie.getVoteCount()));

        String s = "";
        for(int i = 0 ; i < movie.getGenres().size() ; i++)
        {
            s += movie.getGenres().get(i);
            if(i < movie.getGenres().size()-1)
             s+= ", ";
        }
        genres.setText(s);
        String temp = movie.getRuntime()/60 + "h " + movie.getRuntime()%60 + "min";
        runtime.setText(temp);
        date.setText(movie.getYear());

        String d="Directors: ";
        for(int i = 0 ; i < movie.getDirectors().size(); i++)
        {
            d+=movie.getDirectors().get(i);
            if(i<movie.getDirectors().size()-1)
                d+=", ";
        }
        directors.setText(d);

        String w="Writers: ";
        for(int i = 0 ; i < movie.getWriters().size(); i++)
        {
            w+=movie.getWriters().get(i);
            if(i<movie.getWriters().size()-1)
                w+=", ";
        }
        writers.setText(w);

        budget.setText("Budget: "+ String.format("%,.02f $",movie.getBudget()));
        revenue.setText("Revenue: "+ String.format("%,.02f $",movie.getRevenue()));
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
