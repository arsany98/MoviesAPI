package com.example.user.moviesapi;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.moviesapi.Adapter.MovieAdapter;
import com.example.user.moviesapi.Adapter.PersonAdapter;
import com.example.user.moviesapi.Model.Movie;
import com.example.user.moviesapi.Model.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VolleyManager {
    private Context context;
    private List<Movie> movieList;
    private MovieAdapter movieAdapter;
    private PersonAdapter personAdapter;
    private Movie movieDetails;

    public VolleyManager(Context c, List<Movie>movies , MovieAdapter adapter)
    {
        context=c;
        movieList=movies;
        movieAdapter=adapter;
    }
    public VolleyManager(Context c, Movie movie, PersonAdapter pAdapter,MovieAdapter mAdapter)
    {
        context = c;
        movieDetails=movie;
        personAdapter = pAdapter;
        movieAdapter = mAdapter;
    }
    public void getPopularMovies(int page)
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
                    movieList.clear();
                    for (int i = 0; i < arr.length(); i++)
                    {
                        Movie movie = new Movie();

                        JSONObject item = arr.getJSONObject(i);

                        movie.setID(item.get("id").toString());
                        movie.setTitle(item.get("title").toString());
                        movie.setYear(item.get("release_date").toString());
                        movie.setImage(context.getString(R.string.Image_URL)+context.getString(R.string.Image_size)+item.get("poster_path").toString());

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
                        Toast.makeText(context,"No Internet Connection",Toast.LENGTH_SHORT).show();
                        Log.i("error","an error occurred");
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    public void getDetails(final String id)
    {
        String URL = context.getString(R.string.Base_URL) + "movie/" + id + "?api_key=" + context.getString(R.string.Key);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    movieDetails.setOverview(response.get("overview").toString());
                    movieDetails.setImage(context.getString(R.string.Image_URL)+context.getString(R.string.Image_size)+response.get("poster_path").toString());
                    movieDetails.setTitle(response.get("title").toString());
                    movieDetails.setYear(response.get("release_date").toString());
                    movieDetails.setRating(Float.parseFloat(response.get("vote_average").toString()));
                    movieDetails.setVoteCount(response.getInt("vote_count"));
                    movieDetails.setBudget(response.getDouble("budget"));
                    movieDetails.setRevenue(response.getDouble("revenue"));

                    JSONArray arr = response.getJSONArray("genres");
                    ArrayList<String> genres=new ArrayList<>();
                    for(int i = 0 ; i < arr.length() ; i++)
                    {
                        JSONObject genre = arr.getJSONObject(i);
                        genres.add(genre.get("name").toString());
                    }
                    movieDetails.setGenres(genres);
                    movieDetails.setRuntime(response.getInt("runtime"));
                    getVideos(id);
                    getRecommendations(id);
                    getCredits(id);
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

    public void getVideos(String id)
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
                            movieDetails.setTrailer(video.get("key").toString());
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

    public void getRecommendations(String id)
    {
        String URL = context.getString(R.string.Base_URL) + "movie/"+id+"/recommendations?api_key=" + context.getString(R.string.Key);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    JSONArray arr = response.getJSONArray("results");
                    movieDetails.Recommendations.clear();
                    for (int i = 0; i < arr.length(); i++)
                    {
                        Movie movie = new Movie();

                        JSONObject item = arr.getJSONObject(i);

                        movie.setID(item.get("id").toString());
                        movie.setTitle(item.get("title").toString());
                        movie.setYear(item.get("release_date").toString());
                        movie.setImage(context.getString(R.string.Image_URL)+context.getString(R.string.Image_size)+item.get("poster_path").toString());

                        movieDetails.Recommendations.add(movie);
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
                        Toast.makeText(context,"No Internet Connection",Toast.LENGTH_SHORT).show();
                        Log.i("error","an error occurred");
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    public void getCredits(String id)
    {
        String URL = context.getString(R.string.Base_URL) + "movie/"+id+"/credits?api_key=" + context.getString(R.string.Key);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    JSONArray arr = response.getJSONArray("cast");
                    movieDetails.CastAndCrew.clear();

                    for (int i = 0; i < arr.length(); i++)
                    {
                        Person person = new Person();

                        JSONObject item = arr.getJSONObject(i);

                        person.setName(item.get("name").toString());
                        person.setRole(item.get("character").toString());
                        person.setImage(context.getString(R.string.Image_URL)+context.getString(R.string.Image_size)+item.get("profile_path").toString());

                        movieDetails.CastAndCrew.add(person);
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
                    movieDetails.setDirectors(directors);
                    movieDetails.setWriters(writers);

                    ((MovieActivity)context).onLoad();
                    personAdapter.notifyDataSetChanged();
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

    public void Search(String query)
    {
        String URL = context.getString(R.string.Base_URL) + "search/movie?api_key=" + context.getString(R.string.Key)+"&query="+query;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    JSONArray arr = response.getJSONArray("results");
                    movieList.clear();
                    int results = arr.length()<20?arr.length():20;
                    for (int i = 0; i < results; i++)
                    {
                        Movie movie = new Movie();

                        JSONObject item = arr.getJSONObject(i);

                        movie.setID(item.get("id").toString());
                        movie.setTitle(item.get("title").toString());
                        movie.setYear(item.get("release_date").toString());
                        movie.setImage(context.getString(R.string.Image_URL)+context.getString(R.string.Image_size)+item.get("poster_path").toString());

                        movieList.add(movie);
                    }
                    movieAdapter.notifyDataSetChanged();

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
