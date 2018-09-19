package com.example.android.popmoviesearchstage2.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

@Database(entities = {FavoriteEntry.class}, version = 1, exportSchema = false)

@TypeConverters(DateConverter.class)

public abstract class FavoriteAppDatabase extends RoomDatabase {

    private static final String LOG_TAG = FavoriteAppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "movie.db";
    private static FavoriteAppDatabase sInstance;

    public static FavoriteAppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        FavoriteAppDatabase.class, FavoriteAppDatabase.DATABASE_NAME)
                        // Queries should be done in a separate thread to avoid locking the UI
                        // We will allow this ONLY TEMPORALLY to see that our DB is working
                        .allowMainThreadQueries()
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract FavoriteDao favoriteDao();

}
