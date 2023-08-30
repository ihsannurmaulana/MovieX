package com.example.moviex.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieFavDao {

    @Insert
    suspend fun insertMovie(movieFavDao: MovieFavEntity)

    @Query("SELECT * FROM movie_fav WHERE user_id = :userId")
    suspend fun getAllByUserId(userId: Int): List<MovieFavEntity>

    @Query("DELETE FROM movie_fav WHERE movie_id = :movieId")
    suspend fun deleteMovieFav(movieId: Int)
}