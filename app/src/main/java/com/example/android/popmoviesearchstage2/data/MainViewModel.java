package com.example.android.popmoviesearchstage2.data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<FavoriteEntry>> favoriteEntry;

    public MainViewModel(Application application) {
        super(application);
        FavoriteAppDatabase database = FavoriteAppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        favoriteEntry = database.favoriteDao().getAllFavorites();
    }

    public LiveData<List<FavoriteEntry>> getFavoriteEntry() {
        return favoriteEntry;
    }

}

