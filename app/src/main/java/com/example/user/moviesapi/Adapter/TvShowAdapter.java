package com.example.user.moviesapi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.moviesapi.Model.Movie;
import com.example.user.moviesapi.Model.TvShow;
import com.example.user.moviesapi.MovieActivity;
import com.example.user.moviesapi.R;
import com.example.user.moviesapi.TvShowActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder> {

    private Context context;
    private List<TvShow> tvShows;

    public TvShowAdapter(Context context, List<TvShow> tvShows)
    {
        this.context = context;
        this.tvShows = tvShows;
    }
    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.movie_item,parent,false);
        TvShowAdapter.TvShowViewHolder holder = new TvShowAdapter.TvShowViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder holder, int position) {
        final TvShow current = tvShows.get(position);
        holder.tvShowTitle.setText(current.getTitle());
        holder.tvShowYear.setText(current.getYear());
        holder.tvShowCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TvShowActivity.class);
                intent.putExtra("ID",current.getID());
                context.startActivity(intent);
            }
        });
        Picasso.with(context).load(current.getImage()).into(holder.tvShowImage);
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder {

        private TextView tvShowTitle;
        private TextView tvShowYear;
        private ImageView tvShowImage;
        private CardView tvShowCard;
        public TvShowViewHolder(View itemView) {
            super(itemView);
            tvShowTitle=itemView.findViewById(R.id.title_textview);
            tvShowYear=itemView.findViewById(R.id.year_textview);
            tvShowImage=itemView.findViewById(R.id.image_imageview);
            tvShowCard = itemView.findViewById(R.id.movie_cardView);
        }
    }
}
