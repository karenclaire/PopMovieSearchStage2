package com.example.android.popmoviesearchstage2.retrofit;

import com.example.android.popmoviesearchstage2.model.Movie;
import com.example.android.popmoviesearchstage2.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieInterface {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<Movie> getTopRatedMovies(@Query("api_key") String apiKey);



    /** For reference
     @GET("movie/{movie_id}/videos")
    Call<Trailer> getTrailers(@Path("movie_id") String movieId, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/reviews")
    Call<Review> getReviews(@Path("movie_id") String movieId, @Query("api_key") String apiKey);

    @GET("/movie/{movie_id}")
    Call<Movie> getMovies(@Path("sort") String sort, @Query("api_key") String api_key);**/




}

