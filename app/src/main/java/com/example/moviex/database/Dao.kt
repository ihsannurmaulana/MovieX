package com.example.moviex.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao{
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM user where email = :email")
    suspend fun getUserByEmail(email: String): User

    @Query("SELECT * FROM user where isLogin = :isLogin")
    suspend fun getUserByLogin(isLogin: Boolean): User

    @Query("UPDATE user SET isLogin = :isLogin where id = :id")
    suspend fun updateUser(id: Int, isLogin: Boolean)
}