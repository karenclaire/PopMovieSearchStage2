package com.example.android.popmoviesearchstage2.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private static final String DEBUG_TAG ="DebugStuff";

    /* The context we use to utility methods, app resources and layout inflaters */
    private Context mContext;

    public static final String POSTER_PATH = "http://image.tmdb.org/t/p/w185/";

    boolean favorite = true;

    Movie mMovie;

    private List<Movie> moviesList;

    MovieAdapter mMovieAdapter;


    private int mPosition = -1;
    public ImageView posterImageView;

    public TextView dateTextView;
    public TextView ratingTextView;
    Movie currentMovie;
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
        Picasso.with(holder.posterImageView.getContext())
                .load(poster)
                .into(holder.posterImageView);
    }

    // I am only running this one



    @Override
    public int getItemCount() {

        return moviesList.size();

    }

    public void loadMovies(List<Movie> moviesList, Context mContext){
        Log.d(DEBUG_TAG, "MovieAdapter loadMovies");
        this.mContext = mContext;
        if (moviesList != null && !moviesList.isEmpty()){
            this.moviesList = moviesList; //should work :)
            notifyDataSetChanged ();
        }

    }

     public class MovieAdapterViewHolder extends RecyclerView.ViewHolder {
         @BindView(R.id.tv_rating)
         TextView ratingTextView;
         @BindView(R.id.tv_date)
         TextView dateTextView;
         @BindView(R.id.movie_poster)
         ImageView posterImageView;
         @Nullable
         @BindView(R.id.favorite_button) //found in movie_details
                 ImageButton mFavoriteButton;
         @BindView(R.id.detail_cardview)
         CardView mCardView;
         @BindView(R.id.movie_list_item_layout)
         LinearLayout mListItemLayout;


         MovieAdapterViewHolder(View view) {
             super ( view );
             ButterKnife.bind ( this, view );


             view.setOnClickListener ( new View.OnClickListener () {
                 @Override
                 public void onClick(View view) {
                     Log.d ( DEBUG_TAG, "MovieAdapter:  view.setOnClickListener" );
                     int position = getAdapterPosition ();
                     if (position != mRecyclerView.NO_POSITION) {

                         /**Movie movieInformation = new Movie ( currentMovie.getId (), currentMovie.getTitle (),
                          currentMovie.getReleaseDate (), currentMovie.getOverview (),
                          currentMovie.getVoteAverage (), currentMovie.getPosterPath () );

                          Intent intent = new Intent ( mContext, DetailsActivity.class );
                          intent.putExtra ( DetailsActivity.EXTRA_MOVIE, movieInformation );
                          //intent.putExtra(DetailsActivity.EXTRA_POSTER, poster);
                          intent.putExtra ( DetailsActivity.EXTRA_POSTER, POSTER_PATH + currentMovie.getPosterPath () );
                          mContext.startActivity ( intent );**/




                          /**currentMovie = new Movie(currentMovie.getId (), currentMovie.getTitle (),
                                  currentMovie.getReleaseDate (), currentMovie.getOverview (),
                                  currentMovie.getVoteAverage (), currentMovie.getPosterPath ());**/

                         /**Intent intent = new Intent ( mContext, DetailsActivity.class );
                         intent.putExtra ( DetailsActivity.EXTRA_MOVIE, currentMovie );
                         intent.putExtra ( DetailsActivity.EXTRA_POSTER, POSTER_PATH + currentMovie.getPosterPath () );
                         mContext.startActivity ( intent );**/

                          Context context = view.getContext();
                          Intent intent = new Intent ( context, DetailsActivity.class );
                          intent.putExtra ( DetailsActivity.EXTRA_MOVIE, moviesList.get(position) );
                          // intent.putExtra ( DetailsActivity.EXTRA_POSTER, moviesList.get(position).getPosterPath () );
                          context.startActivity ( intent );

                         /**Movie currentMovie = moviesList.get ( position );
                         Intent intent = new Intent ( mContext, DetailsActivity.class );
                         intent.putExtra ( "vote_average", currentMovie.getVoteAverage () );
                         intent.putExtra ( "released_date", currentMovie.getReleaseDate () );
                         intent.putExtra ( "poster_path", currentMovie.getPosterPath () );
                         intent.putExtra ( "overview", currentMovie.getOverview () );
                         intent.addFlags ( Intent.FLAG_ACTIVITY_NEW_TASK );
                         mContext.startActivity ( intent );**/
                     }


                     //}
                 }
             } );
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






