package com.example.android.popmoviesearchstage2.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popmoviesearchstage2.DetailsActivity;
import com.example.android.popmoviesearchstage2.R;
import com.example.android.popmoviesearchstage2.model.Review;

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


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private static final String DEBUG_TAG = "DebugStuff";

    private List<Review> mReviewList;
    private Context mContext;
    Review review;
    @BindView(R.id.review_author)
    TextView authorTextView;
    @BindView(R.id.review_content)
    TextView contentTextView;
    @BindView(R.id.reviews_list)
    RecyclerView reviewRecycler;

    static final String MOVIE_ID = " ";

    public interface ReviewAdapterListener {
        void onReviewClick(int position);
    }

    public ReviewAdapter(Context context, List<Review> reviewList) {
        //ReviewAdapter.ReviewAdapterListener reviewAdapterListener
        this.mContext = context;
        this.mReviewList = reviewList;
        //this.mReviewAdapterListener = reviewAdapterListener;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context mContext = parent.getContext ();
        int layoutId = R.layout.review_item;
        LayoutInflater inflater = LayoutInflater.from ( mContext );


        View view = inflater.inflate ( layoutId, parent, false );
        ReviewViewHolder holder = new ReviewViewHolder ( view );
        return holder;

    }


    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        Bundle bundle = new Bundle ();

        review = mReviewList.get ( position );
        holder.authorTextView.setText ( review.getAuthor () );
        holder.contentTextView.setText ( review.getContent () );


    }

    @Override
    public int getItemCount() {
        return (mReviewList == null) ? 0 : mReviewList.size ();


    }


    public class ReviewViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.review_author)
        TextView authorTextView;
        @BindView(R.id.review_content)
        TextView contentTextView;
        @BindView(R.id.reviews_list)
        RecyclerView reviewRecycler;
        View view;


        ReviewViewHolder(View view) {
            super ( view );
            ButterKnife.bind ( this, view );
            view.setOnClickListener ( new View.OnClickListener () {

                //if (mReviewList != null && mReviewList.size () > 0) {

                //reviewRecycler.setOnClickListener ( new View.OnClickListener () {


                @Override
                public void onClick(View v) {
                    Log.d ( DEBUG_TAG, "ReviewAdapter onClick" );

                    int position = getAdapterPosition ();
                    if (position != RecyclerView.NO_POSITION) {
                        Review reviewInformation = new Review ( review.getId (), review.getAuthor (),
                                review.getContent (), review.getUrl () );

                        //String reviewPath = "https://www.themoviedb.org/review/" + MOVIE_ID + "?&append_to_response=reviews";

                        String reviewPath = "https://www.themoviedb.org/review/" + String.valueOf ( mReviewList.get ( position ).getId () );
                        Context context = view.getContext();
                        Intent intent = new Intent ( mContext, DetailsActivity.class );
                        intent.putExtra ( DetailsActivity.EXTRA_REVIEW, reviewInformation );
                        intent.putExtra ( DetailsActivity.EXTRA_REVIEW_SITE, reviewPath );
                        context.startActivity ( intent );


                    }
            }



                    });
                }
            }

}

