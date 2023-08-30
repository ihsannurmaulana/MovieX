package com.example.moviex.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class, MovieFavEntity::class], version = 4)
abstract class  AppDatabase : RoomDatabase(){
    abstract  fun UserDao(): UserDao

    abstract fun movieFavDao(): MovieFavDao

    companion object{
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return Instance ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Database"
                ).build()
                Instance = instance
                instance
            }
        }
    }
}