package com.example.user.moviesapi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.user.moviesapi.Adapter.MovieAdapter;
import com.example.user.moviesapi.Adapter.PersonAdapter;
import com.example.user.moviesapi.Model.Movie;
import com.example.user.moviesapi.Model.Person;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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

        personAdapter = new PersonAdapter(this,movie.CastAndCrew);
        movieAdapter = new MovieAdapter(this,movie.Recommendations);
        volleyManager = new VolleyManager(this,movie,personAdapter,movieAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recommendations.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(personAdapter);
        recommendations.setAdapter(movieAdapter);

        String movieID = intent.getStringExtra("ID");

        volleyManager.getDetails(movieID);
        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void setViews()
    {
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
}
