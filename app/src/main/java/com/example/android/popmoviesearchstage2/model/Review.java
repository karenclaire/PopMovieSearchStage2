package com.example.android.popmoviesearchstage2.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Review implements Parcelable {

    private int mId;
    private String mAuthor;
    private String mContent;
    private String mReviewPath;

    public int getId() {
        return mId;
    }


    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }


    //Get Review Path
    public String getReviewPath() {
        return mReviewPath;
    }

    public void setReviewPath(String reviewPath) {
        mReviewPath = reviewPath;
    }

    public String getUrl() {
        return url;
    }

    private String url;

    public Review(int id, String author, String content, String reviewPath) {
        mId = id;
        mAuthor = author;
        mContent = content;
        mReviewPath = reviewPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(mId);
        dest.writeString(mAuthor);
        dest.writeString(mContent);
        dest.writeString(mReviewPath);


    }

    protected Review(Parcel in) {
        mId = in.readInt();
        mAuthor = in.readString();
        mContent = in.readString();
        mReviewPath = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
}

