package com.nick.movieexplorer.domain.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.nick.movieexplorer.domain.entity.Favorites;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Favorites favorites);

    @Update
    void update(Favorites favorites);

    @Query("SELECT * from favorite_table ORDER By title Asc")
    LiveData<List<Favorites>> getFavorites();

    @Delete
    void delete(Favorites favorites);
}