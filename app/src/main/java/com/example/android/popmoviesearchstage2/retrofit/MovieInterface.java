package com.example.android.popmoviesearchstage2.retrofit;

import com.example.android.popmoviesearchstage2.model.MovieResponse;
import com.example.android.popmoviesearchstage2.model.ReviewResponse;
import com.example.android.popmoviesearchstage2.model.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieInterface {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}/videos")
    Call<TrailerResponse> getTrailers(@Path("id") int movieId, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/reviews")
    Call<ReviewResponse> getReviews(@Path("id") int movieId, @Query("api_key") String apiKey);



}

