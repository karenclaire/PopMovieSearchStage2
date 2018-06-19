package com.example.android.popmoviesearchstage2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popmoviesearchstage2.adapters.ReviewAdapter;
import com.example.android.popmoviesearchstage2.adapters.TrailerAdapter;
import com.example.android.popmoviesearchstage2.data.FavoriteDBHelper;
import com.example.android.popmoviesearchstage2.model.Movie;
import com.example.android.popmoviesearchstage2.model.Review;
import com.example.android.popmoviesearchstage2.model.Trailer;
import com.example.android.popmoviesearchstage2.model.TrailerResponse;
import com.example.android.popmoviesearchstage2.retrofit.MovieInterface;
import com.example.android.popmoviesearchstage2.retrofit.RetrofitClient;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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

public class DetailsActivity extends AppCompatActivity {

    private static final String LOG_TAG = DetailsActivity.class.getSimpleName();
    private static final String DEBUG_TAG ="DebugStuff";

    public static final String EXTRA_MOVIE = "intent_extra_movie";
    public static final String EXTRA_POSTER = "intent_poster_movie";
    public static final String EXTRA_VIDEO= "intent_video";
    public static final String EXTRA_VIDEO_SITE= "intent_video_site";
    public static final String EXTRA_REVIEW = "intent_review";
    public static final String EXTRA_REVIEW_SITE = "intent_review_site";

    public ImageButton mFavoriteButton;

    @BindView(R.id.tv_rating)
    TextView ratingTextView;
    @BindView (R.id.tv_date)
    TextView dateTextView;
    @BindView (R.id.movie_poster)
    ImageView posterImageView;
    @BindView(R.id.summary)
    TextView overviewTextView;
    @BindView(R.id.title)
    TextView titleTextView;
    @BindView(R.id.trailer_container)
    ViewGroup trailerContainer;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolBar;
    @BindView( (R.id.appBar))
    AppBarLayout mAppBar;
    @BindView ( R.id.favorite_button)
    MaterialFavoriteButton materialFavoriteButton;


    public List<Movie> moviesList = new ArrayList<>();
    Context mContext;
    private Movie mMovie;
    private String posterPath;
    public static final String VIDEO_PATH = "https://www.youtube.com/watch?v=";

    private List <Review> mReviewList = new ArrayList<>();
    public ReviewAdapter mReviewAdapter;

    FavoriteDBHelper favoriteDBHelper;
    public Movie favoriteMovies;
    private final AppCompatActivity activity = DetailsActivity.this;

    public List <Trailer> mTrailerList = new ArrayList<>();
    private final TrailerAdapter mTrailerAdapter = new TrailerAdapter ( this, mTrailerList );
    @BindView ( R.id.trailer_list)
    RecyclerView trailerListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(DEBUG_TAG, "DetailsActivity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initCollapsingToolbar();

        Intent intent = getIntent();
        String moviePath;

       mMovie= intent.getParcelableExtra(DetailsActivity.EXTRA_MOVIE);
        moviePath = intent.getStringExtra(EXTRA_POSTER);

        showMovieDetails(mMovie);

      if (intent.hasExtra ("original_title")){

        /**  Bundle intentExtras = getIntent ().getExtras ();
           String thumbnail = intentExtras.getString ("poster_path");
           String movieTitle =intentExtras.getString ("original_title");
           String overview = intentExtras.getString ("overview");
           String rating = intentExtras.getString ("vote_average");
           String releaseDate = intentExtras.getString ("release_date");**/

        Picasso.with(mContext).setLoggingEnabled(true);

        Picasso.with(mContext)
                .load(moviePath)
                .into(posterImageView);


           /**dateTextView.setText(releaseDate);
           ratingTextView.setText(rating);
           overviewTextView.setText(overview);
           titleTextView.setText(movieTitle);**/

       } else{
           Toast.makeText (this, "No Movie Data Available", Toast.LENGTH_SHORT).show();
       }

       SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext ());
       materialFavoriteButton.setOnFavoriteChangeListener( new MaterialFavoriteButton.OnFavoriteChangeListener(){
                   @Override
                   public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                    if (favorite){
                        SharedPreferences.Editor editor = getSharedPreferences
                                ("com.example.android.popmoviesearchstage2.DetailsActivity", MODE_PRIVATE).edit();
                        editor.putBoolean ("Favorite Added", true);
                        editor.apply();
                        saveFavorite();
                        Snackbar.make ( buttonView, "Added to Favorite", Snackbar.LENGTH_SHORT ).show ();
                    } else{
                        int id = getIntent ().getExtras ().getInt("id");
                        favoriteDBHelper = new FavoriteDBHelper (activity);
                        favoriteDBHelper.deleteFavorite (id);
                        SharedPreferences.Editor editor = getSharedPreferences
                                ("com.example.android.popmoviesearchstage2.DetailsActivity", MODE_PRIVATE).edit();
                        editor.putBoolean ("Favorite Removed", true);
                        editor.apply();
                        Snackbar.make ( buttonView, "Removed from Favorite", Snackbar.LENGTH_SHORT ).show ();

                    }
                   }



       });

        initViews ();
    }

     public void showMovieDetails(Movie mMovie) {
     dateTextView.setText(mMovie.getReleaseDate());
     ratingTextView.setText(mMovie.getVoteAverage());
     overviewTextView.setText(mMovie.getOverview());
     titleTextView.setText(mMovie.getTitle());
     }


    public void initCollapsingToolbar(){
        mCollapsingToolBar.setTitle("");
        mAppBar.setExpanded(true);
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1){
                    appBarLayout.getTotalScrollRange();
                } if (scrollRange + verticalOffset == 0){
                    mCollapsingToolBar.setTitle(getString(R.string.title_activity_detail));
                    isShow = true;
                }else if (isShow){
                    mCollapsingToolBar.setTitle("");
                    isShow = false;

                }
            }
        });

    }

    private void initViews() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager (getApplicationContext ());
        trailerListRecyclerView.setLayoutManager (layoutManager);
        trailerListRecyclerView.setAdapter (mTrailerAdapter);
        mTrailerAdapter.notifyDataSetChanged ();

        loadTrailers();

    }

    private void loadTrailers() {
        int mMovieId = getIntent ().getExtras ().getInt ( "id" );
        try {
            if (BuildConfig.API_KEY.isEmpty ()) {
                Toast.makeText ( getApplicationContext (), "Please get API from themoviedb.org", Toast.LENGTH_SHORT ).show ();

            }

            RetrofitClient retrofitClient = new RetrofitClient ();
            MovieInterface movieInterface = retrofitClient.getRetrofitClient ().create ( MovieInterface.class );
            Call<TrailerResponse> call = movieInterface.getTrailers ( mMovieId, BuildConfig.API_KEY );
            call.enqueue ( new Callback<TrailerResponse> () {

                @Override
                public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                    mTrailerList = response.body ().getTrailerList ();

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
        favoriteDBHelper = new FavoriteDBHelper ( activity );
        favoriteMovies = new Movie ();
        int id = getIntent ().getExtras ().getInt ( "id" );
        String voteAverage = getIntent ().getExtras ().getString ("vote_average" );
        String movieTitle =  getIntent ().getExtras ().getString ("original_title" );
        String releaseDate = getIntent ().getExtras ().getString ("release_date" );
        String posterPath = getIntent ().getExtras ().getString ("poster_path" );

        favoriteMovies.setId (id);
        favoriteMovies.setTitle ( movieTitle);
        favoriteMovies.setVoteAverage (voteAverage);
        favoriteMovies.setReleaseDate (releaseDate );
        favoriteMovies.setPosterPath (posterPath );

        favoriteDBHelper.addFavorites (favoriteMovies);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
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







