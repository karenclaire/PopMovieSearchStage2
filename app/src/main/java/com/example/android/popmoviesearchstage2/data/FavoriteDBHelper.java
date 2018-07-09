package com.example.android.popmoviesearchstage2.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.popmoviesearchstage2.data.FavoriteContract.FavoriteMovieEntry;
import com.example.android.popmoviesearchstage2.model.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FavoriteDBHelper extends SQLiteOpenHelper {

    final String LOG_TAG = FavoriteDBHelper.class.getSimpleName ();
    public static final String DEBUG_TAG = "DebugStuff";

    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "movie.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;


    /**
     * Constructs a new instance of {@link FavoriteDBHelper}.
     *
     * @param context of the app
     */
    public FavoriteDBHelper(Context context) {
        super ( context, DATABASE_NAME, null, DATABASE_VERSION );
    }


    /**
     * This is called when the database is created for the first time.
     */

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + FavoriteMovieEntry.TABLE_NAME + " ("
                + FavoriteMovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FavoriteMovieEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL, "
                + FavoriteMovieEntry.COLUMN_TITLE + " TEXT NOT NULL, "
                + FavoriteMovieEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL DEFAULT 0.0, "
                + FavoriteMovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL ) ;";
                //+ FavoriteMovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, "
                //+ FavoriteMovieEntry.COLUMN_POSTER_PATH + " STRING NOT NULL );";

        // Execute the SQL statement
        db.execSQL ( SQL_CREATE_FAVORITE_TABLE );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL ( "DROP TABLE IF EXISTS " + FavoriteMovieEntry.TABLE_NAME );
        onCreate ( db );
    }

    public void addFavorites(Movie movie) {
        SQLiteDatabase database = this.getWritableDatabase ();

        ContentValues values = new ContentValues ();
        values.put ( FavoriteMovieEntry.COLUMN_MOVIE_ID, movie.getId () );
        values.put ( FavoriteMovieEntry.COLUMN_TITLE, movie.getTitle () );
        values.put ( FavoriteMovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage () );
        values.put ( FavoriteMovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate () );
        //values.put ( FavoriteMovieEntry.COLUMN_OVERVIEW, movie.getOverview () );
        //values.put ( FavoriteMovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath () );

        database.insert ( FavoriteMovieEntry.TABLE_NAME, null, values );
        database.close ();

    }

    public void deleteFavorite(int id) {
        SQLiteDatabase database = this.getWritableDatabase ();
        database.delete ( FavoriteMovieEntry.TABLE_NAME,
                FavoriteMovieEntry.COLUMN_MOVIE_ID + "=" + id, null );
    }

    public List<Movie> getAllFavoriteMovies() {
        String movieColumns[] = {FavoriteMovieEntry._ID,
                FavoriteMovieEntry.COLUMN_MOVIE_ID,
                FavoriteMovieEntry.COLUMN_TITLE,
                FavoriteMovieEntry.COLUMN_VOTE_AVERAGE,
                FavoriteMovieEntry.COLUMN_RELEASE_DATE,
                //FavoriteMovieEntry.COLUMN_OVERVIEW,
                //FavoriteMovieEntry.COLUMN_POSTER_PATH
        };

        String sortOrder = FavoriteMovieEntry._ID + "ASC";
        List<Movie> favoriteList = new ArrayList<> ();

        SQLiteDatabase database = this.getReadableDatabase ();

        Cursor cursor = database.query ( FavoriteMovieEntry.TABLE_NAME, movieColumns,
                null,
                null,
                null,
                null,
                sortOrder );

        if (cursor.moveToFirst ()) {
            do {
                Movie movie = new Movie ();
                movie.setId ( Integer.parseInt ( cursor.getString ( cursor.getColumnIndex ( FavoriteMovieEntry.COLUMN_MOVIE_ID ) ) ) );
                movie.setTitle ( cursor.getString ( cursor.getColumnIndex ( FavoriteMovieEntry.COLUMN_TITLE ) ) );
                movie.setVoteAverage ( cursor.getDouble ( cursor.getColumnIndex ( FavoriteMovieEntry.COLUMN_VOTE_AVERAGE ) ) ) ;
                movie.setReleaseDate ( cursor.getString ( cursor.getColumnIndex ( FavoriteMovieEntry.COLUMN_RELEASE_DATE ) ) );
                //movie.setOverview ( cursor.getString ( cursor.getColumnIndex ( FavoriteMovieEntry.COLUMN_OVERVIEW ) ) );
                //movie.setPosterPath ( cursor.getString ( cursor.getColumnIndex ( FavoriteMovieEntry.COLUMN_POSTER_PATH ) ) );

                favoriteList.addAll ( Collections.singleton ( movie ) );
            } while (cursor.moveToNext ());
            cursor.close ();
            database.close ();

        }
        return favoriteList;

    }
}
