package com.nick.movieexplorer.ui.movies;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nick.movieexplorer.network.RetrofitClient;
import com.nick.movieexplorer.ui.movies.model.Errors;
import com.nick.movieexplorer.ui.movies.model.MoviesData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesViewModel extends ViewModel {
    MutableLiveData<MoviesData> movieDataList = new MutableLiveData<>();
    MutableLiveData<Errors> errorData = new MutableLiveData<>();

    void getMovies(){
        RetrofitClient.getInstance().api.getMovies().enqueue(new Callback<MoviesData>() {
            @Override
            public void onResponse(Call<MoviesData> call, Response<MoviesData> response) {
                if (response.isSuccessful()){
                    movieDataList.setValue(response.body());
                } else {
                    Errors errors = new Errors(false, "Something wrong");
                    errorData.setValue(errors);
                }
            }

            @Override
            public void onFailure(Call<MoviesData> call, Throwable t) {
                Errors errors = new Errors(false, t.getLocalizedMessage());
                errorData.setValue(errors);
            }
        });
    }

}