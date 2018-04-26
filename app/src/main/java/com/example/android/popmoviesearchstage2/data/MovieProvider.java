package com.example.android.popmoviesearchstage2.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.popmoviesearchstage2.data.MovieContract.FavoriteMovieEntry;
import com.example.android.popmoviesearchstage2.data.MovieContract.PopularMovieEntry;
import com.example.android.popmoviesearchstage2.data.MovieContract.TopRatedMovieEntry;


/**
 * References for code correction and guidance
 *  https://github.com/karenclaire/EscrimaInventoryApp
 *  https://www.youtube.com/watch?v=OOLFhtyCspA
 *  https://github.com/FrangSierra/Udacity-Popular-Movies-Stage-2
 *  https://github.com/SubhrajyotiSen/Popular-Movies-2
 *  https://github.com/jimandreas/PopularMovies
 */

public class MovieProvider extends ContentProvider {

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = MovieProvider.class.getSimpleName();

    /**
     * URI matcher code for the content URI for the Pmovie table
     */
    private static final int POPULAR_MOVIE = 100;
    private static final int TOP_RATED_MOVIE = 101;
    private static final int FAVORITE_MOVIE = 103;

    /**
     * URI matcher code for the content URI for a single item in the movie table
     */
    static final int POPULAR_MOVIE_DETAIL = 104;
    static final int TOP_RATED_MOVIE_DETAIL = 105;
    static final int FAVORITE_MOVIE_DETAIL = 106;

    static final int MOVIE_ID = 107;


    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        // The content URI of the form "content://com.example.android.popmoviesearchstage2/will map to the
        // integer code. This URI is used to provide access to MULTIPLE rows of the movie tables.
        sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE, POPULAR_MOVIE);
        sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE, TOP_RATED_MOVIE);
        sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE,FAVORITE_MOVIE);

        // This URI is used to provide access to ONE single row of the chosen movie table.
        // In this case, the "#" wildcard is used where "#" can be substituted for an integer.
        //
        sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_POPULAR + "/#" +
                MovieContract.PATH_MOVIE + "/#", POPULAR_MOVIE_DETAIL);

        sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_TOP_RATED + "/#" +
                MovieContract.PATH_MOVIE + "/#", TOP_RATED_MOVIE_DETAIL);

        sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_FAVORITE + "/#" +
                MovieContract.PATH_MOVIE + "/#", FAVORITE_MOVIE_DETAIL);
    }

    /**
     * Database helper object
     */
    private MovieDBHelper mDbHelper;


    @Override
    public boolean onCreate() {
        mDbHelper = new MovieDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            // For the Movie, query the Movie table directly with the given
            // projection, selection, selection arguments, and sort order. The cursor
            // could contain multiple rows of the chosen movie table.
                case POPULAR_MOVIE: {
                    cursor = database.query(MovieContract.PopularMovieEntry.TABLE_NAME, projection,
                            selection, selectionArgs, null, null, sortOrder);
                    break;
                }
                case TOP_RATED_MOVIE: {
                    cursor = database.query(MovieContract.TopRatedMovieEntry.TABLE_NAME, projection,
                            selection, selectionArgs, null, null, sortOrder);
                    break;
                }
                case FAVORITE_MOVIE: {
                    cursor = database.query(MovieContract.TopRatedMovieEntry.TABLE_NAME, projection,
                            selection, selectionArgs, null, null, sortOrder);
                    break;
                }

                // For the MOVIE_ID code, extract out the ID from the URI.
                // The selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                case POPULAR_MOVIE_DETAIL: {
                    selection = MovieContract.PopularMovieEntry.COLUMN_MOVIE_ID + "=?";
                    selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                    // This will perform a query on the movie table where the _id  will return a
                    // Cursor containing that row of the Popular Movie table.
                    cursor = database.query(MovieContract.PopularMovieEntry.TABLE_NAME, projection, selection, selectionArgs,
                            null, null, sortOrder);
                    break;
                }

                case TOP_RATED_MOVIE_DETAIL: {
                    selection = MovieContract.TopRatedMovieEntry.COLUMN_MOVIE_ID + "=?";
                    selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                    // This will perform a query on the movie table where the _id  will return a
                    // Cursor containing that row of the TopRated Movie table.
                    cursor = database.query(MovieContract.TopRatedMovieEntry.TABLE_NAME, projection, selection, selectionArgs,
                            null, null, sortOrder);
                    break;
                }

                case FAVORITE_MOVIE_DETAIL: {
                    selection = MovieContract.FavoriteMovieEntry.COLUMN_MOVIE_ID + "=?";
                    selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                    // This will perform a query on the movie table where the _id  will return a
                    // Cursor containing that row of the Favorite Movie table.
                    cursor = database.query(MovieContract.FavoriteMovieEntry.TABLE_NAME, projection, selection, selectionArgs,
                            null, null, sortOrder);
                    break;
                }

                default:
                    throw new IllegalArgumentException("Cannot query unknown URI " + uri);
            }
            // Set notification URI on the Cursor,
            // so we know what content URI the Cursor was created for.
            // If the data at this URI changes, then we know we need to update the Cursor.
            cursor.setNotificationUri(getContext().getContentResolver(), uri);

            // Return the cursor
            return cursor;
        }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case POPULAR_MOVIE: {
                return MovieContract.PopularMovieEntry.CONTENT_LIST_TYPE;
            }
            case TOP_RATED_MOVIE: {
                return MovieContract.TopRatedMovieEntry.CONTENT_LIST_TYPE;
            }
            case FAVORITE_MOVIE: {
                return MovieContract.FavoriteMovieEntry.CONTENT_LIST_TYPE;
            }
            case POPULAR_MOVIE_DETAIL:{
                return MovieContract.PopularMovieEntry.CONTENT_ITEM_TYPE;
            }
            case TOP_RATED_MOVIE_DETAIL:{
                return MovieContract.TopRatedMovieEntry.CONTENT_ITEM_TYPE;
            }
            case FAVORITE_MOVIE_DETAIL: {
                return MovieContract.FavoriteMovieEntry.CONTENT_ITEM_TYPE;
            }

            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        // Get writable database
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        Uri insertUri;

        switch (match) {
            case POPULAR_MOVIE: {
                long id = db.insertOrThrow(MovieContract.PopularMovieEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    insertUri = MovieContract.PopularMovieEntry.buildPopularMoviesUri();
                    break;
                } else {
                    throw new IllegalArgumentException("Insertion is not supported for " + uri);
                }
            }
            case TOP_RATED_MOVIE: {
                long id = db.insertOrThrow(MovieContract.TopRatedMovieEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    insertUri = MovieContract.TopRatedMovieEntry.buildTopRatedMoviesUri();
                    break;
                } else {
                    throw new IllegalArgumentException("Insertion is not supported for " + uri);
                }
            }
            case FAVORITE_MOVIE: {
                long id = db.insertOrThrow(MovieContract.FavoriteMovieEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    insertUri = MovieContract.FavoriteMovieEntry.buildFavoriteMoviesUri();
                    break;
                } else {
                    throw new IllegalArgumentException("Insertion is not supported for " + uri);
                }
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        notifyResolver(uri);
        return insertUri;

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        // Get writable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {

            case POPULAR_MOVIE: {
                // Delete all rows that match the Popular Movie selection and selection args
                rowsDeleted = database.delete(PopularMovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }

            case TOP_RATED_MOVIE: {
                // Delete all rows that match the Top Rated Movie selection and selection args
                rowsDeleted = database.delete(TopRatedMovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }

            case FAVORITE_MOVIE: {
                // Delete all rows that match the Favorite Movie selection and selection args
                rowsDeleted = database.delete(FavoriteMovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        notifyResolver(uri);
        return rowsDeleted;

     }



    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        // Get writable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Track the number of rows that were updated
        int rowsUpdated;

        final int match = sUriMatcher.match(uri);
        switch (match) {

            case POPULAR_MOVIE: {
                // Update all rows that match the Popular Movie selection and selection args
                rowsUpdated = database.update(PopularMovieEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }

            case TOP_RATED_MOVIE: {
                // Update all rows that match the Top Rated Movie selection and selection args
                rowsUpdated = database.update(TopRatedMovieEntry.TABLE_NAME,values, selection, selectionArgs);
                break;
            }

            case FAVORITE_MOVIE: {
                // Update all rows that match the Favorite Movie selection and selection args
                rowsUpdated = database.update(FavoriteMovieEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }

            case POPULAR_MOVIE_DETAIL: {
                selection = PopularMovieEntry.COLUMN_MOVIE_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsUpdated = database.update(PopularMovieEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }

            case TOP_RATED_MOVIE_DETAIL: {
                selection = TopRatedMovieEntry.COLUMN_MOVIE_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsUpdated = database.update(TopRatedMovieEntry.TABLE_NAME,values, selection, selectionArgs);
                break;
            }

            case FAVORITE_MOVIE_DETAIL: {
                selection = FavoriteMovieEntry.COLUMN_MOVIE_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsUpdated = database.update(FavoriteMovieEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        notifyResolver(uri);
        return rowsUpdated;
    }


    private void notifyResolver(Uri uri) {
        getContext().getContentResolver().notifyChange(uri, null);
    }
}
