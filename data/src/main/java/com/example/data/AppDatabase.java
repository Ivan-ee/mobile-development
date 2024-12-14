package com.example.data;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.data.model.FavoritePokemonEntity;

@Database(entities = {FavoritePokemonEntity.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract FavoritePokemonDao favoritePokemonDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "pokemon-db")
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return instance;
    }
}
