package com.example.android.popmoviesearchstage2.loaders;

/**
 * Created by karenulmer on 2/20/2018.
 */


public class MovieLoader { /*
 extends AsyncTaskLoader<List<Movie>>  {
    private static final String DEBUG_TAG = "DebugStuff";

    */
/**
 * Tag for log messages
 *//*

    private static final String LOG_TAG = MovieLoader.class.getName();

    */
/**
 * Query URL
 *//*

    private String mUrl;


    private final List<Movie> mMovieList = new ArrayList<>();

    Movie mMovie;
    MovieAdapter mMovieAdapter;
    long movieId;
    boolean isFaveMovie;

    */
/**
 * Constructs a new {@link MovieLoader}.
 *
 * @param context of the activity
 * @param url     to load data from
 *//*

    public MovieLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.d(DEBUG_TAG, "Movie Loader onStartLoading Error line 51");

    }
    */
/**
 * This is on a background thread.
 *//*

    @Override
    public List<Movie> loadInBackground() {
        Log.d(DEBUG_TAG, "MovieLoader loadInBackground Error line60");
        if (mUrl == null) {
            return null;

        }
        // Perform the network request, parse the response, and extract a list of movies.
        List<Movie> movies = MovieUtils.fetchMovieData(mUrl);
        return movies;


    }


*/
}