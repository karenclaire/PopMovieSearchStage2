package com.example.android.popmoviesearchstage2.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Review implements Parcelable {

    @SerializedName ("id" )
    private String mId;

    @SerializedName ( "author")
    private String mAuthor;

    @SerializedName ( "content")
    private String mContent;
    //private String mReviewPath;

    @SerializedName ( "url")
    private String mUrl;


    public String getId() {
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
    //public String getReviewPath() {return mReviewPath; }

   // public void setReviewPath(String reviewPath) { mReviewPath = reviewPath; }

    // Get Review URL
    public String getUrl() {
        return mUrl;
    }


    public Review(String id, String author, String content, String url) {
        mId = id;
        mAuthor = author;
        mContent = content;
       mUrl = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(mId);
        dest.writeString(mAuthor);
        dest.writeString(mContent);
        dest.writeString(mUrl);


    }

    protected Review(Parcel in) {
        mId = in.readString();
        mAuthor = in.readString();
        mContent = in.readString();
        mUrl = in.readString();
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

