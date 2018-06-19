package com.example.android.popmoviesearchstage2.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Trailer implements Parcelable{

    public static final String YOUTUBE = "YouTube";
    public static final String TRAILER = "Trailer";
    public static final String TRAILER_PATH ="http://api.themoviedb.org/3/movie/" + "id" + "?&append_to_response=trailers";


    @SerializedName ("key")
    private String mKey;

    @SerializedName ("name")
    private String mName;

    private String mSite;
    private String mTrailerPath;


    public Trailer(String key, String name) {
        //String site, String trailerPath
        //mId = id;
        mKey = key;
        mName = name;
        //mSite = site;
        //mTrailerPath = trailerPath;
    }



    //Get Trailer Key
    public String getKey() { return mKey;}
    public void setKey (String key) {mKey = key;}

    //Get Trailer Name
    public String getName() {return mName;}
    public void setTrailerName(String name) { this.mName = name;}

   /** Get Trailer Site
    public String getSite() {
        return mSite;
    }
    public void setSite (String site) {mSite = site;}

    //Get Trailer Path
    public String getTrailerPath() {return mTrailerPath;}
    public void setTrailerPath (String trailerPath) {mTrailerPath = trailerPath;}**/


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        //dest.writeInt(mId);
        dest.writeString(mKey);
        dest.writeString(mName);
        //dest.writeString(mSite);
        //dest.writeString(mTrailerPath);

    }

    protected Trailer(Parcel in) {
       // mId = in.readInt();
        mKey = in.readString();
        mName =in.readString();
        //mSite = in.readString();
       //mTrailerPath = in.readString();


    }

    public static final Parcelable.Creator<Trailer> CREATOR = new Parcelable.Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };
}
