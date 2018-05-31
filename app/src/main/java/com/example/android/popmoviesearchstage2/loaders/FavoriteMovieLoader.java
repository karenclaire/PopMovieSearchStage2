package com.example.android.popmoviesearchstage2.loaders;

import com.example.android.popmoviesearchstage2.model.Movie;

import java.util.List;

interface FavoriteMovieLoader {
    List<Movie> loadInBackground();

    void addFavoriteID(int movie_id);
}
