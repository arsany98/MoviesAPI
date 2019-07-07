package com.example.user.moviesapi;

import android.support.v4.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.moviesapi.Adapter.TvShowAdapter;
import com.example.user.moviesapi.Model.TvShow;

import java.util.ArrayList;
import java.util.List;

public class TvShowsFragment extends Fragment {
    private List<TvShow> tvShows;
    private TvShowAdapter tvShowAdapter;
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
        tvShows = new ArrayList<>();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false));
        else
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false));

        tvShowAdapter = new TvShowAdapter(getActivity(),tvShows);
        recyclerView.setAdapter(tvShowAdapter);

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
        if(page== TvShow.totalPages)
            next.setVisibility(View.INVISIBLE);
        else
            next.setVisibility(View.VISIBLE);

        volleyManager.getPopularTvShows(page, tvShows, tvShowAdapter);
        pageTextView.setText(Integer.toString(page));
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("page", page);
    }

}
