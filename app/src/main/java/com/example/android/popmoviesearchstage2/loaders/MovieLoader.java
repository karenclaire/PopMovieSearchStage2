package com.example.android.popmoviesearchstage2.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.android.popmoviesearchstage2.adapters.MovieAdapter;
import com.example.android.popmoviesearchstage2.model.Movie;
import com.example.android.popmoviesearchstage2.utils.MovieUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karenulmer on 2/20/2018.
 */

public class MovieLoader extends AsyncTaskLoader<List<Movie>>  {

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = MovieLoader.class.getName();

    /**
     * Query URL
     */
    private String mUrl;

    private Context mContext;
    private final List<Movie> mMovieList = new ArrayList<>();

    Movie mMovie;
    MovieAdapter mMovieAdapter;
    long movieId;
    boolean isFaveMovie;

    /**
     * Constructs a new {@link MovieLoader}.
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public MovieLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Movie> loadInBackground() {
        Log.d("MovieLoader", "loadInBackground");
        if (mUrl == null) {
            return null;

        }
        // Perform the network request, parse the response, and extract a list of movies.
        List<Movie> movies = MovieUtils.fetchMovieData(mUrl);
        return movies;


    }


}

