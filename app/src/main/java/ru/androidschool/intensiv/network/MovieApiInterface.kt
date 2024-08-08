package ru.androidschool.intensiv.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.androidschool.intensiv.data.CreditsResponse
import ru.androidschool.intensiv.data.MovieDetails
import ru.androidschool.intensiv.data.MoviesResponse
import ru.androidschool.intensiv.data.TvShowsResponse

interface MovieApiInterface{

    @GET("movie/now_playing")
    fun getNowPlaying(@Query("api_key") apiKey: String, @Query("language") language: String): Call<MoviesResponse>

    @GET("discover/tv")
    fun getPopularTvShows(@Query("api_key") apiKey: String, @Query("language") language: String): Call<TvShowsResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(@Query("api_key") apiKey: String, @Query("language") language: String): Call<MoviesResponse>

    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String, @Query("language") language: String): Call<MoviesResponse>

    @GET("movie/{id}")
    fun getMovieDetails(@Path("id") id: Int, @Query("api_key") apiKey: String, @Query("language") language: String): Call<MovieDetails>

    @GET("movie/{id}/credits")
    fun getMovieCredits(@Path("id") id: Int, @Query("api_key") apiKey: String, @Query("language") language: String): Call<CreditsResponse>
}
