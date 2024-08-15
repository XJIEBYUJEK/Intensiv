package ru.androidschool.intensiv.network

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.androidschool.intensiv.data.CreditsResponse
import ru.androidschool.intensiv.data.MovieDetails
import ru.androidschool.intensiv.data.MoviesResponse
import ru.androidschool.intensiv.data.TvShowsResponse
import java.util.Locale

interface MovieApiInterface {

    @GET("movie/now_playing")
    fun getNowPlaying(@Query("api_key") apiKey: String, @Query("language") language: String = Locale.getDefault().toLanguageTag()): Single<MoviesResponse>

    @GET("discover/tv")
    fun getPopularTvShows(@Query("api_key") apiKey: String, @Query("language") language: String = Locale.getDefault().toLanguageTag()): Single<TvShowsResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(@Query("api_key") apiKey: String, @Query("language") language: String = Locale.getDefault().toLanguageTag()): Single<MoviesResponse>

    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String, @Query("language") language: String = Locale.getDefault().toLanguageTag()): Single<MoviesResponse>

    @GET("movie/{id}")
    fun getMovieDetails(@Path("id") id: Int, @Query("api_key") apiKey: String, @Query("language") language: String = Locale.getDefault().toLanguageTag()): Single<MovieDetails>

    @GET("movie/{id}/credits")
    fun getMovieCredits(@Path("id") id: Int, @Query("api_key") apiKey: String, @Query("language") language: String = Locale.getDefault().toLanguageTag()): Single<CreditsResponse>
}