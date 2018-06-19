package com.example.android.popmoviesearchstage2.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class FavoriteContract {

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
    public static final String PATH_FAVORITE = "favorite";


    public static final class FavoriteMovieEntry implements BaseColumns{

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


        public static final String TABLE_NAME = "favorite";

        public static final String _ID = BaseColumns._ID;

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

}
}
