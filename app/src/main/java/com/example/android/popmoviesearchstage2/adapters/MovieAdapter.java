package com.example.android.popmoviesearchstage2.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popmoviesearchstage2.DetailsActivity;
import com.example.android.popmoviesearchstage2.R;
import com.example.android.popmoviesearchstage2.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karenulmer on 2/18/2018.
 *
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    /* The context we use to utility methods, app resources and layout inflaters */
    private Context mContext;

    public static final String POSTER_PATH = "http://image.tmdb.org/t/p/w185//";



    final private MovieAdapterOnClickListener mOnClickListener;

    private ArrayList<Movie> moviesList;


    public ImageView posterImageView;

    public TextView dateTextView;
    public TextView ratingTextView;
    private Movie currentMovie;
    private RecyclerView mRecyclerView;

    private ImageButton mFavoriteButton;
    private CardView mCardView;



    public interface MovieAdapterOnClickListener {
        void onMovieClick(int clickMovieIndex);

    }

    public MovieAdapter(Context mContext, ArrayList<Movie> moviesList, MovieAdapterOnClickListener listener) {
        this.mContext = mContext;
        this.moviesList = moviesList;
        this.mOnClickListener = listener;


    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context mContext = parent.getContext();
        int layoutId = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(layoutId, parent, false);
        MovieAdapterViewHolder holder = new MovieAdapterViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {

        currentMovie = moviesList.get(position);
        holder.ratingTextView.setText(currentMovie.getVoteAverage());
        holder.dateTextView.setText(currentMovie.getReleaseDate());


        Picasso.with(mContext)
                .load(MovieAdapter.POSTER_PATH + currentMovie.getPosterPath())
                .into(holder.posterImageView);


    }

    @Override
    public int getItemCount() {
        return (moviesList == null) ? 0 : moviesList.size();
        //return moviesList.size();
    }



    class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_rating)
        TextView ratingTextView;
        @BindView(R.id.tv_date)
        TextView dateTextView;
        @BindView(R.id.movie_poster)
        ImageView posterImageView;
        @BindView(R.id.favorite_button)
        ImageButton mFavoriteButton;
        @BindView (R.id.detail_cardview)
        CardView mCardView;



        MovieAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);

            //set the onClickListener to the CardView
            mCardView.setOnClickListener(this);

        }

        // Set an item click listener on the RecyclerView, which sends an intent to a web browser
        @Override
        public void onClick(View view) {
            Movie movieInformation = new Movie(currentMovie.getId(), currentMovie.getTitle(),
                    currentMovie.getReleaseDate(), currentMovie.getOverview(),
                    currentMovie.getVoteAverage(), currentMovie.getPosterPath());

            Intent intent = new Intent(mContext, DetailsActivity.class);
            intent.putExtra(DetailsActivity.EXTRA_MOVIE, movieInformation);
            intent.putExtra(DetailsActivity.EXTRA_POSTER, POSTER_PATH + currentMovie.getPosterPath());
            mContext.startActivity(intent);

        }
    }


    public void setMovieList(List<Movie> moviesList) {
        this.moviesList.clear();
        this.moviesList.addAll(moviesList);
        // The adapter needs to know that the data has changed
        notifyDataSetChanged();
    }


}