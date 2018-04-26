package com.example.android.popmoviesearchstage2.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popmoviesearchstage2.DetailsActivity;
import com.example.android.popmoviesearchstage2.R;
import com.example.android.popmoviesearchstage2.model.Review;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.popmoviesearchstage2.DetailsActivity.EXTRA_REVIEW;


/**
 * References for code correction and guidance
 *  https://github.com/karenclaire/EscrimaInventoryApp
 *  https://www.youtube.com/watch?v=OOLFhtyCspA
 *  https://github.com/FrangSierra/Udacity-Popular-Movies-Stage-2
 *  https://github.com/SubhrajyotiSen/Popular-Movies-2
 *  https://github.com/jimandreas/PopularMovies
 */


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private ArrayList<Review> mReviewList;
    private ReviewAdapter.ReviewAdapterListener mReviewAdapterListener;
    private Context mContext;
    private TextView trailerTextView;
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

    public ReviewAdapter(Context context, ArrayList<Review> reviewList, ReviewAdapter.ReviewAdapterListener reviewAdapterListener) {
        this.mContext = context;
        this.mReviewList = reviewList;
        this.mReviewAdapterListener = reviewAdapterListener;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context mContext = parent.getContext();
        int layoutId = R.layout.review_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);


        View view = inflater.inflate(layoutId, parent, false);
        ReviewViewHolder holder= new ReviewViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        Bundle bundle = new Bundle();

        review = mReviewList.get(position);
        holder.authorTextView.setText(review.getAuthor());
        holder.contentTextView.setText(review.getContent());


        if (mReviewList != null && mReviewList.size() > 0) {
            bundle.putParcelableArrayList(EXTRA_REVIEW, new ArrayList<>(mReviewList));

            reviewRecycler.setOnClickListener(new View.OnClickListener() {
                @Override
                    public void onClick(View view) {
                        Review reviewInformation = new Review(review.getId(), review.getAuthor(),
                                review.getContent(), review.getReviewPath());
                        String REVIEW_PATH = "http://api.themoviedb.org/3/movie/" + MOVIE_ID + "?&append_to_response=reviews";

                        Intent intent = new Intent(mContext, DetailsActivity.class);
                        intent.putExtra(DetailsActivity.EXTRA_VIDEO, reviewInformation);
                        intent.putExtra(DetailsActivity.EXTRA_REVIEW_SITE, REVIEW_PATH + review.getReviewPath());
                        mContext.startActivity(intent);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (mReviewList == null) ? 0 : mReviewList.size();


    }


    class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.review_author)
        TextView authorTextView;
        @BindView(R.id.review_content)
        TextView contentTextView;
        @BindView(R.id.reviews_list)
        RecyclerView reviewRecycler;
        View view;


        ReviewViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener((View.OnClickListener) this);
        }

        // Set an item click listener on the RecyclerView, which sends an intent to a web browser to
        //access chosen trailer
        @Override
        public void onClick(View view) {
            Review reviewInformation = new Review(review.getId(), review.getAuthor(),
                    review.getContent(), review.getReviewPath());
            String REVIEW_PATH ="http://api.themoviedb.org/3/movie/" + MOVIE_ID + "?&append_to_response=reviews";

            Intent intent = new Intent(mContext, DetailsActivity.class);
            intent.putExtra(DetailsActivity.EXTRA_VIDEO, reviewInformation);
            intent.putExtra(DetailsActivity.EXTRA_REVIEW_SITE, REVIEW_PATH + review.getReviewPath());
            mContext.startActivity(intent);

        }

        /**
         * Retrieve a {@link Review} item from the adapter list based on a given position.
         */
        //public Review getReviewFromPosition(int position) {
            //return mReviewList.get(position);}
        public Review get(int position) {return mReviewList.get(position);}

        private void setTrailerName(String name) {
            trailerTextView.setText(name);
        }

        public ArrayList<Review> getReviewList() {
            return mReviewList;
        }

        //Set an onClickListener for the TrailerAdapter
        private void setOnClickListener(ReviewAdapter.ReviewAdapterListener mReviewAdapterListener, final int position) {
            view.setOnClickListener(v -> mReviewAdapterListener.onReviewClick(position));
        }


        public void addAll(ArrayList<Review> review) {
            this.addAll(review);
            notifyDataSetChanged();

        }







        }
    }

