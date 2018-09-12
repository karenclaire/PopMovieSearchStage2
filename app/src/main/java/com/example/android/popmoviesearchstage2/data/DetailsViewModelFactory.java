package com.example.android.popmoviesearchstage2.data;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

public class DetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

//TODO: Check should ViewModelFactory be for this or MainModel?
    private final FavoriteAppDatabase mDb;
    private final int mMovieId;

    //Initialize the member variables in the constructor with the parameters received
    public DetailsViewModelFactory(FavoriteAppDatabase database, int movieId) {
        mDb = database;
        mMovieId = movieId;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new DetailsViewModel (mDb, mMovieId);
    }
}

