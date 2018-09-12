package com.example.android.popmoviesearchstage2.data;

//TODO: 1) Create Favorite Entry
// TODO:2) Create FavoriteDao
//TODO: 3) Create Favorite AppDatabase
//TODO: 3) Create DetailsViewModel that contains an Observer if MovieIsInDb Status (UX) that will
//          trigger change in the UI (must show if it is favorite or not)
//TODO: 4)Create ViewModelFactory
//TODO: 5) Create MainViewModel
// TODO: 6) Fix DetailsActivity
// TODO: Delete this when Room works
//public class FavoriteProvider extends ContentProvider {

    /**
     * Tag for the log messages
     */
    ///public static final String LOG_TAG = FavoriteProvider.class.getSimpleName();

    //Mental note: tag debugs with a different TAG
    //private static final String DEBUG_TAG = "DebugStuff";

    //Movie movie;**/

    /**
     * URI matcher code for the content URI for the movie table
     **/
    //    private static final int FAVORITE_MOVIE = 100;

    /**
     * URI matcher code for the content URI for a single item in the movie table
     **/

    //static final int FAVORITE_MOVIE_DETAIL = 101;

    //static final int MOVIE_ID = 107;


    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     **/
    //private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


    // Static initializer. This is run the first time anything is called from this class.
    //static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        // The content URI of the form "content://com.example.android.popmoviesearchstage2/will map to the
        // integer code. This URI is used to provide access to MULTIPLE rows of the movie tables.
      //  sUriMatcher.addURI(FavoriteContract.CONTENT_AUTHORITY, FavoriteContract.PATH_MOVIE,FAVORITE_MOVIE);

        // This URI is used to provide access to ONE single row of the chosen movie table.
        // In this case, the "#" wildcard is used where "#" can be substituted for an integer.
        //
     //  sUriMatcher.addURI(FavoriteContract.CONTENT_AUTHORITY, FavoriteContract.PATH_FAVORITE
       //                + "/#" , FAVORITE_MOVIE_DETAIL);
    //}

    /**
     * Database helper object
     **/
    //private FavoriteDBHelper mFavoriteDbHelper;

    /**@Override
    public boolean onCreate() {
        mFavoriteDbHelper = new FavoriteDBHelper(getContext());
        return true;

    }

    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(DEBUG_TAG,"Movie Provider Cursor query:start - uri: " + uri.toString());
        // Get readable database
        SQLiteDatabase database = mFavoriteDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            // For the Movie, query the Movie table directly with the given
            // projection, selection, selection arguments, and sort order. The cursor
            // could contain multiple rows of the chosen movie table.
            case FAVORITE_MOVIE: {
                cursor = database.query(FavoriteMovieEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            }

            // For the MOVIE_ID code, extract out the ID from the URI.
            // The selection will be "_id=?" and the selection argument will be a
            // String array containing the actual ID.
            //
            case FAVORITE_MOVIE_DETAIL: {
                selection =FavoriteMovieEntry.COLUMN_MOVIE_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                // This will perform a query on the movie table where the _id  will return a
                // Cursor containing that row of the Favorite Movie table.
                cursor = database.query(FavoriteMovieEntry.TABLE_NAME, projection, selection, selectionArgs,
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
        Log.d(DEBUG_TAG,"Movie Provider Cursor getType: uri: " + uri.toString());
        final int match = sUriMatcher.match ( uri );
        switch (match) {
            case FAVORITE_MOVIE: {
                return FavoriteMovieEntry.CONTENT_LIST_TYPE;
            }
            case FAVORITE_MOVIE_DETAIL: {
                return FavoriteMovieEntry.CONTENT_ITEM_TYPE;

            }default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);

        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(DEBUG_TAG,"Movie Provider Cursor insert - uri: " + uri.toString());
        final int match = sUriMatcher.match ( uri );

        // Get writable database
        SQLiteDatabase database = mFavoriteDbHelper.getWritableDatabase();

        Uri returnUri;

        switch (match) {
            case FAVORITE_MOVIE: {
                long _id = database.insert(FavoriteMovieEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = FavoriteMovieEntry.buildFavoriteMoviesUri ();
                else
                    throw new android.database.SQLException("Insertion is not supported for" + uri);
                break;
        }
        default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
      getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;


    }**/

       /** public  Uri addFavorites(Uri uri) {
        // Get writable database
        SQLiteDatabase database = mFavoriteDbHelper.getWritableDatabase();

            ContentValues values = new ContentValues ();

        values.getAsInteger (FavoriteMovieEntry.COLUMN_MOVIE_ID);
        values.getAsString ( FavoriteMovieEntry.COLUMN_TITLE);
        values.getAsString( FavoriteMovieEntry.COLUMN_VOTE_AVERAGE);
        values.getAsString ( FavoriteMovieEntry.COLUMN_RELEASE_DATE);
        values.getAsString( FavoriteMovieEntry.COLUMN_OVERVIEW);
        values.getAsString( FavoriteMovieEntry.COLUMN_POSTER_PATH);

        values.put (FavoriteMovieEntry.COLUMN_MOVIE_ID, movie.getId ());
        values.put (FavoriteMovieEntry.COLUMN_TITLE, movie.getTitle () );
        values.put (FavoriteMovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage () );
        values.put (FavoriteMovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate () );
        values.put (FavoriteMovieEntry.COLUMN_OVERVIEW, movie.getOverview () );
        values.put (FavoriteMovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath () );
        database.insert ( FavoriteMovieEntry.TABLE_NAME, null, values );
        database.close ();

        // Insert the new item with the given values
        long id =database.insert ( FavoriteMovieEntry.TABLE_NAME, null, values );
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);

        }

        //  Notify changes
        getContext().getContentResolver().notifyChange(uri, null);

        // Return Content Uri
        return ContentUris.withAppendedId(uri, id);
    }**/


/**    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mFavoriteDbHelper.getWritableDatabase ();
        int favoriteMovieRemoved;
        final int match = sUriMatcher.match ( uri );
        switch (match) {
            case FAVORITE_MOVIE:
                selection = FavoriteMovieEntry.COLUMN_MOVIE_ID + "=?";
                selectionArgs = new String[]{String.valueOf ( ContentUris.parseId ( uri ) )};
                favoriteMovieRemoved = database.delete ( FavoriteMovieEntry.TABLE_NAME,  selection,
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException ( "Deletion is not supported for " + uri );

               // if (favoriteMovieRemoved != 0) {
                 //   getContext ().getContentResolver ().notifyChange ( uri, null );
                }


            if (selection == null || favoriteMovieRemoved != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
            }

               // Return the number of rows deleted
        return favoriteMovieRemoved;
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
         throw new IllegalArgumentException("Update is not supported for " + uri);

    }



}
**/