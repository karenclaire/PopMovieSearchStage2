package com.example.android.popmoviesearchstage2.loaders;

/**public class FavoriteLoader extends AsyncTask<String, Void, Void> {
    private final Context mContext;
    private final List<Movie> mMovieList = new ArrayList<>();

    Movie mMovie;
    MovieAdapter mMovieAdapter;
    long movieId;
    boolean isFaveMovie;

    public FavoriteLoader(Context context) {
        mContext = context;
    }


    @Override
    protected Void doInBackground(String... strings) {
        Uri uri = FavoriteMovieEntry.buildFavoriteMoviesUri();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Cursor cursor = mContext.getContentResolver().query(
                    uri,
                    new String[]{
                            FavoriteMovieEntry.COLUMN_MOVIE_ID,
                            FavoriteMovieEntry.COLUMN_FAVORITE_MOVIE
                    },
                    null,
                    null,
                    null,
                    null);

            if (cursor == null || !cursor.moveToFirst()) {
                return null;
            }
            Integer faveMovieId;
            Integer isFaveMovie;
            do {
                faveMovieId = cursor.getInt(cursor.getColumnIndex(
                        FavoriteMovieEntry.COLUMN_MOVIE_ID));
                isFaveMovie = cursor.getInt(cursor.getColumnIndex(
                        FavoriteMovieEntry.COLUMN_FAVORITE_MOVIE));
                if (isFaveMovie == 1) {
                    addToFavoriteMovie();
                    addFavoriteID(faveMovieId);
                } else {
                    int delete = mContext.getContentResolver().delete(
                            uri,
                            FavoriteMovieEntry.COLUMN_MOVIE_ID + " = " + faveMovieId,
                            null
                    );
                }
            }
            while (cursor.moveToNext());
        }
        return null;
    }


    public void addFavoriteID(int movie_id) {
        mFavoritesSet.add(movie_id);
    }

    private static HashSet<Integer> mFavoritesSet = new HashSet<>();

    /**
     * Go through the movies on the list to update one of them with his new favored value.
     */
   /** private void addToFavoriteMovie (){
                for (int i = 0; i < mMovieList.size(); i++) {
                    final Movie movie = mMovieList.get(i);
                    if (movie.getId() != movieId) continue;
                    movie.setFaveMovie(isFaveMovie);
                    mMovieAdapter.notifyDataSetChanged();
                }

            }

        }**/


