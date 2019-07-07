package com.example.user.moviesapi;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.moviesapi.Adapter.MovieAdapter;
import com.example.user.moviesapi.Model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MoviesFragment extends Fragment {
    private List<Movie> movieList;
    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;
    private Button prev,next;
    private TextView pageTextView;
    private VolleyManager volleyManager;
    private int page;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_view, container, false);

        prev = rootView.findViewById(R.id.previous_button);
        next = rootView.findViewById(R.id.next_button);
        pageTextView = rootView.findViewById(R.id.page_textView);
        page=1;

        recyclerView = rootView.findViewById(R.id.recyclerView);
        movieList = new ArrayList<>();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false));
        else
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false));

        movieAdapter=new MovieAdapter(getActivity(),movieList);
        recyclerView.setAdapter(movieAdapter);

        volleyManager= new VolleyManager(getActivity());

        if(savedInstanceState != null)
            page = savedInstanceState.getInt("page");

        setPage();

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page--;
                setPage();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page++;
                setPage();
            }
        });
        return rootView;
    }

    private void setPage()
    {

        if(page==1)
            prev.setVisibility(View.INVISIBLE);
        else
            prev.setVisibility(View.VISIBLE);
        if(page==Movie.totalPages)
            next.setVisibility(View.INVISIBLE);
        else
            next.setVisibility(View.VISIBLE);

        volleyManager.getPopularMovies(page, movieList, movieAdapter);
        pageTextView.setText(Integer.toString(page));
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("page",page);
    }


}
