package com.example.user.moviesapi.Model;

import java.util.ArrayList;

public class TvShow {
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
    private ArrayList<String> Creators;
    private ArrayList<Person> CastAndCrew = new ArrayList<>();
    private ArrayList<TvShow> Recommendations = new ArrayList<>();
    private boolean Favorite;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public String getOverview() {
        return Overview;
    }

    public void setOverview(String overview) {
        Overview = overview;
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

    public ArrayList<String> getCreators() {
        return Creators;
    }

    public void setCreators(ArrayList<String> creators) {
        Creators = creators;
    }


    public ArrayList<Person> getCastAndCrew() {
        return CastAndCrew;
    }

    public void setCastAndCrew(ArrayList<Person> castAndCrew) {
        CastAndCrew = castAndCrew;
    }

    public ArrayList<TvShow> getRecommendations() {
        return Recommendations;
    }

    public void setRecommendations(ArrayList<TvShow> recommendations) {
        Recommendations = recommendations;
    }

    public boolean isFavorite() {
        return Favorite;
    }

    public void setFavorite(boolean favorite) {
        Favorite = favorite;
    }
}
