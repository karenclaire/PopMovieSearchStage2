package com.example.android.popmoviesearchstage2.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popmoviesearchstage2.R;
import com.example.android.popmoviesearchstage2.model.Review;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.ACTION_VIEW;
import static com.example.android.popmoviesearchstage2.DetailsActivity.EXTRA_REVIEW;


/**
 * References for code correction and guidance
 * https://github.com/karenclaire/EscrimaInventoryApp
 * https://www.youtube.com/watch?v=OOLFhtyCspA
 * https://github.com/FrangSierra/Udacity-Popular-Movies-Stage-2
 * https://github.com/SubhrajyotiSen/Popular-Movies-2
 * https://github.com/jimandreas/PopularMovies
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
    @Nullable
    @BindView(R.id.reviews_list)
    RecyclerView reviewRecycler;

    public interface ReviewAdapterListener {
        void onReviewClick(int position);
    }

    public ReviewAdapter(Context context, List<Review> reviewList) {
        this.mContext = context;
        this.mReviewList = reviewList;

    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context mContext = parent.getContext ();
        int layoutId = R.layout.review_item;
        LayoutInflater inflater = LayoutInflater.from ( parent.getContext () );


        View view = inflater.inflate ( layoutId, parent, false );
        //ReviewViewHolder holder = new ReviewViewHolder ( view );
        //return holder;
        return new ReviewViewHolder ( view );

    }


    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        Bundle bundle = new Bundle ();

        review = mReviewList.get ( position );
        holder.authorTextView.setText ( review.getAuthor () );
        holder.contentTextView.setText ( review.getContent () );


    }

    public void loadReviews(List<Review> reviewList, Context mContext) {
        Log.d ( DEBUG_TAG, "ReviewAdapter loadReviews" );
        this.mContext = mContext;
        if (reviewList != null && !reviewList.isEmpty ()) {
            this.mReviewList = reviewList;
            notifyDataSetChanged ();
        }
    }

    @Override
    public int getItemCount() {
        return (mReviewList == null) ? 0 : mReviewList.size ();


    }


    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.review_author)
        TextView authorTextView;
        @BindView(R.id.review_content)
        TextView contentTextView;
        @Nullable
        @BindView(R.id.reviews_list)
        RecyclerView reviewRecycler;
        View view;


        ReviewViewHolder(View view) {
            super ( view );
            ButterKnife.bind ( this, view );
            view.setOnClickListener ( new View.OnClickListener () {


                @Override
                public void onClick(View v) {
                    Log.d ( DEBUG_TAG, "ReviewAdapter onClick" );

                    int position = getAdapterPosition ();
                    if (position != RecyclerView.NO_POSITION) {
                        
                        Context context = view.getContext ();
                        //String reviewPath = "https://www.themoviedb.org/review/" + review.getId ();
                        String reviewPath = review.getId ();
                        //Intent intent = new Intent ();
                        Intent intent = new Intent ( ACTION_VIEW, Uri.parse ( "https://www.themoviedb.org/review/" + reviewPath ) );
                        intent.putExtra ( EXTRA_REVIEW, reviewPath );
                        context.startActivity ( intent );


                    }
                }


            } );
        }
    }

}

