package com.example.moviex.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

data class MovieCreditsResponse(
    val cast: List<CastData>?
)

data class CastData(
    val id: Int,
    val name: String,
    val profile_path: String
)

data class  MovieResponse(
    val results: List<Movie>?
)

data class Genre(val id: Int, val name: String)
data class Movie(
    val id: Int,
    val title: String,
    val poster_path: String,
    val overview: String,
    val vote_average: Double,
    val vote_count: Int,
    val release_date: String,
    val genres: List<Genre>? = null,
)

interface MovieApiService {
    @GET("movie/now_playing?language=en-US&page=1")
    suspend fun getNowPlayingMovies(
        @Header("Authorization") authorization: String
    ): MovieResponse

    @GET("movie/top_rated?language=en-US&page=6")
    suspend fun getTopRatedMovies(
        @Header("Authorization") authorization: String
    ): MovieResponse

    @GET("movie/popular?language=en-US&page=1")
    suspend fun getPopularMovies(
        @Header("Authorization") authorization: String
    ): MovieResponse

    @GET("movie/upcoming?language=en-US&page=1")
    suspend fun getUpComingMovies(
        @Header("Authorization") authorization: String
    ): MovieResponse

    @GET("movie/{movie_id}?language=en-US")
    suspend fun getDetailMovies(
        @Header("Authorization") authorization: String,
        @Path("movie_id") movieId : String): Movie

    @GET("movie/{movie_id}/credits?language=en-US")
    suspend fun getMovieCredits(
        @Header("Authorization") authorization: String,
        @Path("movie_id") movieId: String
    ): MovieCreditsResponse
}

object RetrofitInstance{
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    val retrofit: Retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build()
}

val movieApiService: MovieApiService = RetrofitInstance.retrofit.create(MovieApiService::class.java)

const val authorizationHeader = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjYmQ2YzgxZTJlN2MwZGZjMzFmNDdkMDkxMDMxNzBhZSIsInN1YiI6IjY0ZTc4ZThkMWZlYWMxMDBjNDZkYzRmYiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.1VnGm80oJAL_LJ2yCKz-ufCAzBtq54bROZM0hlB1KHo"