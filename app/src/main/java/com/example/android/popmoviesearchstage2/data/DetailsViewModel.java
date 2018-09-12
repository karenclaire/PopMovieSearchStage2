package com.example.android.popmoviesearchstage2.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

//TODO: 1) Create DetailsViewModel that contains an Observer if MovieIsInDb Status (UX) that will
//          trigger change in the UI (must show if it is favorite or not)
//TODO: 2) must monitor movie status (Favorite or not) and access DB (delete, query, insert and update)
//  For TODO2) Ask if should I have made the AAC stuff more generic like MovieDao or MovieEntry...confused but not urgent?

public class DetailsViewModel extends ViewModel {
    //favorite member variable for the FavoriteEntry object wrapped in a LiveData
    private LiveData<FavoriteEntry> favorite;

    //  Create a constructor where you call getFavoritesById of the favoriteDao to initialize the tasks variable
    // Note: The constructor should receive the database and the id
    public DetailsViewModel(FavoriteAppDatabase mDb, int id) {
        favorite = mDb.favoriteDao().getFavoritesById (id);
    }

    // Create a getter for the favorite variable
    public LiveData<FavoriteEntry> getFavorite() {
        return favorite;
    }



}


