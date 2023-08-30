package com.example.moviex.database

import android.app.Application

class MyApplication : Application(){
    val database by lazy {
        AppDatabase.getDatabase(this)
    }
}