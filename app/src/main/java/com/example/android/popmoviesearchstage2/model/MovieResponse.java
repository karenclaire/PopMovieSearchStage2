package com.example.android.popmoviesearchstage2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
    @SerializedName("page")
    private int mPage;

    @SerializedName("results")
    private List <Movie> mResults;

    private Integer totalResults;
    @SerializedName("total_results")

    private Integer totalPages;
    @SerializedName("total_pages")


    public int getPage (){return mPage;}

    public List<Movie> getResults(){return mResults;}

    public List<Movie> getMovies(){return mResults;}

    public void setResults (List<Movie> results){
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

