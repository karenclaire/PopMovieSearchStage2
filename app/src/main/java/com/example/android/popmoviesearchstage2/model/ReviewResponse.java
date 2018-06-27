package com.example.android.popmoviesearchstage2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResponse {

    @SerializedName ( "id" )
    private int mId;

    @SerializedName("page")
    private int mPage;

    @SerializedName("results")
    private List <Review> mResults;

    @SerializedName("total_results")
    private Integer totalResults;

    @SerializedName("total_pages")
    private Integer totalPages;


    public int getPage (){return mPage;}


    public List<Review> getReviews(){return mResults;}

    public void setResults (List<Review> results){
        this.mResults = results;
    }


    public void setPage(Integer page) {
        this.mPage = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }



}
