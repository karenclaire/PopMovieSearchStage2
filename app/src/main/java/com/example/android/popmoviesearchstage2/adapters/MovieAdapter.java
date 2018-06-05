package com.example.android.popmoviesearchstage2.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.popmoviesearchstage2.DetailsActivity;
import com.example.android.popmoviesearchstage2.R;
import com.example.android.popmoviesearchstage2.model.Movie;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    boolean favorite = true;

    //final private MovieAdapterOnClickListener mOnClickListener;

    private List<Movie> moviesList;

    Cursor mCursor;

    CursorLoader mCursorLoader;

    private int mPosition = -1;
    public ImageView posterImageView;

    public TextView dateTextView;
    public TextView ratingTextView;
    private Movie currentMovie;
    private RecyclerView mRecyclerView;

    private ImageButton mFavoriteButton;
    private CardView mCardView;
    String poster;


    private static final int VIEW_TYPE_TOP_RATED = 1;
    private static final int VIEW_TYPE_FAVORITE = 2;
    private static final int VIEW_TYPE_POPULAR = 0;

    //public interface MovieAdapterOnClickListener {
     //   void onClick(MovieAdapterViewHolder viewHolder); }

    public MovieAdapter(Context mContext, List<Movie> moviesList) {
        this.mContext = mContext;
        this.moviesList = moviesList;
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        if (parent instanceof RecyclerView) {
            Context mContext = parent.getContext();
            int layoutId = R.layout.movie_list_item;
            LayoutInflater inflater = LayoutInflater.from(mContext);

            View view = inflater.inflate(layoutId, parent, false);
            MovieAdapterViewHolder holder = new MovieAdapterViewHolder(view);

            return holder;
        } else {
            throw new RuntimeException("Not bound to RecyclerView");
        }
    }


    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {

        String releaseDate = (moviesList.get(position).getReleaseDate());
        releaseDate = getYear(releaseDate);


        moviesList.get(position);
        holder.ratingTextView.setText(moviesList.get(position).getVoteAverage());
        //holder.dateTextView.setText(moviesList.get(position).getReleaseDate());
        holder.dateTextView.setText(releaseDate);

        poster = POSTER_PATH + moviesList.get(position).getPosterPath();


        Picasso.with(mContext)
                .load(poster)
                .into(holder.posterImageView);

    }

    /**
     * if (!mCursor.moveToFirst()) {
     * return;
     * }
     * <p>
     * switch (viewType) {
     * <p>
     * case VIEW_TYPE_POPULAR:
     * <p>
     * int id = mCursor.getInt(mCursor.getColumnIndex(PopularMovieEntry._ID));
     * int releaseDateColumnIndex = mCursor.getColumnIndex(PopularMovieEntry.COLUMN_RELEASE_DATE);
     * int voteAverageColumnIndex = mCursor.getColumnIndex(PopularMovieEntry.COLUMN_VOTE_AVERAGE);
     * int posterColumnIndex = mCursor.getColumnIndex(PopularMovieEntry.COLUMN_POSTER_PATH);
     * <p>
     * final String releaseDate = mCursor.getString(releaseDateColumnIndex);
     * final float voteAverage = (mCursor.getFloat(voteAverageColumnIndex));
     * final String posterPath = mCursor.getString(posterColumnIndex);
     * <p>
     * final String voteAverageOutput = String.valueOf(voteAverage);
     * <p>
     * holder.dateTextView.setText(releaseDate);
     * holder.ratingTextView.setText(voteAverageOutput);
     * <p>
     * Picasso.with(mContext)
     * .load(posterPath)
     * .into(holder.posterImageView);
     * <p>
     * break;
     * <p>
     * case VIEW_TYPE_TOP_RATED:
     * int topRatedId = mCursor.getInt(mCursor.getColumnIndex(TopRatedMovieEntry._ID));
     * int topRatedReleaseDateColumnIndex = mCursor.getColumnIndex(TopRatedMovieEntry.COLUMN_RELEASE_DATE);
     * int topRatedVoteAverageColumnIndex = mCursor.getColumnIndex(TopRatedMovieEntry.COLUMN_VOTE_AVERAGE);
     * int topRatedPosterColumnIndex = mCursor.getColumnIndex(TopRatedMovieEntry.COLUMN_POSTER_PATH);
     * <p>
     * final String topRatedReleaseDate = mCursor.getString(topRatedReleaseDateColumnIndex);
     * final float topRatedVoteAverage = (mCursor.getFloat(topRatedVoteAverageColumnIndex));
     * final String topRatedPosterPath = mCursor.getString(topRatedPosterColumnIndex);
     * <p>
     * final String topRatedVoteAverageOutput = String.valueOf(topRatedVoteAverage);
     * <p>
     * holder.dateTextView.setText(topRatedReleaseDate);
     * holder.ratingTextView.setText(topRatedVoteAverageOutput);
     * <p>
     * Picasso.with(mContext)
     * .load(topRatedPosterPath)
     * .into(holder.posterImageView);
     * break;
     * <p>
     * case VIEW_TYPE_FAVORITE:
     * int favoriteRatedId = mCursor.getInt(mCursor.getColumnIndex(FavoriteMovieEntry._ID));
     * int favoriteReleaseDateColumnIndex = mCursor.getColumnIndex(FavoriteMovieEntry.COLUMN_RELEASE_DATE);
     * int favoriteVoteAverageColumnIndex = mCursor.getColumnIndex(FavoriteMovieEntry.COLUMN_VOTE_AVERAGE);
     * int favoritePosterColumnIndex = mCursor.getColumnIndex(FavoriteMovieEntry.COLUMN_POSTER_PATH);
     * <p>
     * final String favoriteReleaseDate = mCursor.getString(favoriteReleaseDateColumnIndex);
     * final float favoriteVoteAverage = (mCursor.getFloat(favoriteVoteAverageColumnIndex));
     * final String favoritePosterPath = mCursor.getString(favoritePosterColumnIndex);
     * <p>
     * final String favoriteVoteAverageOutput = String.valueOf(favoriteVoteAverage);
     * <p>
     * holder.dateTextView.setText(favoriteReleaseDate);
     * holder.ratingTextView.setText(favoriteVoteAverageOutput);
     * <p>
     * Picasso.with(mContext)
     * .load(favoritePosterPath)
     * .into(holder.posterImageView);
     * break;
     * <p>
     * }
     **/
    @Override
    public int getItemCount() {
        // if (null == mCursor) return 0;
        // return mCursor.getCount();
        //}
        //return (moviesList == null) ? 0 : moviesList.size();
        return moviesList.size();

        // public void swapCursor(Cursor newCursor) {
        //     mCursor = newCursor;
//      After the new Cursor is set, call notifyDataSetChanged
        //    notifyDataSetChanged();
        //}
    }

     public class MovieAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_rating)
        TextView ratingTextView;
        @BindView(R.id.tv_date)
        TextView dateTextView;
        @BindView(R.id.movie_poster)
        ImageView posterImageView;
        @BindView(R.id.favorite_button)
        ImageButton mFavoriteButton;
        @BindView(R.id.detail_cardview)
        CardView mCardView;
        @BindView(R.id.movie_list_item_layout)
        LinearLayout mListItemLayout;


        MovieAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);



        view.setOnClickListener(view1 -> {
            int position = getAdapterPosition();
            if (position != mRecyclerView.NO_POSITION){

                Movie currentMovie = moviesList.get(position);
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra("vote_average", currentMovie.getVoteAverage());
                intent.putExtra("released_date", currentMovie.getReleaseDate());
                intent.putExtra("poster_path",currentMovie.getPosterPath());
                intent.putExtra("overview", currentMovie.getOverview());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }


        });




        // Set an item click listener on the RecyclerView, which sends an intent to a web browser
        //@Override
        //public void onClick(View view) {
        //    int adapterPosition = getAdapterPosition();
        //    mCursor.moveToPosition(adapterPosition);

        //    int movie_id_column = mCursor.getColumnIndex(MovieContract.PopularMovieEntry.COLUMN_MOVIE_ID);
        //    if (movie_id_column == -1) {
         //       return;
         //   }
         //   long movie_id = mCursor.getLong(movie_id_column);
         //   mOnClickListener.onMovieClick(movie_id, this);  // pass the movie ID to the fragment
        }
     }

    public static String getYear(String dateString){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = parser.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.YEAR));


    }

}






