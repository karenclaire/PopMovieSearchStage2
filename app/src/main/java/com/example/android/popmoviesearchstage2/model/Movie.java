package com.example.android.popmoviesearchstage2.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by karenulmer on 2/18/2018.
 */

public class Movie implements Parcelable {


        /**
         * Movie title
         */
        private String mTitle;

        /**
         * Movie Release Date
         */
        private String mReleaseDate;
        /**
         * Image URL
         */
        private String mImageUrl;

        /**
         *  Overview
         */
        private String mOverview;

        /**
         *  Rating
         */
        private String mVoteAverage;

        private String mPosterPath;

        private boolean mTrailer;

        private boolean mFaveMovie = false;

       int mId;


    /**
     * Create a new movie object from five inputs
         *
         * @param title    is the title of the movie
         * @param releaseDate    is the date of the movie
         * @param posterPath is the poster image of the movie
         * @param overview  is the synopsis of the movie
         */
        public Movie(int id, String title, String releaseDate, String overview, String voteAverage,
                     String posterPath) {
    //

            mTitle = title;
            mReleaseDate = releaseDate;
            //mImageUrl = imageUrl;
            mOverview = overview;
            mVoteAverage = voteAverage;
            mId = id;
            mPosterPath = posterPath;
            //mTrailer = trailer;
            //mFaveMovie = faveMovie;

        }

        protected Movie(Parcel in) {
            mTitle = in.readString();
            mReleaseDate = in.readString();
            //mImageUrl = in.readString();
            mOverview = in.readString();
            mVoteAverage = in.readString();
            mId = in.readInt();
            mPosterPath = in.readString();
            //mTrailer = (in.readByte() != 0);
            //mFaveMovie = (in.readByte() != 0)  ;
        }

        /**
         * Get the title of the movie
         */
        public String getTitle() {
            return mTitle;
        }

        public int getId() {
        return mId;
    }

        /**
         * Get the year movie was released
         */
        public String getReleaseDate() {
            return mReleaseDate;
        }

        /**
         * Get the image URL of the movie
         */
        public String getImageUrl() { return mImageUrl; }

        /**
         * Get the URL of the movie
         */
        public String getOverview() {
            return mOverview;
        }

        /**
         * Get the Rating of the movie
         */
        public String getVoteAverage() {
            return mVoteAverage;
        }

        /**
         * Get the Poster Path of the movie
         */
        public String getPosterPath() {return mPosterPath; }

        public void setPosterPath(String posterPath) {  this.mPosterPath = posterPath;}

        /**
         * Get if Movie has Trailer
         */
        //public boolean isTrailer() {return mTrailer;}

        //public void setTrailer(boolean trailer) {mTrailer = trailer;}

        /**
         * Get if Movie is marked as Favorite
         */

       public boolean isFaveMovie() {return mFaveMovie;}

       public void setFaveMovie(boolean faveMovie) {mFaveMovie = faveMovie;}




        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mTitle);
            dest.writeString(mReleaseDate);
            //dest.writeString(mImageUrl);
            dest.writeString(mOverview);
            dest.writeString(mVoteAverage);
            dest.writeString(mPosterPath);
            dest.writeInt(mId);
            //dest.writeByte((byte) (mTrailer ? 1 : 0));
            dest.writeByte((byte) (mFaveMovie ? 1 : 0));
        }



    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    }

