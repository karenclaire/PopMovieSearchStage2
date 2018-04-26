package com.example.android.popmoviesearchstage2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popmoviesearchstage2.adapters.ReviewAdapter;
import com.example.android.popmoviesearchstage2.adapters.ReviewAdapter.ReviewAdapterListener;
import com.example.android.popmoviesearchstage2.adapters.TrailerAdapter;
import com.example.android.popmoviesearchstage2.adapters.TrailerAdapter.TrailerAdapterListener;
import com.example.android.popmoviesearchstage2.model.Movie;
import com.example.android.popmoviesearchstage2.model.Review;
import com.example.android.popmoviesearchstage2.model.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * References for code correction and guidance
 *  https://github.com/karenclaire/EscrimaInventoryApp
 *  https://www.youtube.com/watch?v=OOLFhtyCspA
 *  https://github.com/FrangSierra/Udacity-Popular-Movies-Stage-2
 *  https://github.com/SubhrajyotiSen/Popular-Movies-2
 *  https://github.com/jimandreas/PopularMovies
 */

public class DetailsFragment extends Fragment {
    public static final String EXTRA_MOVIE = "intent_extra_movie";
    public static final String EXTRA_POSTER = "intent_poster_movie";
    public static final String EXTRA_VIDEO = "intent_video";
    public static final String EXTRA_REVIEW = "intent_review";

    public static final String REVIEW_PATH ="http://api.themoviedb.org/3/movie/" + "id" + "?&append_to_response=reviews";
    final String MOVIE_ID = "id";

    private RecyclerView trailerRecycler;
    public ImageButton mFavoriteButton;
    private RecyclerView reviewRecycler;

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

    public List<Movie> moviesList = new ArrayList<>();
    Context mContext;
    private Movie mMovie;
    private String posterPath;
    public static final String VIDEO_PATH = "https://www.youtube.com/watch?v=";

    public List<Trailer> mTrailerList = new ArrayList<>();
    private TrailerAdapter mTrailerAdapter;
    private TrailerAdapterListener mTrailerAdapterListener;
    private Trailer mTrailer;

    private ArrayList<Review> mReviewList = new ArrayList<>();
    public ReviewAdapter mReviewAdapter;
    public ReviewAdapterListener mReviewAdapterListener;
    private Review mReview;

    public static DetailsFragment newInstance(Movie movie, List<Trailer> trailer, List<Review> reviews) {
        DetailsFragment detailsFragment = new DetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_MOVIE, movie);

        if (trailer != null && trailer.size() > 0)
            bundle.putParcelableArrayList(EXTRA_VIDEO, new ArrayList<>(trailer));
        if (reviews != null && reviews.size() > 0)
            bundle.putParcelableArrayList(EXTRA_REVIEW, new ArrayList<>(reviews));

        return detailsFragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_details, container, false);
        ButterKnife.bind(this, rootView);
        //Intent intent = getIntent();

        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_MOVIE, mMovie);
        bundle.getParcelable(EXTRA_POSTER);

        //mMovie = intent.getParcelableExtra(DetailsActivity.EXTRA_MOVIE);
        //posterPath = intent.getStringExtra(EXTRA_POSTER);
        //mTrailerList = intent.getParcelableArrayListExtra(EXTRA_VIDEO);

        //if (mTrailerList != null)
        if (mTrailerList != null && mTrailerList.size() > 0)
            bundle.putParcelableArrayList(EXTRA_VIDEO, new ArrayList<>(mTrailerList));
            showTrailers();

        //if (mReviewList != null)
        if (mReviewList != null && mReviewList.size() > 0)
           bundle.putParcelableArrayList(EXTRA_REVIEW, new ArrayList<>(mReviewList));
            showReviews();

        showMovieDetails(mMovie);

        Picasso.with(mContext).setLoggingEnabled(true);

        Picasso.with(mContext)
                .load(posterPath)
                .into(posterImageView);

        return rootView;

    }


    public void showMovieDetails(Movie mMovie) {
        dateTextView.setText(mMovie.getReleaseDate());
        ratingTextView.setText(mMovie.getVoteAverage());
        overviewTextView.setText(mMovie.getOverview());
        titleTextView.setText(mMovie.getTitle());
    }

    public void showTrailers() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        trailerRecycler.setLayoutManager(linearLayoutManager);
        mTrailerAdapter = new TrailerAdapter(mContext, mTrailerList, mTrailerAdapterListener);
        trailerRecycler.setAdapter(mTrailerAdapter);
        trailerContainer.setVisibility(View.VISIBLE);

       /** if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            trailerRecycler.addOnItemTouchListener(new TrailerAdapterListener(getContext(), (view, position) -> {
                mTrailer = mTrailerList.get(position);
                String url = "https://www.youtube.com/watch?v=".concat(mTrailer.getKey());
                //String url = "http://api.themoviedb.org/3/movie/" + MOVIE_ID + "?&append_to_response=trailers";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }));**/
        }

    public void showReviews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        reviewRecycler.setLayoutManager(linearLayoutManager);
        mReviewAdapter = new ReviewAdapter(mContext, mReviewList, mReviewAdapterListener);
        reviewRecycler.setAdapter(mReviewAdapter);
        reviewContainer.setVisibility(View.VISIBLE);

        /**if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            reviewRecycler.addOnItemTouchListener(new ReviewAdapterListener(getContext(), (view, position) -> {
                mReview = mReviewList.get(position);
                String url = "http://api.themoviedb.org/3/movie/" + MOVIE_ID + "?&append_to_response=reviews";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }));
        }**/
    }
    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (moviesList != null) {
            outState.putParcelableArrayList(EXTRA_VIDEO, new ArrayList<>(moviesList));
        }
        if (mTrailerList != null) {
            outState.putParcelableArrayList(EXTRA_VIDEO, new ArrayList<>(mTrailerList));
        }
        if (mReviewList != null) {
            outState.putParcelableArrayList(EXTRA_REVIEW, new ArrayList<>(mReviewList));
        }
    }
}

