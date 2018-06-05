package com.example.android.popmoviesearchstage2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popmoviesearchstage2.adapters.ReviewAdapter;
import com.example.android.popmoviesearchstage2.adapters.TrailerAdapter;
import com.example.android.popmoviesearchstage2.model.Movie;
import com.example.android.popmoviesearchstage2.model.Review;
import com.example.android.popmoviesearchstage2.model.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    public static final String EXTRA_MOVIE = "intent_extra_movie";
    public static final String EXTRA_POSTER = "intent_poster_movie";
    public static final String EXTRA_VIDEO= "intent_video";
    public static final String EXTRA_VIDEO_SITE= "intent_video_site";
    public static final String EXTRA_REVIEW = "intent_review";
    public static final String EXTRA_REVIEW_SITE = "intent_review_site";
    private RecyclerView trailerRecycler;
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

    public List<Movie> moviesList = new ArrayList<>();
    Context mContext;
    private Movie mMovie;
    private String posterPath;
    public static final String VIDEO_PATH = "https://www.youtube.com/watch?v=";
    public ArrayList <Trailer> mTrailerList = new ArrayList<>();
    private TrailerAdapter mTrailerAdapter;
    private ArrayList <Review> mReviewList = new ArrayList<>();
    public ReviewAdapter mReviewAdapter;
    private TrailerAdapter.TrailerAdapterListener mTrailerAdapterListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        Picasso.with(mContext).setLoggingEnabled(true);

        Picasso.with(mContext)
                .load(moviePath)
                .into(posterImageView);
    }


    public void showMovieDetails(Movie mMovie) {
        dateTextView.setText(mMovie.getReleaseDate());
        ratingTextView.setText(mMovie.getVoteAverage());
        overviewTextView.setText(mMovie.getOverview());
        titleTextView.setText(mMovie.getTitle());
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







