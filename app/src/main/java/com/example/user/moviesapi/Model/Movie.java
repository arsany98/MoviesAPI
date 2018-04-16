package com.example.user.moviesapi.Model;

import android.widget.ImageView;
import android.widget.TextView;

public class Movie {
    private String Title;
    private String Year;
    private String Image;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
