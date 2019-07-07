package com.example.user.moviesapi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.moviesapi.Adapter.MovieAdapter;
import com.example.user.moviesapi.Adapter.TvShowAdapter;
import com.example.user.moviesapi.Model.Movie;
import com.example.user.moviesapi.Model.Person;
import com.example.user.moviesapi.Model.TvShow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VolleyManager {
    private Context context;
    public VolleyManager(Context c)
    {
        context = c;
    }
    public void getPopularMovies(int page, final List list, final RecyclerView.Adapter adapter)
    {
        String URL = context.getString(R.string.Base_URL) + "movie/popular?api_key=" + context.getString(R.string.Key)+"&page="+page;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    Movie.totalPages = response.getInt("total_pages");
                    JSONArray arr = response.getJSONArray("results");
                    list.clear();
                    for (int i = 0; i < arr.length(); i++)
                    {
                        Movie movie = new Movie();

                        JSONObject item = arr.getJSONObject(i);

                        movie.setID(item.get("id").toString());
                        movie.setTitle(item.get("title").toString());
                        movie.setYear(item.get("release_date").toString());
                        movie.setImage(context.getString(R.string.Image_URL)+context.getString(R.string.Image_size)+item.get("poster_path").toString());

                        list.add(movie);
                    }
                    adapter.notifyDataSetChanged();
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
                        Toast.makeText(context,"No Internet Connection",Toast.LENGTH_SHORT).show();
                        Log.i("error","an error occurred");
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    public void getPopularTvShows(int page, final List list, final RecyclerView.Adapter adapter)
    {
        String URL = context.getString(R.string.Base_URL) + "tv/popular?api_key=" + context.getString(R.string.Key)+"&page="+page;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    Movie.totalPages = response.getInt("total_pages");
                    JSONArray arr = response.getJSONArray("results");
                    list.clear();
                    for (int i = 0; i < arr.length(); i++)
                    {
                        TvShow tvShow= new TvShow();

                        JSONObject item = arr.getJSONObject(i);

                        tvShow.setID(item.get("id").toString());
                        tvShow.setTitle(item.get("name").toString());
                        tvShow.setYear(item.get("first_air_date").toString());
                        tvShow.setImage(context.getString(R.string.Image_URL)+context.getString(R.string.Image_size)+item.get("poster_path").toString());

                        list.add(tvShow);
                    }
                    adapter.notifyDataSetChanged();
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
                        Toast.makeText(context,"No Internet Connection",Toast.LENGTH_SHORT).show();
                        Log.i("error","an error occurred");
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
    public void getMovieDetails(final String id, final Movie movie)
    {
        String URL = context.getString(R.string.Base_URL) + "movie/" + id + "?api_key=" + context.getString(R.string.Key);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    movie.setOverview(response.get("overview").toString());
                    movie.setImage(context.getString(R.string.Image_URL)+context.getString(R.string.Image_size)+response.get("poster_path").toString());
                    movie.setTitle(response.get("title").toString());
                    movie.setYear(response.get("release_date").toString());
                    movie.setRating(Float.parseFloat(response.get("vote_average").toString()));
                    movie.setVoteCount(response.getInt("vote_count"));
                    movie.setBudget(response.getDouble("budget"));
                    movie.setRevenue(response.getDouble("revenue"));

                    JSONArray arr = response.getJSONArray("genres");
                    ArrayList<String> genres=new ArrayList<>();
                    for(int i = 0 ; i < arr.length() ; i++)
                    {
                        JSONObject genre = arr.getJSONObject(i);
                        genres.add(genre.get("name").toString());
                    }
                    movie.setGenres(genres);
                    movie.setRuntime(response.getInt("runtime"));
                    getMovieVideos(id, movie);
                    getMovieRecommendations(id, movie);
                    getMovieCredits(id, movie);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,"No Internet Connection",Toast.LENGTH_SHORT).show();
                        Log.i("error","an error occurred");
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    public void getTvShowDetails(final String id, final TvShow tvShow)
    {
        String URL = context.getString(R.string.Base_URL) + "tv/" + id + "?api_key=" + context.getString(R.string.Key);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    tvShow.setOverview(response.get("overview").toString());
                    tvShow.setImage(context.getString(R.string.Image_URL)+context.getString(R.string.Image_size)+response.get("poster_path").toString());
                    tvShow.setTitle(response.get("name").toString());
                    tvShow.setYear(response.get("first_air_date").toString());
                    tvShow.setRating(Float.parseFloat(response.get("vote_average").toString()));
                    tvShow.setVoteCount(response.getInt("vote_count"));

                    JSONArray genresArr = response.getJSONArray("genres");
                    ArrayList<String> genres=new ArrayList<>();
                    for(int i = 0 ; i < genresArr.length() ; i++)
                    {
                        JSONObject genre = genresArr.getJSONObject(i);
                        genres.add(genre.get("name").toString());
                    }
                    tvShow.setGenres(genres);
                    JSONArray runTimes = response.getJSONArray("episode_run_time");
                    if(runTimes.length() > 0)
                        tvShow.setRuntime(runTimes.getInt(0));

                    JSONArray createdByArr = response.getJSONArray("created_by");
                    ArrayList<String> createdBy=new ArrayList<>();
                    for(int i = 0 ; i < createdByArr.length() ; i++)
                    {
                        JSONObject object = createdByArr.getJSONObject(i);
                        createdBy.add(object.get("name").toString());
                    }
                    tvShow.setCreators(createdBy);

                    getTvShowVideos(id, tvShow);
                    getTvShowRecommendations(id, tvShow);
                    getTvShowCredits(id, tvShow);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,"No Internet Connection",Toast.LENGTH_SHORT).show();
                        Log.i("error","an error occurred");
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }


    public void getMovieVideos(String id, final Movie movie)
    {
        String URL = context.getString(R.string.Base_URL) + "movie/" + id + "/videos?api_key=" + context.getString(R.string.Key);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray arr = response.getJSONArray("results");
                    for (int i = 0; i < arr.length(); i++)
                    {
                        JSONObject video = arr.getJSONObject(i);
                        if(video.get("type").toString().equals("Trailer"))
                        {
                            movie.setTrailer(video.get("key").toString());
                            break;
                        }
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,"No Internet Connection",Toast.LENGTH_SHORT).show();
                        Log.i("error","an error occurred");
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    public void getTvShowVideos(String id, final TvShow tvShow)
    {
        String URL = context.getString(R.string.Base_URL) + "tv/" + id + "/videos?api_key=" + context.getString(R.string.Key);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray arr = response.getJSONArray("results");
                    for (int i = 0; i < arr.length(); i++)
                    {
                        JSONObject video = arr.getJSONObject(i);
                        if(video.get("type").toString().equals("Trailer"))
                        {
                            tvShow.setTrailer(video.get("key").toString());
                            break;
                        }
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,"No Internet Connection",Toast.LENGTH_SHORT).show();
                        Log.i("error","an error occurred");
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
    public void getMovieRecommendations(String id, final Movie movie)
    {
        String URL = context.getString(R.string.Base_URL) + "movie/"+id+"/recommendations?api_key=" + context.getString(R.string.Key);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    JSONArray arr = response.getJSONArray("results");
                    ArrayList<Movie> Recommendations = movie.getRecommendations();
                    for (int i = 0; i < arr.length(); i++)
                    {
                        Movie movie = new Movie();

                        JSONObject item = arr.getJSONObject(i);

                        movie.setID(item.get("id").toString());
                        movie.setTitle(item.get("title").toString());
                        movie.setYear(item.get("release_date").toString());
                        movie.setImage(context.getString(R.string.Image_URL)+context.getString(R.string.Image_size)+item.get("poster_path").toString());

                        Recommendations.add(movie);
                    }

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
                        Toast.makeText(context,"No Internet Connection",Toast.LENGTH_SHORT).show();
                        Log.i("error","an error occurred");
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    public void getTvShowRecommendations(String id, final TvShow tvShow)
    {
        String URL = context.getString(R.string.Base_URL) + "tv/"+id+"/recommendations?api_key=" + context.getString(R.string.Key);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    JSONArray arr = response.getJSONArray("results");
                    ArrayList<TvShow> Recommendations = tvShow.getRecommendations();
                    for (int i = 0; i < arr.length(); i++)
                    {
                        TvShow tvShow= new TvShow();

                        JSONObject item = arr.getJSONObject(i);

                        tvShow.setID(item.get("id").toString());
                        tvShow.setTitle(item.get("name").toString());
                        tvShow.setYear(item.get("first_air_date").toString());
                        tvShow.setImage(context.getString(R.string.Image_URL)+context.getString(R.string.Image_size)+item.get("poster_path").toString());

                        Recommendations.add(tvShow);
                    }

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
                        Toast.makeText(context,"No Internet Connection",Toast.LENGTH_SHORT).show();
                        Log.i("error","an error occurred");
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    public void getMovieCredits(String id, final Movie movie)
    {
        String URL = context.getString(R.string.Base_URL) + "movie/"+id+"/credits?api_key=" + context.getString(R.string.Key);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    JSONArray arr = response.getJSONArray("cast");
                    ArrayList<Person> CastAndCrew = movie.getCastAndCrew();

                    for (int i = 0; i < arr.length(); i++)
                    {
                        Person person = new Person();

                        JSONObject item = arr.getJSONObject(i);

                        person.setName(item.get("name").toString());
                        person.setRole(item.get("character").toString());
                        person.setImage(context.getString(R.string.Image_URL)+context.getString(R.string.Image_size)+item.get("profile_path").toString());

                        CastAndCrew.add(person);
                    }
                    JSONArray arr2 = response.getJSONArray("crew");

                    ArrayList<String> directors=new ArrayList<>();
                    ArrayList<String> writers=new ArrayList<>();
                    for (int i = 0; i < arr2.length(); i++)
                    {
                        JSONObject item = arr2.getJSONObject(i);

                        if(item.get("job").toString().equals("Director"))
                            directors.add(item.get("name").toString());
                        else if (item.get("job").toString().equals("Writer") || item.get("job").toString().equals("Screenplay"))
                            writers.add(item.get("name").toString());
                    }
                    movie.setDirectors(directors);
                    movie.setWriters(writers);

                    ((MovieActivity)context).onLoad();

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
                        Toast.makeText(context,"No Internet Connection",Toast.LENGTH_SHORT).show();
                        Log.i("error","an error occurred");
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    public void getTvShowCredits(String id, final TvShow tvShow)
    {
        String URL = context.getString(R.string.Base_URL) + "tv/"+id+"/credits?api_key=" + context.getString(R.string.Key);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    JSONArray arr = response.getJSONArray("cast");
                    ArrayList<Person> CastAndCrew = tvShow.getCastAndCrew();

                    for (int i = 0; i < arr.length(); i++)
                    {
                        Person person = new Person();

                        JSONObject item = arr.getJSONObject(i);

                        person.setName(item.get("name").toString());
                        person.setRole(item.get("character").toString());
                        person.setImage(context.getString(R.string.Image_URL)+context.getString(R.string.Image_size)+item.get("profile_path").toString());

                        CastAndCrew.add(person);
                    }

                    ((TvShowActivity)context).onLoad();

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
                        Toast.makeText(context,"No Internet Connection",Toast.LENGTH_SHORT).show();
                        Log.i("error","an error occurred");
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
    public void Search(String query, final List moviesList, final List tvShowsList)
    {
        String URL = context.getString(R.string.Base_URL) + "search/multi?api_key=" + context.getString(R.string.Key)+"&query="+query;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    JSONArray arr = response.getJSONArray("results");
                    moviesList.clear();
                    tvShowsList.clear();
                    int results = arr.length()<20?arr.length():20;
                    for (int i = 0; i < results; i++)
                    {
                        JSONObject item = arr.getJSONObject(i);
                        if(item.get("media_type").equals("movie"))
                        {
                            Movie movie = new Movie();

                            movie.setID(item.get("id").toString());
                            movie.setTitle(item.get("title").toString());
                            movie.setYear(item.get("release_date").toString());
                            movie.setImage(context.getString(R.string.Image_URL)+context.getString(R.string.Image_size)+item.get("poster_path").toString());

                            moviesList.add(movie);
                        }
                        else if(item.get("media_type").equals("tv"))
                        {
                            TvShow tvShow= new TvShow();

                            tvShow.setID(item.get("id").toString());
                            tvShow.setTitle(item.get("name").toString());
                            tvShow.setYear(item.get("first_air_date").toString());
                            tvShow.setImage(context.getString(R.string.Image_URL)+context.getString(R.string.Image_size)+item.get("poster_path").toString());

                            tvShowsList.add(tvShow);
                        }
                    }

                    ((SearchResultsActivity)context).onLoad();
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
                        Toast.makeText(context,"No Internet Connection",Toast.LENGTH_SHORT).show();
                        Log.i("error","an error occurred");
                    }
                });
        requestQueue.add(jsonObjectRequest);

    }
}
