package com.example.user.moviesapi.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.user.moviesapi.Model.Movie;
import com.example.user.moviesapi.Model.TvShow;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    private static String name = "favorite.db";
    private static int version = 1;
    private SQLiteDatabase favoriteDB;

    public DbHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SqlMoviesCreateQuery = "Create Table " + Contract.Movies_Table_Name + " (" + Contract.Column_MovieId + " Integer primary key, " +
                                                                                Contract.Column_MovieTitle + " Text Not Null, " +
                                                                                Contract.Column_MovieYear + " Text Not Null, " +
                                                                                Contract.Column_MovieImage + " Text Not Null" + ");";
        String SqlTvShowsCreateQuery = "Create Table " + Contract.TvShows_Table_Name + " (" + Contract.Column_TvShowId + " Integer primary key, " +
                Contract.Column_TvShowTitle + " Text Not Null, " +
                Contract.Column_TvShowYear + " Text Not Null, " +
                Contract.Column_TvShowImage + " Text Not Null" + ");";

        db.execSQL(SqlMoviesCreateQuery);
        db.execSQL(SqlTvShowsCreateQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SqlMoviesDropQuery = "Drop Table if Exists " + Contract.Movies_Table_Name;
        db.execSQL(SqlMoviesDropQuery);
        String SqlTvShowsDropQuery = "Drop Table if Exists " + Contract.TvShows_Table_Name;
        db.execSQL(SqlTvShowsDropQuery);
        onCreate(db);
    }

    public ArrayList<Movie> getMovies()
    {
        ArrayList<Movie> favorite = new ArrayList<>();
        favoriteDB = getReadableDatabase();
        Cursor cursor = favoriteDB.query(Contract.Movies_Table_Name, null,null,null,null,null, null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                Movie movie = new Movie();

                movie.setID(cursor.getString(cursor.getColumnIndex(Contract.Column_MovieId)));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(Contract.Column_MovieTitle)));
                movie.setYear(cursor.getString(cursor.getColumnIndex(Contract.Column_MovieYear)));
                movie.setImage(cursor.getString(cursor.getColumnIndex(Contract.Column_MovieImage)));

                favorite.add(movie);

                cursor.moveToNext();
            }
            cursor.close();
            favoriteDB.close();
        }
        return favorite;
    }
    public void addMovie(Movie movie)
    {
        favoriteDB = getWritableDatabase();

        ContentValues c = new ContentValues();
        c.put(Contract.Column_MovieId, movie.getID());
        c.put(Contract.Column_MovieTitle, movie.getTitle());
        c.put(Contract.Column_MovieYear, movie.getYear());
        c.put(Contract.Column_MovieImage, movie.getImage());
        favoriteDB.insert(Contract.Movies_Table_Name, null, c);

        favoriteDB.close();
    }
    public void deleteMovie(Movie movie)
    {
        favoriteDB = getWritableDatabase();

        String SqlDeleteQuery = "Delete From " + Contract.Movies_Table_Name + " Where " + Contract.Column_MovieId + " = " + movie.getID();
        favoriteDB.execSQL(SqlDeleteQuery);

        favoriteDB.close();
    }

    public boolean search(Movie movie)
    {
        favoriteDB = getReadableDatabase();

        boolean found = false;
        Cursor cursor = favoriteDB.query(Contract.Movies_Table_Name, null,null,null,null,null, null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                if(movie.getID().equals(cursor.getString(cursor.getColumnIndex(Contract.Column_MovieId))))
                {
                    found = true;
                    break;
                }
                cursor.moveToNext();
            }
            cursor.close();
            favoriteDB.close();
        }
        return found;
    }

    public ArrayList<TvShow> getTvShows()
    {
        ArrayList<TvShow> favorite = new ArrayList<>();
        favoriteDB = getReadableDatabase();
        Cursor cursor = favoriteDB.query(Contract.TvShows_Table_Name, null,null,null,null,null, null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                TvShow tvShow = new TvShow();

                tvShow.setID(cursor.getString(cursor.getColumnIndex(Contract.Column_TvShowId)));
                tvShow.setTitle(cursor.getString(cursor.getColumnIndex(Contract.Column_TvShowTitle)));
                tvShow.setYear(cursor.getString(cursor.getColumnIndex(Contract.Column_TvShowYear)));
                tvShow.setImage(cursor.getString(cursor.getColumnIndex(Contract.Column_TvShowImage)));

                favorite.add(tvShow);

                cursor.moveToNext();
            }
            cursor.close();
            favoriteDB.close();
        }
        return favorite;
    }
    public void addTvShow(TvShow tvShow)
    {
        favoriteDB = getWritableDatabase();

        ContentValues c = new ContentValues();
        c.put(Contract.Column_TvShowId, tvShow.getID());
        c.put(Contract.Column_TvShowTitle, tvShow.getTitle());
        c.put(Contract.Column_TvShowYear, tvShow.getYear());
        c.put(Contract.Column_TvShowImage, tvShow.getImage());
        favoriteDB.insert(Contract.TvShows_Table_Name, null, c);

        favoriteDB.close();
    }
    public void deleteTvShow(TvShow tvShow)
    {
        favoriteDB = getWritableDatabase();

        String SqlDeleteQuery = "Delete From " + Contract.TvShows_Table_Name + " Where " + Contract.Column_TvShowId + " = " + tvShow.getID();
        favoriteDB.execSQL(SqlDeleteQuery);

        favoriteDB.close();
    }

    public boolean search(TvShow tvShow)
    {
        favoriteDB = getReadableDatabase();

        boolean found = false;
        Cursor cursor = favoriteDB.query(Contract.TvShows_Table_Name, null,null,null,null,null, null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                if(tvShow.getID().equals(cursor.getString(cursor.getColumnIndex(Contract.Column_TvShowId))))
                {
                    found = true;
                    break;
                }
                cursor.moveToNext();
            }
            cursor.close();
            favoriteDB.close();
        }
        return found;
    }
}
