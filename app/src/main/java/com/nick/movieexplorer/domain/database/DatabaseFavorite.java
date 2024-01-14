package com.nick.movieexplorer.domain.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.nick.movieexplorer.domain.dao.FavoriteDao;
import com.nick.movieexplorer.domain.entity.Favorites;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Favorites.class}, version = 1, exportSchema = false)
public abstract class DatabaseFavorite extends RoomDatabase {
    public abstract FavoriteDao favoriteDao();

    private static volatile DatabaseFavorite databaseFavorite;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static DatabaseFavorite getDatabase(final Context context) {
        if (databaseFavorite == null) {
            synchronized (DatabaseFavorite.class) {
                if (databaseFavorite == null) {
                    databaseFavorite = Room.databaseBuilder(context.getApplicationContext(),
                                    DatabaseFavorite.class, "student_database")
                            .build();
                }
            }
        }
        return databaseFavorite;
    }
}
