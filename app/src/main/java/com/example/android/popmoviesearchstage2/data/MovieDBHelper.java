package com.example.android.popmoviesearchstage2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.popmoviesearchstage2.data.MovieContract.PopularMovieEntry;
import com.example.android.popmoviesearchstage2.data.MovieContract.TopRatedMovieEntry;
import com.example.android.popmoviesearchstage2.data.MovieContract.FavoriteMovieEntry;


/**
 * References for code correction and guidance
 *  https://github.com/karenclaire/EscrimaInventoryApp
 *  https://www.youtube.com/watch?v=OOLFhtyCspA
 *  https://github.com/FrangSierra/Udacity-Popular-Movies-Stage-2
 *  https://github.com/SubhrajyotiSen/Popular-Movies-2
 *  https://github.com/jimandreas/PopularMovies
 */

public class MovieDBHelper extends SQLiteOpenHelper {


    final String LOG_TAG = MovieDBHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "movie.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link MovieDBHelper}.
     */

    /**
     * Constructs a new instance of {@link MovieDBHelper}.
     * @param context of the app
     */
    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


        /**
         * This is called when the database is created for the first time.
         */

        @Override
        public void onCreate(SQLiteDatabase db){
            // Create a String that contains the SQL statement to create the movie table
            String SQL_CREATE_MOVIE_TABLE =  "CREATE TABLE " + PopularMovieEntry.TABLE_NAME + " ("
                    + PopularMovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + PopularMovieEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL, "
                    + PopularMovieEntry.COLUMN_TITLE + " TEXT NOT NULL, "
                    + PopularMovieEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL DEFAULT 0.0, "
                    + PopularMovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, "
                    + PopularMovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, "
                    + PopularMovieEntry.COLUMN_POSTER_PATH + " STRING NOT NULL, "
                    + PopularMovieEntry.COLUMN_TRAILER1 + " TEXT, "
                    + PopularMovieEntry.COLUMN_TRAILER2 + " TEXT, "
                    + PopularMovieEntry.COLUMN_TRAILER3 + " TEXT, "
                    + PopularMovieEntry.COLUMN_TRAILER1_NAME + " TEXT, "
                    + PopularMovieEntry.COLUMN_TRAILER2_NAME + " TEXT, "
                    + PopularMovieEntry.COLUMN_TRAILER3_NAME + " TEXT, "
                    + PopularMovieEntry.COLUMN_REVIEW_AUTHOR + " TEXT NOT NULL, "
                    + PopularMovieEntry.COLUMN_REVIEW2_AUTHOR + " TEXT NOT NULL, "
                    + PopularMovieEntry.COLUMN_REVIEW3_AUTHOR + " TEXT NOT NULL, "
                    + PopularMovieEntry.COLUMN_REVIEW_CONTENT + " TEXT NOT NULL, "
                    + PopularMovieEntry.COLUMN_REVIEW_CONTENT2 + " TEXT NOT NULL, "
                    + PopularMovieEntry.COLUMN_REVIEW_CONTENT3+ " TEXT NOT NULL, "
                    + PopularMovieEntry.COLUMN_FAVORITE_MOVIE + " INTEGER NOT NULL DEFAULT 0, "
                    + PopularMovieEntry.COLUMN_PREFERENCE + " TEXT NOT NULL );";

            String SQL_CREATE_MOVIE_TABLE2 =  "CREATE TABLE " + TopRatedMovieEntry.TABLE_NAME + " ("
                    + TopRatedMovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TopRatedMovieEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL, "
                    + TopRatedMovieEntry.COLUMN_TITLE + " TEXT NOT NULL, "
                    + TopRatedMovieEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL DEFAULT 0.0, "
                    + TopRatedMovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, "
                    + TopRatedMovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, "
                    + TopRatedMovieEntry.COLUMN_POSTER_PATH + " STRING NOT NULL, "
                    + TopRatedMovieEntry.COLUMN_TRAILER1 + " TEXT, "
                    + TopRatedMovieEntry.COLUMN_TRAILER2 + " TEXT, "
                    + TopRatedMovieEntry.COLUMN_TRAILER3 + " TEXT, "
                    + TopRatedMovieEntry.COLUMN_TRAILER1_NAME + " TEXT, "
                    + TopRatedMovieEntry.COLUMN_TRAILER2_NAME + " TEXT, "
                    + TopRatedMovieEntry.COLUMN_TRAILER3_NAME + " TEXT, "
                    + TopRatedMovieEntry.COLUMN_REVIEW_AUTHOR + " TEXT NOT NULL, "
                    + TopRatedMovieEntry.COLUMN_REVIEW2_AUTHOR + " TEXT NOT NULL, "
                    + TopRatedMovieEntry.COLUMN_REVIEW3_AUTHOR + " TEXT NOT NULL, "
                    + TopRatedMovieEntry.COLUMN_REVIEW_CONTENT + " TEXT NOT NULL, "
                    + TopRatedMovieEntry.COLUMN_REVIEW_CONTENT2 + " TEXT NOT NULL, "
                    + TopRatedMovieEntry.COLUMN_REVIEW_CONTENT3+ " TEXT NOT NULL, "
                    + TopRatedMovieEntry.COLUMN_FAVORITE_MOVIE + " INTEGER NOT NULL DEFAULT 0, "
                    + TopRatedMovieEntry.COLUMN_PREFERENCE + " TEXT NOT NULL );";

            String SQL_CREATE_MOVIE_TABLE3 =  "CREATE TABLE " + FavoriteMovieEntry.TABLE_NAME + " ("
                    + FavoriteMovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + FavoriteMovieEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL, "
                    + FavoriteMovieEntry.COLUMN_TITLE + " TEXT NOT NULL, "
                    + FavoriteMovieEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL DEFAULT 0.0, "
                    + FavoriteMovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, "
                    + FavoriteMovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, "
                    + FavoriteMovieEntry.COLUMN_POSTER_PATH + " STRING NOT NULL, "
                    + FavoriteMovieEntry.COLUMN_TRAILER1 + " TEXT, "
                    + FavoriteMovieEntry.COLUMN_TRAILER2 + " TEXT, "
                    + FavoriteMovieEntry.COLUMN_TRAILER3 + " TEXT, "
                    + FavoriteMovieEntry.COLUMN_TRAILER1_NAME + " TEXT, "
                    + FavoriteMovieEntry.COLUMN_TRAILER2_NAME + " TEXT, "
                    + FavoriteMovieEntry.COLUMN_TRAILER3_NAME + " TEXT, "
                    + FavoriteMovieEntry.COLUMN_REVIEW_AUTHOR + " TEXT NOT NULL, "
                    + FavoriteMovieEntry.COLUMN_REVIEW2_AUTHOR + " TEXT NOT NULL, "
                    + FavoriteMovieEntry.COLUMN_REVIEW3_AUTHOR + " TEXT NOT NULL, "
                    + FavoriteMovieEntry.COLUMN_REVIEW_CONTENT + " TEXT NOT NULL, "
                    + FavoriteMovieEntry.COLUMN_REVIEW_CONTENT2 + " TEXT NOT NULL, "
                    + FavoriteMovieEntry.COLUMN_REVIEW_CONTENT3 + " TEXT NOT NULL, "
                    + FavoriteMovieEntry.COLUMN_FAVORITE_MOVIE + " INTEGER NOT NULL DEFAULT 0, "
                    + TopRatedMovieEntry.COLUMN_PREFERENCE + " TEXT NOT NULL );";

            // Execute the SQL statement
            db.execSQL(SQL_CREATE_MOVIE_TABLE);
            db.execSQL(SQL_CREATE_MOVIE_TABLE2);
            db.execSQL(SQL_CREATE_MOVIE_TABLE3);
        }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PopularMovieEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TopRatedMovieEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteMovieEntry.TABLE_NAME);
        onCreate(db);
    }

}
