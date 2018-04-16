package com.example.user.moviesapi.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.moviesapi.Model.Movie;
import com.example.user.moviesapi.R;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{
    Context context;
    List<Movie> movieList;
    public MovieAdapter(Context context, List<Movie> movieList)
    {
        this.context=context;
        this.movieList=movieList;
    }
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.movie_item,parent,false);
        MovieViewHolder holder = new MovieViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie current = movieList.get(position);
        holder.movieTitle.setText(current.getTitle());
        holder.movieYear.setText(current.getYear());
        Glide.with(context).load(current.getImage()).override(500,500).into(holder.movieImage);
        //Picasso.with(context).load(current.getImage()).into(holder.movieImage);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitle;
        TextView movieYear;
        ImageView movieImage;
        public MovieViewHolder(View itemView) {
            super(itemView);
            movieTitle=itemView.findViewById(R.id.title_textview);
            movieYear=itemView.findViewById(R.id.year_textview);
            movieImage=itemView.findViewById(R.id.image_imageview);
        }
    }
}
