package com.nick.movieexplorer.ui.details;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nick.movieexplorer.domain.entity.Favorites;
import com.nick.movieexplorer.domain.repository.FavoriteRepository;
import com.nick.movieexplorer.network.RetrofitClient;
import com.nick.movieexplorer.ui.details.models.MovieDetails;
import com.nick.movieexplorer.ui.movies.model.Errors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsViewModel extends AndroidViewModel {

    MutableLiveData<MovieDetails> movieDetails = new MutableLiveData<>();
    MutableLiveData<Errors> errorData = new MutableLiveData<>();
    private FavoriteRepository favoriteRepository;

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
        favoriteRepository = new FavoriteRepository(application);
    }

    void getMovieDetails(String movieId){
        RetrofitClient.getInstance().api.getMovieDetails(movieId).enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                if (response.isSuccessful()){
                    movieDetails.setValue(response.body());
                } else {
                    Errors errors = new Errors(false, "Something wrong");
                    errorData.setValue(errors);
                }
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                Errors errors = new Errors(false, t.getLocalizedMessage());
                errorData.setValue(errors);
            }
        });
    }

    public void insertFavorite(Favorites favorites) {
        favoriteRepository.insertFavorites(favorites);
    }
    public void removeFavorites(Favorites favorites) {
        favoriteRepository.deleteFavorites(favorites);
    }


}
