package com.example.user.moviesapi.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.user.moviesapi.Model.Movie;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    private static String name = "favorite.db";
    private static int version = 1;
    SQLiteDatabase favoriteDB;

    public DbHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SqlCreateQuery = "Create Table " + Contract.Table_Name + " (" + Contract.Column_MovieId + " Integer primary key, " +
                                                                                Contract.Column_MovieTitle + " Text Not Null, " +
                                                                                Contract.Column_MovieYear + " Text Not Null, " +
                                                                                Contract.Column_MovieImage + " Text Not Null" + ");";
        db.execSQL(SqlCreateQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SqlDropQuery = "Drop Table if Exists " + Contract.Table_Name;
        db.execSQL(SqlDropQuery);
        onCreate(db);
    }

    public ArrayList<Movie> getMovies()
    {
        ArrayList<Movie> favorite = new ArrayList<>();
        favoriteDB = getReadableDatabase();
        Cursor cursor = favoriteDB.query(Contract.Table_Name, null,null,null,null,null, null);
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
        favoriteDB.insert(Contract.Table_Name, null, c);

        favoriteDB.close();
    }
    public void deleteMovie(Movie movie)
    {
        favoriteDB = getWritableDatabase();

        String SqlDeleteQuery = "Delete From " + Contract.Table_Name + " Where " + Contract.Column_MovieId + " = " + movie.getID();
        favoriteDB.execSQL(SqlDeleteQuery);

        favoriteDB.close();
    }

    public boolean search(Movie movie)
    {
        favoriteDB = getReadableDatabase();

        boolean found = false;
        Cursor cursor = favoriteDB.query(Contract.Table_Name, null,null,null,null,null, null);
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
}
