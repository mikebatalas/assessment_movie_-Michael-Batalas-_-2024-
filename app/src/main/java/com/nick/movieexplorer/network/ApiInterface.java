package com.nick.movieexplorer.network;

import com.nick.movieexplorer.ui.details.models.MovieDetails;
import com.nick.movieexplorer.ui.movies.model.MoviesData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ApiInterface {

    @Headers("Content-Type: application/json")
    @GET("movies/")
    Call<MoviesData> getMovies();

    @Headers("Content-Type: application/json")
    @GET("movies/{id}")
    Call<MovieDetails> getMovieDetails(@Path("id") String id);

}
