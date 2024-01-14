package com.nick.movieexplorer.ui.favorite;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.nick.movieexplorer.domain.entity.Favorites;
import com.nick.movieexplorer.domain.repository.FavoriteRepository;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {
    private FavoriteRepository favoriteRepository;
    final LiveData<List<Favorites>> listLiveData;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        favoriteRepository = new FavoriteRepository(application);
        listLiveData = favoriteRepository.getAllFavorites();
    }
}