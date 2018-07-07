package com.example.user.moviesapi.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.moviesapi.Model.Person;
import com.example.user.moviesapi.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder>{
    Context context;
    ArrayList<Person> castList;
    public PersonAdapter(Context context,ArrayList<Person> castList)
    {
        this.castList = castList;
        this.context = context;
    }
    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.movie_item,parent,false);
        PersonViewHolder holder = new PersonViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        Person current = castList.get(position);

        holder.name.setText(current.getName());
        holder.role.setText(current.getRole());
        Picasso.with(context).load(current.getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    class PersonViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        TextView role;
        ImageView image;

        public PersonViewHolder(View view)
        {
            super(view);
            name = view.findViewById(R.id.title_textview);
            role = view.findViewById(R.id.year_textview);
            image = view.findViewById(R.id.image_imageview);
        }
    }
}
