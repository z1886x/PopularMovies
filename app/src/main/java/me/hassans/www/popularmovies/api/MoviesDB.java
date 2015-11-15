package me.hassans.www.popularmovies.api;

import me.hassans.www.popularmovies.api.models.MovieResults;
import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by hassan on 11/15/2015.
 */
public interface MoviesDB {
    @GET("3/discover/movie")
    Call<MovieResults> getMovies();
}
