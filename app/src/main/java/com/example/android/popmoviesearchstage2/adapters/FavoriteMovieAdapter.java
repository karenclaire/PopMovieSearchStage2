package com.example.android.popmoviesearchstage2.adapters;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popmoviesearchstage2.MainActivity;
import com.example.android.popmoviesearchstage2.R;
import com.example.android.popmoviesearchstage2.data.MovieContract.FavoriteMovieEntry;

import static com.example.android.popmoviesearchstage2.utils.MovieUtils.getYear;

public class FavoriteMovieAdapter extends CursorAdapter{

    public static final String POSTER_PATH = "http://image.tmdb.org/t/p/w185//";
   static public String favoriteMovie;

    public FavoriteMovieAdapter(MainActivity context, Cursor c) {
        super(context, c, 0);

    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.movie_list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Location of Views
        TextView releaseDateTextView = view.findViewById(R.id.tv_date);
        TextView voteAverageTextView = view.findViewById(R.id.tv_rating);
        TextView titleTextView = view.findViewById(R.id.title);
        TextView overviewTextView = view.findViewById(R.id.summary);
        ImageView posterImageView = view.findViewById(R.id.movie_poster);
        TextView trailer1TextView = view.findViewById(R.id.trailer1_name);
        TextView trailer2TextView = view.findViewById(R.id.trailer2_name);
        TextView trailer3TextView = view.findViewById(R.id.trailer3_name);
        ImageButton favoriteButton = view.findViewById(R.id.favorite_button);
        TextView reviewAuthor1TextView = view.findViewById(R.id.tv_review_author1);
        TextView reviewAuthor2TextView = view.findViewById(R.id.tv_review_author2);
        TextView reviewAuthor3TextView = view.findViewById(R.id.tv_review_author3);
        TextView review1TextView = view.findViewById(R.id.tv_review1);
        TextView review2TextView = view.findViewById(R.id.tv_review2);
        TextView review3TextView = view.findViewById(R.id.tv_review3);

        //Read the attributes of the movie from the Cursor for the current movie.
        int id = cursor.getInt(cursor.getColumnIndex(FavoriteMovieEntry._ID));
        int titleColumnIndex = cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_TITLE);
        int releaseDateColumnIndex = cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_RELEASE_DATE);
        int voteAverageColumnIndex = cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_VOTE_AVERAGE);
        int overviewColumnIndex = cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_OVERVIEW);
        int posterColumnIndex = cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_POSTER_PATH);
        int trailer1ColumnIndex = cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_TRAILER1_NAME);
        int trailer2ColumnIndex = cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_TRAILER2_NAME);
        int trailer3ColumnIndex = cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_TRAILER3_NAME);
        int favoriteColumnIndex = cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_FAVORITE_MOVIE);
        int reviewAuthor1ColumnIndex = cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_REVIEW_AUTHOR);
        int reviewAuthor2ColumnIndex = cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_REVIEW2_AUTHOR);
        int reviewAuthor3ColumnIndex = cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_REVIEW3_AUTHOR);
        int review1ColumnIndex = cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_REVIEW_CONTENT);
        int review2ColumnIndex = cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_REVIEW_CONTENT2);
        int review3ColumnIndex = cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_REVIEW_CONTENT3);

        final Uri currentMovieUri = ContentUris.withAppendedId(FavoriteMovieEntry.CONTENT_URI, id);

        final String title = cursor.getString(titleColumnIndex);
        final String releaseDate = cursor.getString(releaseDateColumnIndex);
        final Float voteAverage = cursor.getFloat(voteAverageColumnIndex);
        final String overview = cursor.getString(overviewColumnIndex);
        final String posterPath = cursor.getString(posterColumnIndex);
        final String trailer1 = cursor.getString(trailer1ColumnIndex);
        final String trailer2 = cursor.getString(trailer2ColumnIndex);
        final String trailer3 = cursor.getString(trailer3ColumnIndex);
        final Integer favorite = cursor.getInt(favoriteColumnIndex);
        final String reviewAuthor1 = cursor.getString(reviewAuthor1ColumnIndex);
        final String reviewAuthor2 = cursor.getString(reviewAuthor2ColumnIndex);
        final String reviewAuthor3 = cursor.getString(reviewAuthor3ColumnIndex);
        final String review1 = cursor.getString(review1ColumnIndex);
        final String review2 = cursor.getString(review2ColumnIndex);
        final String review3 = cursor.getString(review3ColumnIndex);

        // Update the TextViews with the attributes for the current favorite movie
        titleTextView.setText(title);
        releaseDateTextView.setText(String.valueOf(getYear(releaseDate)));
        overviewTextView.setText(overview);
        voteAverageTextView.setText(String.valueOf(voteAverage));
        trailer1TextView.setText(trailer1);
        trailer2TextView.setText(trailer2);
        trailer3TextView.setText(trailer3);
        reviewAuthor1TextView.setText(reviewAuthor1);
        reviewAuthor2TextView.setText(reviewAuthor2);
        reviewAuthor3TextView.setText(reviewAuthor3);
        review1TextView.setText(review1);
        review2TextView.setText(review2);
        review3TextView.setText(review3);

        posterImageView.setImageURI(Uri.parse(cursor.getString(cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_POSTER_PATH))));




        // OnClicklistener for favorite button
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri;
                int favorite;
                ContentResolver resolver = v.getContext().getContentResolver();

                ContentValues values = new ContentValues();
                // If the isFaveMovie equal to favoriteMovie, the currentMovie list will be added to the Favorite Movie List
                boolean isFaveMovie = false;
                if (favoriteMovie != null && favoriteMovie.equals(!isFaveMovie)){
                    uri = FavoriteMovieEntry.buildMovieDetailsUri(id);
                    resolver.update(
                            currentMovieUri,
                            values,
                            null,
                            null

                    );

                    favoriteButton.setImageResource(R.drawable.yellow_star);
                    Toast.makeText(context, "Movie is now in Favorite list", Toast.LENGTH_SHORT).show();;
                    context.getContentResolver().notifyChange(currentMovieUri, null);
                } else {
                    // Not a favorite
                    favoriteButton.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                    Toast.makeText(context, "Movie not in Favorite List", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}


