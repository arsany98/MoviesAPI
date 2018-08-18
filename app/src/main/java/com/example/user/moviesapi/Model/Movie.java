package com.example.user.moviesapi.Model;

import java.util.ArrayList;

public class Movie {
    public static int totalPages;

    private String ID;
    private String Title;
    private String Year;
    private String Image;
    private String Trailer;
    private String Overview;
    private float Rating;
    private int voteCount;
    private ArrayList<String> Genres;
    private int Runtime;
    private ArrayList<String> Directors;
    private ArrayList<String> Writers;
    private double Budget;
    private double Revenue;
    public ArrayList<Person> CastAndCrew = new ArrayList<>();
    public ArrayList<Movie> Recommendations = new ArrayList<>();
    private boolean Favorite;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getOverview() {
        return Overview;
    }

    public void setOverview(String overview) {
        Overview = overview;
    }

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

    public String getTrailer() {
        return Trailer;
    }

    public void setTrailer(String trailer) {
        Trailer = trailer;
    }

    public float getRating() {
        return Rating;
    }

    public void setRating(float rating) {
        Rating = rating;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public ArrayList<String> getGenres() {
        return Genres;
    }

    public void setGenres(ArrayList<String> genres) {
        Genres = genres;
    }

    public int getRuntime() {
        return Runtime;
    }

    public void setRuntime(int runtime) {
        Runtime = runtime;
    }

    public ArrayList<String> getDirectors() {
        return Directors;
    }

    public void setDirectors(ArrayList<String> directors) {
        Directors = directors;
    }

    public ArrayList<String> getWriters() {
        return Writers;
    }

    public void setWriters(ArrayList<String> writers) {
        Writers = writers;
    }

    public double getBudget() {
        return Budget;
    }

    public void setBudget(double budget) {
        Budget = budget;
    }

    public double getRevenue() {
        return Revenue;
    }

    public void setRevenue(double revenue) {
        Revenue = revenue;
    }

    public boolean isFavorite() {
        return Favorite;
    }

    public void setFavorite(boolean favorite) {
        Favorite = favorite;
    }
}
