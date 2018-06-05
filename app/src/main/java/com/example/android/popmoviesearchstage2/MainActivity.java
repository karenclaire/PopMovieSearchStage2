package com.example.android.popmoviesearchstage2;

/** * Created by karenulmer on 2/20/2018.
 *
 * References used for coding guide and corrections:
 *  https://github.com/first087/Android-ViewHolder-Example/blob/master/app/src/main/java/com/artitk/android_viewholder_example/GridViewActivity.java
 *  https://discussions.udacity.com/t/popular-movies-stage1-help/618976/27
 *  https://discussions.udacity.com/t/sharedpreferences-is-killing-me/163576/5
 *  https://github.com/ajinkya007/Popular-Movies-Stage-1
 *  https://github.com/bapspatil/FlickOff
 *  https://github.com/henriquenfaria/popular-movies-stage-1
 *  My previous udacity projects:
 *    https://github.com/karenclaire/EscrimaInventoryApp
 *    https://github.com/karenclaire/NewsApp
 *    https://github.com/karenclaire/BookListingApp
 *  https://googledevndscholars.slack.com/threads/
 *  https://gist.github.com/riyazMuhammad/1c7b1f9fa3065aa5a46f
 *  https://www.youtube.com/watch?v=OOLFhtyCspA
 *  https://github.com/FrangSierra/Udacity-Popular-Movies-Stage-2
 *  https://github.com/SubhrajyotiSen/Popular-Movies-2
 *  https://github.com/jimandreas/PopularMovies
 *  yellow star image care of  https://en.wikipedia.org/w/index.php?curid=21298582
 **/


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popmoviesearchstage2.adapters.MovieAdapter;
import com.example.android.popmoviesearchstage2.model.Movie;
import com.example.android.popmoviesearchstage2.model.MovieResponse;
import com.example.android.popmoviesearchstage2.retrofit.MovieInterface;
import com.example.android.popmoviesearchstage2.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

//implements  SharedPreferences.OnSharedPreferenceChangeListener
    /**
     * Tag for log messages
     */
    public static final String LOG_TAG = MainActivity.class.getName();
    private static final String DEBUG_TAG = "Debug";

    private final String TOP_RATED = "top_rated";
    private final String FAVORITE = "favorite";
    private final String POPULAR = "popular";
    public static final String PATH_TOP_RATED = "top_rated";
    public static final String PATH_POPULAR = "popular";
    public static final String PATH_FAVORITE = "favorite";

    /**
     * Constant value for the movie loader ID
     */
    private static final int MOVIE_LOADER_ID = 1;

    /**
     * Movies List
     */
    public List<Movie> moviesList;

    /**
     * Adapter for the list of movies
     */
    public MovieAdapter mMovieAdapter;

    public Movie mMovie;

    public Context mContext;


    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    /**
     * ProgressBar that is displayed when the data is loaded
     */
    private ProgressBar mProgressBar;

    static public String prefSortOrder;

    private RecyclerView mRecyclerView;

    private GridLayoutManager layoutManager;
    ProgressDialog progressDialog;

    private SwipeRefreshLayout swipeRefreshLayout;

    private static final String MOVIE_REQUEST_URL = "http://api.themoviedb.org/3/movie/popular?api_key=";
    private static final String MOVIE_TOP_RATED_URL = "http://api.themoviedb.org/3/movie/top_rated?api_key=";


    CursorLoader mCursorLoader;
    Cursor mCursor;

   /** public String[] topRatedProjection = {
            TopRatedMovieEntry.COLUMN_MOVIE_ID,
            TopRatedMovieEntry.COLUMN_TITLE,
            TopRatedMovieEntry.COLUMN_VOTE_AVERAGE,
            TopRatedMovieEntry.COLUMN_RELEASE_DATE,
            TopRatedMovieEntry.COLUMN_POSTER_PATH};

    public String[] favoriteProjection = {
            FavoriteMovieEntry.COLUMN_MOVIE_ID,
            FavoriteMovieEntry.COLUMN_TITLE,
            FavoriteMovieEntry.COLUMN_VOTE_AVERAGE,
            FavoriteMovieEntry.COLUMN_RELEASE_DATE,
            FavoriteMovieEntry.COLUMN_POSTER_PATH};

    public String[] popularProjection = {
            PopularMovieEntry.COLUMN_MOVIE_ID,
            PopularMovieEntry.COLUMN_TITLE,
            PopularMovieEntry.COLUMN_VOTE_AVERAGE,
            PopularMovieEntry.COLUMN_RELEASE_DATE,
            PopularMovieEntry.COLUMN_POSTER_PATH};**/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(DEBUG_TAG, "MainActivity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Movies");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //Find reference to the RecyclerView and assert
        mRecyclerView = findViewById(R.id.recyclerview_grid);
        assert mRecyclerView != null;

        // Find the reference to the progress bar in the layout
        //mProgressBar = findViewById(R.id.pb_loading_indicator);
        // Find the reference to the empty text view in a layout and set empty view
        //mEmptyStateTextView = findViewById(R.id.empty_view);
        //movieGridView.setEmptyView(mEmptyStateTextView);

        moviesList = new ArrayList<>();
        mMovieAdapter = new MovieAdapter(mContext,moviesList);
        /*
         * Use this setting to improve performance if you know that changes in content
         * change the child layout size in the RecyclerView
         */
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(this, 2);
            mRecyclerView.setLayoutManager(layoutManager);
        } else {
            layoutManager = new GridLayoutManager(this, 4);
            mRecyclerView.setLayoutManager(layoutManager);
        }

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mMovieAdapter = new MovieAdapter(mContext, moviesList);
        mRecyclerView.setAdapter(mMovieAdapter);
        mMovieAdapter.notifyDataSetChanged();

        loadJson();
    }

    //Helper method to get Activity context
    public Activity getActivity() {
        Context context = this;
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }


    private void loadJson() {
        Log.d(DEBUG_TAG, "MainActivity loadJson");

        try {
            if (BuildConfig.API_KEY.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please get API from themoviedb.org", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            RetrofitClient retrofitClient = new RetrofitClient();
            MovieInterface movieInterface = RetrofitClient.getRetrofitClient().create(MovieInterface.class);
            Call<MovieResponse> call = movieInterface.getPopularMovies(BuildConfig.API_KEY);

            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    List<Movie> movieList = response.body(). getResults();
                    mRecyclerView.setAdapter(new MovieAdapter(getApplicationContext(), moviesList));
                    mRecyclerView.smoothScrollToPosition(0);

                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(MainActivity.this, "Error fetching Data", Toast.LENGTH_SHORT).show();
                }

            });
        } catch (Exception e) {
            Log.d("MainActivity Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();

        }


    }

    // Helper method to check network connection
    /**public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }**/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





    /**@Override
    public Loader onCreateLoader(int loaderId, Bundle bundle) {
        Log.d(DEBUG_TAG, "MainActivity onCreateLoader " + String.valueOf(loaderId));
        // Define a projection that specifies the columns from the table we care about.
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String preferenceSortOrder = sharedPrefs.getString(getString(R.string.pref_sorting_criteria_key),
                getString(R.string.pref_sorting_criteria_default_value));
       Uri baseUri;
        switch (loaderId) {

            case MOVIE_LOADER_ID:
                if (prefSortOrder != null && preferenceSortOrder.contains(TOP_RATED)){
                    Log.d(DEBUG_TAG, "MainActivity topRatedSortOrder " + prefSortOrder);
                    return new CursorLoader(this,   // Parent activity context
                            TopRatedMovieEntry.CONTENT_URI,   // Provider content URI to query
                            topRatedProjection,             // Columns to include in the resulting Cursor
                            null,                   // No selection clause
                            null,                   // No selection arguments
                            null);
               }else if (prefSortOrder != null && preferenceSortOrder.contains(FAVORITE)){
                    Log.d(DEBUG_TAG, "MainActivity favoriteSortOrder " + prefSortOrder);
                    return new CursorLoader(this,   // Parent activity context
                            FavoriteMovieEntry.CONTENT_URI,   // Provider content URI to query
                            favoriteProjection,             // Columns to include in the resulting Cursor
                            null,                   // No selection clause
                            null,                   // No selection arguments
                            null);

                }else {Log.d(DEBUG_TAG, "MainActivity defaultSortOrder " + prefSortOrder);
                    return new CursorLoader(this,   // Parent activity context
                            PopularMovieEntry.CONTENT_URI,   // Provider content URI to query
                            popularProjection,             // Columns to include in the resulting Cursor
                            null,                   // No selection clause
                            null,                   // No selection arguments
                            null);

                }
            default:
                throw new RuntimeException("Loader Not Implemented: " + loaderId);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        Log.d(DEBUG_TAG, "MainActivity OnLoadFinished");
        // Update {@link MovieAdapter} with this new cursor containing updated Movie data
        mMovieAdapter.swapCursor(mCursor);
        mEmptyStateTextView.setText(R.string.no_movies);
        mProgressBar.setVisibility(View.GONE);
    }


    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        Log.d(DEBUG_TAG, "OnCreate Loader Reset");
                //  clear the Cursor
        mMovieAdapter.swapCursor(null);

    }

    @Override
    protected void onDestroy() {
        Log.d(DEBUG_TAG, "MainActivity OnDestroy");
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);

    }**/

    /**@Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(DEBUG_TAG, "MainActivity OnSharedPreference");
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader((MOVIE_LOADER_ID), null, this);

    }**/
}