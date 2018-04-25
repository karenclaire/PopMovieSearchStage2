package com.example.android.popmoviesearchstage2.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Karen Claire Ulmer on 11 April 2018.
 */

public class MovieContract {

    private MovieContract(){}

    /**
     * Content authority
     */
    public static final String CONTENT_AUTHORITY = "com.example.android.popmoviesearchstage2";

    /**
     * Base of all URI's which apps will use to contact the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     */
    public static final String PATH_MOVIE = "movies";
    public static final String PATH_TOP_RATED = "top_rated";
    public static final String PATH_POPULAR = "popular";
    public static final String PATH_FAVORITE = "favorite";

    /**
     * Inner class that defines constant values for the movie database table.
     * Each entry in the table represents a single movie detail
     */

    public static final class PopularMovieEntry implements BaseColumns {

        /** The content URI to access the movie data in the provider */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_POPULAR).build();

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of popular movies.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POPULAR;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single movie.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POPULAR + "/" + PATH_MOVIE;

        public static final String TABLE_NAME = "movie";

        public static final String _ID = BaseColumns._ID;

        //Movie ID, STRING, NOT NULL
        public static final String COLUMN_MOVIE_ID = "movie_id";

        //Movie Title, STRING, NOT NULL
        public static final String COLUMN_TITLE= "original_title";

        //Movie Rating, FLOAT NOT NULL
        public static final String COLUMN_VOTE_AVERAGE= "vote_average";

        //Movie Release Date, STRING NOT NULL
        public static final String COLUMN_RELEASE_DATE ="release_date";

        //Movie Overview, STRING, NOT NULL
        public static final String COLUMN_OVERVIEW ="overview";

        //Movie Poster, STRING, NOT NULL
        public static final String COLUMN_POSTER_PATH ="poster_path";

        // Movie TRAILER NAME, TEXT
        public static final String COLUMN_TRAILER1_NAME= "trailer1_name";
        public static final String COLUMN_TRAILER2_NAME= "trailer2_name";
        public static final String COLUMN_TRAILER3_NAME= "trailer3_name";

        //Movie TRAILER SOURCE, TEXT
        public static final String COLUMN_TRAILER1= "trailer1";
        public static final String COLUMN_TRAILER2= "trailer2";
        public static final String COLUMN_TRAILER3= "trailer3";

        //Movie in Favorite List, BOOLEAN, NOT NULL, NOT DEFAULT
        public static final String COLUMN_FAVORITE_MOVIE = "favorite";

        ///Movie Reviews Author, STRING
        public static final String COLUMN_REVIEW_AUTHOR = "author1";
        public static final String COLUMN_REVIEW2_AUTHOR = "author2";
        public static final String COLUMN_REVIEW3_AUTHOR = "author3";

        //Movie Reviews STRING
        public static final String COLUMN_REVIEW_CONTENT = "content1";
        public static final String COLUMN_REVIEW_CONTENT2 = "content2";
        public static final String COLUMN_REVIEW_CONTENT3 = "content3";

        //Movie Settings Preference, STRING, NOT NULL
        public static final String COLUMN_PREFERENCE = "PREFERENCE";

        public static Uri buildMovieDetailsUri(long id) {
            return ContentUris.withAppendedId(buildMovieDetails(), id);
        }

        public static Uri buildPopularMoviesUri() {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_POPULAR)
                    .build();
        }

        public static Uri buildMovieDetails() {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_POPULAR)
                    .appendPath(PATH_MOVIE)
                    .build();
        }

        public static String getMovieFromUri(Uri uri) {
            return uri.getPathSegments().get(0);
        }
        public static String getMovieIDFromUri(Uri uri) {
            return uri.getPathSegments().get(2);
        }

    }

    public static final class TopRatedMovieEntry implements BaseColumns {

        public static final String TABLE_NAME = "top_rated";

        /** The content URI to access the movie data in the provider */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TOP_RATED).build();

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of top_rated movies.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TOP_RATED;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single movie.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TOP_RATED + "/" + PATH_MOVIE;

        public static final String _ID = BaseColumns._ID;

        //Movie ID, STRING, NOT NULL
        public static final String COLUMN_MOVIE_ID = "movie_id";

        //Movie Title, STRING, NOT NULL
        public static final String COLUMN_TITLE= "original_title";

        //Movie Rating, FLOAT NOT NULL
        public static final String COLUMN_VOTE_AVERAGE= "vote_average";

        //Movie Release Date, STRING NOT NULL
        public static final String COLUMN_RELEASE_DATE ="release_date";

        //Movie Overview, STRING, NOT NULL
        public static final String COLUMN_OVERVIEW ="overview";

        //Movie Poster, STRING, NOT NULL
        public static final String COLUMN_POSTER_PATH ="poster_path";

        // Movie TRAILER NAME, TEXT
        public static final String COLUMN_TRAILER1_NAME= "trailer1_name";
        public static final String COLUMN_TRAILER2_NAME= "trailer2_name";
        public static final String COLUMN_TRAILER3_NAME= "trailer3_name";

        //Movie TRAILER SOURCE, TEXT
        public static final String COLUMN_TRAILER1= "trailer1";
        public static final String COLUMN_TRAILER2= "trailer2";
        public static final String COLUMN_TRAILER3= "trailer3";

        //Movie in Favorite List, BOOLEAN, NOT NULL, NOT DEFAULT
        public static final String COLUMN_FAVORITE_MOVIE = "favorite";

        //Movie Reviews Author, STRING
        public static final String COLUMN_REVIEW_AUTHOR = "author1";
        public static final String COLUMN_REVIEW2_AUTHOR = "author2";
        public static final String COLUMN_REVIEW3_AUTHOR = "author3";

        //Movie Reviews STRING
        public static final String COLUMN_REVIEW_CONTENT = "content1";
        public static final String COLUMN_REVIEW_CONTENT2 = "content2";
        public static final String COLUMN_REVIEW_CONTENT3 = "content3";

        //Movie Settings Preference, STRING, NOT NULL
        public static final String COLUMN_PREFERENCE = "PREFERENCE";

        public static final String COLUMN_POPULAR_INDEX = "popular_index";
        public static final String COLUMN_TOP_RATED_INDEX = "top_rated_index";



        public static Uri buildMovieDetailsUri(long id) {
            return ContentUris.withAppendedId(buildMovieDetails(), id);
        }

        public static Uri buildTopRatedMoviesUri() {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_TOP_RATED)
                    .build();
        }

        public static Uri buildMovieDetails() {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_TOP_RATED)
                    .appendPath(PATH_MOVIE)
                    .build();
        }

        public static Uri buildMovieDetails(String movie_id) {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_MOVIE)
                    .appendPath(movie_id)
                    .build();
        }
        public static String getMovieIDFromUri(Uri uri) {
            return uri.getPathSegments().get(2);
        }
    }

    public static final class FavoriteMovieEntry implements BaseColumns {

        public static final String TABLE_NAME = "favorite";

        /** The content URI to access the movie data in the provider */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE).build();

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of top_rated movies.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single movie.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE + "/" + PATH_MOVIE;

        public static final String _ID = BaseColumns._ID;

        //Movie ID, STRING, NOT NULL
        public static final String COLUMN_MOVIE_ID = "movie_id";

        //Movie Title, STRING, NOT NULL
        public static final String COLUMN_TITLE= "original_title";

        //Movie Rating, FLOAT NOT NULL
        public static final String COLUMN_VOTE_AVERAGE= "vote_average";

        //Movie Release Date, STRING NOT NULL
        public static final String COLUMN_RELEASE_DATE ="release_date";

        //Movie Overview, STRING, NOT NULL
        public static final String COLUMN_OVERVIEW ="overview";

        //Movie Poster, STRING, NOT NULL
        public static final String COLUMN_POSTER_PATH ="poster_path";

        // Movie TRAILER NAME, TEXT
        public static final String COLUMN_TRAILER1_NAME= "trailer1_name";
        public static final String COLUMN_TRAILER2_NAME= "trailer2_name";
        public static final String COLUMN_TRAILER3_NAME= "trailer3_name";

        //Movie TRAILER SOURCE, TEXT
        public static final String COLUMN_TRAILER1= "trailer1";
        public static final String COLUMN_TRAILER2= "trailer2";
        public static final String COLUMN_TRAILER3= "trailer3";



        //Movie in Favorite List, BOOLEAN, NOT NULL, NOT DEFAULT
        public static final String COLUMN_FAVORITE_MOVIE = "favorite";

        //Movie Reviews Author, STRING
        public static final String COLUMN_REVIEW_AUTHOR = "author1";
        public static final String COLUMN_REVIEW2_AUTHOR = "author2";
        public static final String COLUMN_REVIEW3_AUTHOR = "author3";

        //Movie Reviews STRING
        public static final String COLUMN_REVIEW_CONTENT = "content1";
        public static final String COLUMN_REVIEW_CONTENT2 = "content2";
        public static final String COLUMN_REVIEW_CONTENT3 = "content3";

        //Movie Settings Preference, STRING, NOT NULL
        public static final String COLUMN_PREFERENCE = "PREFERENCE";

        public static final String COLUMN_POPULAR_INDEX = "popular_index";
        public static final String COLUMN_TOP_RATED_INDEX = "top_rated_index";

        public static Uri buildMovieDetailsUri(long id) {
            return ContentUris.withAppendedId(buildMovieDetails(), id);
        }

        public static Uri buildFavoriteMoviesUri() {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_FAVORITE)
                    .build();
        }

        public static Uri buildMovieFavoritesWithID(String movie_id) {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_FAVORITE)
                    .appendPath(movie_id)
                    .build();
        }

        public static Uri buildMovieDetails() {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_FAVORITE)
                    .appendPath(PATH_MOVIE)
                    .build();
        }

        public static String getMovieIDFromUri(Uri uri) {
            return uri.getPathSegments().get(2);
        }
    }
}
