package com.example.user.moviesapi;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import com.example.user.moviesapi.Adapter.TvShowAdapter;
import com.example.user.moviesapi.Database.DbHelper;
import com.example.user.moviesapi.Model.TvShow;
import com.squareup.picasso.Picasso;

public class TvShowActivity extends AppCompatActivity {
    private TextView overview;
    private FrameLayout trailer;
    private VolleyManager volleyManager;
    private ImageView thumbnail;
    private ImageView poster;
    private TextView title;
    private TextView rating;
    private TextView voteCount;
    private TextView genres;
    private TextView runtime;
    private TextView date;
    private TextView createdBy;
    private RecyclerView castAndCrew;
    private PersonAdapter personAdapter;
    private TvShowAdapter tvShowAdapter;
    private RecyclerView recommendations;
    private TvShow tvShow;
    private ProgressBar progressBar;
    private ActionBar actionBar;
    private Button addToFavorite;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show);

        tvShow = new TvShow();

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
        castAndCrew = findViewById(R.id.cast);
        createdBy = findViewById(R.id.created_by);
        recommendations = findViewById(R.id.recommendations);
        progressBar = findViewById(R.id.progress);
        actionBar = getSupportActionBar();
        addToFavorite = findViewById(R.id.add_to_favorite);
        dbHelper = new DbHelper(this);

        personAdapter = new PersonAdapter(this,tvShow.getCastAndCrew());
        tvShowAdapter = new TvShowAdapter(this,tvShow.getRecommendations());
        volleyManager = new VolleyManager(this);

        castAndCrew.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recommendations.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        castAndCrew.setAdapter(personAdapter);
        recommendations.setAdapter(tvShowAdapter);

        progressBar.setVisibility(View.VISIBLE);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        String tvShowID = intent.getStringExtra("ID");
        tvShow.setID(tvShowID);
        if(dbHelper.search(tvShow))
        {
            tvShow.setFavorite(true);
        }
        volleyManager.getTvShowDetails(tvShowID, tvShow);
    }

    public void onLoad()
    {
        if(tvShow.isFavorite())
        {
            addToFavorite.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.baseline_favorite_white_36, 0, 0);
        }
        progressBar.setVisibility(View.GONE);
        overview.setText(tvShow.getOverview());
        if(tvShow.getTrailer()==null)
            trailer.setVisibility(View.GONE);

        Picasso.with(this).load("https://img.youtube.com/vi/"+ tvShow.getTrailer() +"/hqdefault.jpg").into(thumbnail);

        trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://www.youtube.com/watch?v=" + tvShow.getTrailer()));
                startActivity(i);
            }
        });

        addToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvShow.isFavorite())
                {
                    dbHelper.deleteTvShow(tvShow);
                    Toast.makeText(TvShowActivity.this , "Removed From Favorite", Toast.LENGTH_SHORT).show();
                    addToFavorite.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.baseline_favorite_border_white_36, 0, 0);
                    tvShow.setFavorite(false);
                }
                else
                {
                    dbHelper.addTvShow(tvShow);
                    Toast.makeText(TvShowActivity.this , "Added To Favorite", Toast.LENGTH_SHORT).show();
                    addToFavorite.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.baseline_favorite_white_36, 0, 0);
                    tvShow.setFavorite(true);
                }

            }
        });
        Picasso.with(this).load(tvShow.getImage()).into(poster);

        title.setText(tvShow.getTitle());
        rating.setText(tvShow.getRating() + "/10");
        voteCount.setText(String.format("%,d",tvShow.getVoteCount()));

        String s = "";
        for(int i = 0 ; i < tvShow.getGenres().size() ; i++)
        {
            s += tvShow.getGenres().get(i);
            if(i < tvShow.getGenres().size()-1)
                s+= ", ";
        }
        genres.setText(s);
        String temp = tvShow.getRuntime()/60 + "h " + tvShow.getRuntime()%60 + "min";
        runtime.setText(temp);
        date.setText(tvShow.getYear());

        String creators="Created By: ";
        for(int i = 0 ; i < tvShow.getCreators().size(); i++)
        {
            creators+=tvShow.getCreators().get(i);
            if(i<tvShow.getCreators().size()-1)
                creators+=", ";
        }
        createdBy.setText(creators);

        personAdapter.notifyDataSetChanged();
        tvShowAdapter.notifyDataSetChanged();
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
