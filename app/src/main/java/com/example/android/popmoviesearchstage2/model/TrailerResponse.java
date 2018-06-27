package com.example.android.popmoviesearchstage2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerResponse {

    @SerializedName ( "id" )
    private int mTrailerId;

    @SerializedName ( "results" )
    List <Trailer> mTrailerList;

    //Get Trailer ID
    public int getTrailerId() { return mTrailerId;}
    public void setTrailerId(int trailerId) {mTrailerId = trailerId;}



    // Get Trailer List

    public List<Trailer> getTrailers() {return mTrailerList; }

}
