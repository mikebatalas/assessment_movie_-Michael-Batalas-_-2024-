package com.nick.movieexplorer.domain.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.nick.movieexplorer.domain.dao.FavoriteDao;
import com.nick.movieexplorer.domain.database.DatabaseFavorite;
import com.nick.movieexplorer.domain.entity.Favorites;

import java.util.List;

public class FavoriteRepository {
    DatabaseFavorite databaseFavorite;
    FavoriteDao favoriteDao;
    private LiveData<List<Favorites>> listFavorites;

    public FavoriteRepository(Application application) {
        databaseFavorite = DatabaseFavorite.getDatabase(application);
        favoriteDao = databaseFavorite.favoriteDao();
        listFavorites = favoriteDao.getFavorites();
    }

    public void insertFavorites(Favorites favorites) {
        DatabaseFavorite.databaseWriteExecutor.execute(() -> favoriteDao.insert(favorites));
    }

    public LiveData<List<Favorites>> getAllFavorites() {
        return listFavorites;
    }

    public void deleteFavorites(Favorites favorites){
        DatabaseFavorite.databaseWriteExecutor.execute(() -> favoriteDao.delete(favorites));
    }
}
