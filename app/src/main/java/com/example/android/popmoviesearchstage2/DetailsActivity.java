package com.example.android.popmoviesearchstage2;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popmoviesearchstage2.adapters.ReviewAdapter;
import com.example.android.popmoviesearchstage2.adapters.TrailerAdapter;
import com.example.android.popmoviesearchstage2.data.FavoriteContract.FavoriteMovieEntry;
import com.example.android.popmoviesearchstage2.data.FavoriteDBHelper;
import com.example.android.popmoviesearchstage2.model.Movie;
import com.example.android.popmoviesearchstage2.model.Review;
import com.example.android.popmoviesearchstage2.model.ReviewResponse;
import com.example.android.popmoviesearchstage2.model.Trailer;
import com.example.android.popmoviesearchstage2.model.TrailerResponse;
import com.example.android.popmoviesearchstage2.retrofit.MovieInterface;
import com.example.android.popmoviesearchstage2.retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by karenulmer on 2/20/2018.
 *
 *References used for coding guide and corrections:
 * 1) https://github.com/first087/Android-ViewHolder-Example/blob/master/app/src/main/java/com/artitk/android_viewholder_example/GridViewActivity.java
 * 2)https://github.com/ajinkya007/Popular-Movies-Stage-1
 * 3) https://github.com/bapspatil/FlickOff
 * 4)https://github.com/henriquenfaria/popular-movies-stage-1
 * 5) my previous udacity projects: NewsApp and BookApp
 * 6) https://googledevndscholars.slack.com/threads/
 * 7)https://gist.github.com/riyazMuhammad/1c7b1f9fa3065aa5a46f
 */

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String EXTRA_MOVIE = "intent_extra_movie";
    public static final String EXTRA_POSTER = "intent_poster_movie";
    public static final String EXTRA_VIDEO = "intent_video";
    public static final String EXTRA_VIDEO_SITE = "intent_video_site";
    public static final String EXTRA_REVIEW = "intent_review";
    public static final String EXTRA_REVIEW_SITE = "intent_review_site";
    public static final String VIDEO_PATH = "https://www.youtube.com/watch?v=";
    public static final String POSTER_PATH = "http://image.tmdb.org/t/p/w185/";
    private static final String LOG_TAG = DetailsActivity.class.getSimpleName ();
    private static final String DEBUG_TAG = "DebugStuff";
    static public String favoriteMovie;
    public ImageButton mFavoriteButton;
    public List<Movie> moviesList = new ArrayList<> ();
    public List<Review> mReviewList = new ArrayList<> ();
    public final ReviewAdapter mReviewAdapter = new ReviewAdapter( this, mReviewList );
    public Movie favoriteMovies;
    public List<Trailer> mTrailerList = new ArrayList<> ();
    private final TrailerAdapter mTrailerAdapter = new TrailerAdapter ( this, mTrailerList );
    @BindView(R.id.tv_rating)
    TextView ratingTextView;
    @BindView(R.id.tv_date)
    TextView dateTextView;
    @BindView(R.id.movie_poster)
    ImageView posterImageView;
    @BindView(R.id.summary)
    TextView overviewTextView;
    @BindView(R.id.title)
    TextView titleTextView;
    @BindView(R.id.trailer_container)
    ViewGroup trailerContainer;
    @BindView(R.id.review_container)
    ViewGroup reviewContainer;
    //@BindView(R.id.toolbar)
    // Toolbar mToolbar;
    //@BindView(R.id.collapsing_toolbar)
    //CollapsingToolbarLayout mCollapsingToolBar;
    //@BindView( (R.id.appBar))
    //AppBarLayout mAppBar;
    //@BindView(R.id.favorite_button)
    //MaterialFavoriteButton materialFavoriteButton;
    @Nullable
    @BindView ( R.id.movie_title)
    TextView favoriteMovieTitle;
    @Nullable
    @BindView ( R.id.movie_id )
    TextView favoriteMovieId;
    @BindView ( R.id.favorite_release_date )
    @Nullable
    TextView favoriteReleaseDate;
    @Nullable
    @BindView ( R.id.favorite_rating )
    TextView favoriteRating;
    @BindView ( R.id.fab )
    FloatingActionButton fab;
    Context mContext;
    //private final AppCompatActivity activity = DetailsActivity.this;
    @BindView(R.id.reviews_list)
    RecyclerView reviewRecyclerView;
    FavoriteDBHelper favoriteDBHelper;
    @BindView(R.id.trailer_list)
    RecyclerView trailerListRecyclerView;
    boolean isFavorite;
    int favorite;
    Uri mFavoriteMovieUri;
    private Movie mMovie;
    private String posterPath;
    int id;

    static final int POPULAR = 1;
    static final int NOT_POPULAR = 2;
    static final int ERROR = 3;

    public static String getYear(String dateString) {
        Calendar calendar = Calendar.getInstance ();
        SimpleDateFormat parser = new SimpleDateFormat ( "yyyy-MM-dd" );
        Date date = null;
        try {
            date = parser.parse ( dateString );
        } catch (ParseException e) {
            e.printStackTrace ();
        }
        calendar.setTime ( date );
        return String.valueOf ( calendar.get ( Calendar.YEAR ) );

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d ( DEBUG_TAG, "DetailsActivity onCreate" );
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.movie_details );
        ButterKnife.bind ( this );

        Intent intent = getIntent ();

        mFavoriteMovieUri = intent.getData();


        mMovie = intent.getParcelableExtra ( DetailsActivity.EXTRA_MOVIE );
        showMovieDetails();

       // Setup FAB to open EditorActivity
        final FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(DEBUG_TAG,"OnCreate: fab.onClick");

                ContentResolver resolver = view.getContext().getContentResolver();
                ContentValues values = new ContentValues();
                // If the isFavorite equal to true, the Current Movie will be added to the Favorite Movie List;
                /**if (isFavorite == true){
                 /**  mFavoriteMovieUri = FavoriteMovieEntry.buildMovieDetailsUri(id);
                    resolver.insert(
                             mFavoriteMovieUri,
                            values
                    );
                    //saveFavorite ();**/
                    //setFavoriteFabIcon ();
                   //if(isMovieInDB ( resolver, mMovie )){
                    isMovieInDB ( resolver, mMovie);
                    changeMovieStatus ( resolver, mMovie, false );
                    /**fab.setImageResource(R.drawable.yellow_star);
                    Toast.makeText(mContext, "Movie is now in Favorite list", Toast.LENGTH_SHORT).show();
                    mContext.getContentResolver().notifyChange(mFavoriteMovieUri, null);
                } else {
                    // Not a favorite
                    //setFavoriteFabIcon ();
                    /**favoriteDBHelper = new FavoriteDBHelper ( mContext );
                    favoriteDBHelper.deleteFavorite ( id );
                    fab.setImageResource(R.drawable.ic_star_white_24dp);
                    Toast.makeText(mContext, "Movie not in Favorite List", Toast.LENGTH_SHORT).show();**/
                }
            });


       /** SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences ( getApplicationContext () );
        materialFavoriteButton.setOnFavoriteChangeListener ( new MaterialFavoriteButton.OnFavoriteChangeListener () {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                if (favorite) {
                    SharedPreferences.Editor editor = getSharedPreferences
                            ( "com.example.android.popmoviesearchstage2.DetailsActivity", MODE_PRIVATE ).edit ();
                    editor.putBoolean ( "Favorite Added", true );
                    materialFavoriteButton.setImageResource(R.drawable.yellow_star);
                    editor.apply ();
                    saveFavorite ();
                    Snackbar.make ( buttonView, "Added to Favorite", Snackbar.LENGTH_SHORT ).show ();
                } else {
                    int id = mMovie.getId ();
                    favoriteDBHelper = new FavoriteDBHelper ( mContext );
                    favoriteDBHelper.deleteFavorite ( id );
                    SharedPreferences.Editor editor = getSharedPreferences
                            ( "com.example.android.popmoviesearchstage2.DetailsActivity", MODE_PRIVATE ).edit ();
                    editor.putBoolean ( "Favorite Removed", true );
                    materialFavoriteButton.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                    editor.apply ();
                    Snackbar.make ( buttonView, "Removed from Favorite", Snackbar.LENGTH_SHORT ).show ();

                }
            }


        } );**/

       initViews ();
    }

   /** private void setFavoriteFabIcon() {
        boolean inFavorites = checkFavorites(mMovie.getId());
        ImageView addToFav = findViewById(R.id.fab);

        if (inFavorites) {
            addToFav.setImageResource(R.drawable.yellow_star);
        } else {
            addToFav.setImageResource(R.drawable.ic_star_white_24dp);
        }
    }**/


    /**private boolean checkFavorites(int id) {

        Uri uri = FavoriteMovieEntry.buildMovieDetailsUri (id );
        ContentResolver resolver = this.getContentResolver();
        Cursor cursor = null;

        try {

            cursor = resolver.query(uri, null, null, null, null);
            if (cursor.moveToFirst())
                return true;

        } finally {

            if (cursor != null)
                cursor.close();

        }

        return false;
    }**/

    public void showMovieDetails() {
        Log.d ( DEBUG_TAG, "DetailsActivity showMovieDetails" );
        String releaseDate=( mMovie.getReleaseDate());
        releaseDate =getYear ( releaseDate );
        dateTextView.setText  (releaseDate);
        ratingTextView.setText ( mMovie.getVoteAverage ().toString () );
        overviewTextView.setText ( mMovie.getOverview () );
        titleTextView.setText ( mMovie.getTitle () );

        //Intent intent = getIntent ();

        posterPath = POSTER_PATH +(mMovie.getPosterPath ());
        //posterPath =intent.getStringExtra ( DetailsActivity.EXTRA_POSTER );
        Picasso.with ( mContext ).setLoggingEnabled ( true );

        Picasso.with ( posterImageView.getContext ())
                .load ( posterPath )
                .into ( posterImageView );


    }

    private void initViews() {

        Log.d ( DEBUG_TAG, "DetailsActivity initViews" );

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager ( getApplicationContext () );
        trailerListRecyclerView.setLayoutManager ( layoutManager );
        trailerListRecyclerView.setAdapter ( mTrailerAdapter );
        mTrailerAdapter.notifyDataSetChanged ();

        loadTrailers ();

        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager ( getApplicationContext () );
        reviewRecyclerView.setLayoutManager ( linearLayoutManager );
        reviewRecyclerView.setAdapter ( mReviewAdapter );
        mReviewAdapter.notifyDataSetChanged ();


        loadReviews ();

    }

    private void loadTrailers() {
        Log.d ( DEBUG_TAG, "DetailsActivity loadTrailers" );
        int mMovieId = mMovie.getId ();
        try {
            if (BuildConfig.API_KEY.isEmpty ()) {
                Toast.makeText ( getApplicationContext (), "Please get API from themoviedb.org", Toast.LENGTH_SHORT ).show ();

            }

            RetrofitClient retrofitClient = new RetrofitClient ();
            Log.d ( DEBUG_TAG, "DetailsActivity movieInterface loadTrailers" );
            MovieInterface movieInterface = retrofitClient.getRetrofitClient ().create ( MovieInterface.class );
            Call<TrailerResponse> call = movieInterface.getTrailers ( mMovieId, BuildConfig.API_KEY );
            call.enqueue ( new Callback<TrailerResponse> () {

                @Override
                   public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                    Log.d ( DEBUG_TAG, "DetailsActivity onResponse Trailers" );
                    mTrailerList = response.body ().getResults ();
                    mTrailerAdapter.loadTrailers (mTrailerList, mContext);
                    trailerListRecyclerView.smoothScrollToPosition ( 0 );


                }

                @Override
                public void onFailure(Call<TrailerResponse> call, Throwable t) {
                    Log.d ( "Error", t.getMessage () );
                    Toast.makeText ( DetailsActivity.this, "Error fetching Data", Toast.LENGTH_SHORT ).show ();
                }

            } );
        } catch (Exception e) {
            Log.d ( "DetailsActivity Error", e.getMessage () );
            Toast.makeText ( this, e.toString (), Toast.LENGTH_SHORT ).show ();
        }
    }

    public void saveFavorite() {
        Log.d ( DEBUG_TAG, "DetailsActivity SaveFavorite" );
        ContentValues values = new  ContentValues();
        favoriteDBHelper = new FavoriteDBHelper ( mContext );
        favoriteMovies = new Movie ();
        int id = getIntent ().getExtras ().getInt ( "id" );
        Double voteAverage = getIntent ().getExtras ().getDouble ( "vote_average" );
        String movieTitle = getIntent ().getExtras ().getString ( "original_title" );
        String releaseDate = getIntent ().getExtras ().getString ( "release_date" );
        String posterPath = getIntent ().getExtras ().getString ( "poster_path" );


        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        ///int id = Integer.parseInt ( favoriteMovieId.getText ().toString ().trim() );
        //Double voteAverage = Double.parseDouble ( favoriteRating.toString ().trim() );
        //String movieTitle = favoriteMovieTitle.getText ().toString ().trim ();
        //String releaseDate = favoriteReleaseDate.getText ().toString ().trim ();

        favoriteMovies.setId ( id );
        favoriteMovies.setTitle ( movieTitle );
        favoriteMovies.setVoteAverage(voteAverage);
        favoriteMovies.setReleaseDate ( releaseDate );
        favoriteMovies.setPosterPath ( posterPath );
        favoriteDBHelper.addFavorites ( favoriteMovies );


       /** posterPath = POSTER_PATH +(mMovie.getPosterPath ());
        Picasso.with ( mContext ).setLoggingEnabled ( true );

        Picasso.with ( posterImageView.getContext ())
                .load ( posterPath )
                .into ( posterImageView );**/

        // Determine if this is a new or existing item  by checking if mFavoriteMovieUri is null or not
        if (mFavoriteMovieUri == null) {
            // This is to insert a new item into the provider, returning the content URI for
            // the new movie.
            Uri newUri = getContentResolver().insert(FavoriteMovieEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful.
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.insert_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.insert_successful),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            // Otherwise this is an EXISTING Movie in the favorite List, so update the Favorite Movie List
            // with content URI: mFavoriteMovieUri and pass in the new ContentValues.
            // Pass in null for the selection and selection args because mFavoriteMovieUri
            // will already identify the correct row in the database that we want to modify.
            int rowsAffected = getContentResolver().update(mFavoriteMovieUri, values, null, null);

            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, getString(R.string.update_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.update_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        finish();
    }

    public void loadReviews() {
        Log.d ( DEBUG_TAG, "DetailsActivity loadReviews" );
        int mMovieId = mMovie.getId ();
        try {
            if (BuildConfig.API_KEY.isEmpty ()) {
                Toast.makeText ( getApplicationContext (), "Please get API from themoviedb.org", Toast.LENGTH_SHORT ).show ();

            }

            RetrofitClient retrofitClient = new RetrofitClient ();
            Log.d ( DEBUG_TAG, "DetailsActivity movieInterface loadReviews" );

            MovieInterface movieInterface = retrofitClient.getRetrofitClient ().create ( MovieInterface.class );
            Call<ReviewResponse> call = movieInterface.getReviews ( mMovieId, BuildConfig.API_KEY );
            call.enqueue ( new Callback<ReviewResponse> () {


                @Override
                public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                    Log.d ( DEBUG_TAG, "DetailsActivity onResponseReviews" );
                    mReviewList = response.body ().getResults ();
                    mReviewAdapter.loadReviews (mReviewList, mContext);
                    reviewRecyclerView.smoothScrollToPosition ( 0 );

                }

                @Override
                public void onFailure(Call<ReviewResponse> call, Throwable t) {
                    Log.d ( "Error", t.getMessage () );
                    Toast.makeText ( DetailsActivity.this, "Error fetching Data", Toast.LENGTH_SHORT ).show ();

                }
            } );
        } catch (Exception e) {
            Log.d ( "DetailsActivity Error", e.getMessage () );
            Toast.makeText ( this, e.toString (), Toast.LENGTH_SHORT ).show ();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ().inflate ( R.menu.menu_main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId ();

        if (id == R.id.action_settings) {
            startActivity ( new Intent ( this, SettingsActivity.class ) );
            return true;
        }

        return super.onOptionsItemSelected ( item );
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d (DEBUG_TAG, "DetailsActivity onCreateLoader");
        // Since the favorite list shows the poster, rating and release date attributes,
        // the projection contains these columns from the favorite movie table
        String[] projection = {
                FavoriteMovieEntry._ID,
                FavoriteMovieEntry.COLUMN_MOVIE_ID,
                FavoriteMovieEntry.COLUMN_TITLE,
                //FavoriteMovieEntry.COLUMN_POSTER_PATH,
                FavoriteMovieEntry.COLUMN_VOTE_AVERAGE,
                FavoriteMovieEntry.COLUMN_POSTER_PATH};


        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader ( this,   // Parent activity context
                mFavoriteMovieUri,   // Query the content URI for the current inventory
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null );                  // Default sort order

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.d (DEBUG_TAG, "DetailsActivity onLoadFinished");
        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            // Find the columns of item attributes that we're interested in
            int id = cursor.getInt(cursor.getColumnIndex(FavoriteMovieEntry._ID));
            int titleColumnIndex = cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_TITLE);
            int posterPathColumnIndex = cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_POSTER_PATH);
            int voteAverageColumnIndex = cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_VOTE_AVERAGE);
            int releaseDateColumnIndex = cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_RELEASE_DATE);

            // Extract out the value from the Cursor for the given column index
           String movieId = cursor.getString(id);
           String movieTitle = cursor.getString(titleColumnIndex);
           String poster = cursor.getString(posterPathColumnIndex);
           String vote = cursor.getString(voteAverageColumnIndex);
           String releaseDate = cursor.getString(releaseDateColumnIndex);

            /** Update the views on the screen with the values from the database /**/
           favoriteMovieId.setText ( movieId );
           favoriteMovieTitle.setText ( movieTitle );
           favoriteRating.setText(vote);
           favoriteReleaseDate.setText(releaseDate);

        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d (DEBUG_TAG, "DetailsActivity onLoadReset");
        favoriteMovieTitle.setText("");
        favoriteMovieId.setText("");
        favoriteRating.setText ( "" );
        favoriteReleaseDate.setText ( "" );

    }


    static boolean isMovieInDB(ContentResolver contentResolver, Movie movie) {
        Log.d (DEBUG_TAG, " isMovieInDB:start - movieId: " + movie.getId ());
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(
                    FavoriteMovieEntry.buildMovieDetailsUri (movie.getId ()),
                    null, null, null, null);

            return ((cursor != null) && (cursor.getCount() > 0));
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    static int changeMovieStatus(ContentResolver contentResolver, Movie movie, Boolean popular) {

        if (popular == null) {
            popular = isMovieInDB(contentResolver, movie);
        }
        if (popular) {
            // delete from database
            int deleted = contentResolver.delete(
                    FavoriteMovieEntry.buildMovieDetailsUri ( movie.getId()),
                    null, null);

            if (deleted > 0) return NOT_POPULAR;
            else return ERROR;
        } else {
            // insert to database
            ContentValues values = new ContentValues();
            values.clear();
            values.put (FavoriteMovieEntry.COLUMN_MOVIE_ID, movie.getId ());
            values.put (FavoriteMovieEntry.COLUMN_TITLE, movie.getTitle () );
            values.put (FavoriteMovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage () );
            values.put (FavoriteMovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate () );
            values.put (FavoriteMovieEntry.COLUMN_OVERVIEW, movie.getOverview () );
            values.put (FavoriteMovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath () );


            Uri insertResult = contentResolver.insert(
                    FavoriteMovieEntry.buildMovieDetailsUri (movie.getId()),
                    values);

            if (insertResult != null) return POPULAR;
            else return ERROR;
        }
    }
}

/**        if (intent == null || !intent.hasExtra(EXTRA_MOVIE)) {
 throw new NullPointerException("Movie cannot be empty");
 }

 if (savedInstanceState == null) {
 Movie movie = intent.getParcelableExtra(EXTRA_MOVIE);
 ArrayList<Trailer> trailer = intent.getParcelableArrayListExtra(EXTRA_VIDEO);
 ArrayList<Review> reviews = intent.getParcelableArrayListExtra(EXTRA_REVIEW);

 DetailsFragment detailsFragment = DetailsFragment.newInstance(movie, trailer, reviews);
 // Add the fragment to its container using a FragmentManager and a Transaction
 FragmentManager fragmentManager = getSupportFragmentManager();
 fragmentManager.beginTransaction()
 .replace(R.id.movie_detail_container, detailsFragment)
 .commit();

 }**/







