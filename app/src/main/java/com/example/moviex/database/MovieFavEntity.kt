package com.example.moviex.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_fav")
data class MovieFavEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val movie_id: Int,
    val title: String,
    val poster_path: String,
    val overview: String,
    val release_date: String,
    val user_id: Int
)
