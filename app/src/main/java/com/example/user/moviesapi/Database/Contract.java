package com.example.user.moviesapi.Database;

import android.provider.BaseColumns;

public class Contract implements BaseColumns{
    public static String Movies_Table_Name = "favoriteMovies";
    public static String Column_MovieId = "movieID";
    public static String Column_MovieTitle = "movieTitle";
    public static String Column_MovieYear = "movieYear";
    public static String Column_MovieImage = "movieImage";

    public static String TvShows_Table_Name = "favoriteTvShows";
    public static String Column_TvShowId = "TvShowID";
    public static String Column_TvShowTitle = "TvShowTitle";
    public static String Column_TvShowYear = "TvShowYear";
    public static String Column_TvShowImage = "TvShowImage";
}
