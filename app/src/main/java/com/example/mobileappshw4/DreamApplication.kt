package com.example.mobileappshw4

import android.app.Application

class DreamApplication : Application() {
    // creating 1 instance of database
    // 1 instance of repository

    // lazy -> the value gets computes or executes only upon first access
    val database by lazy { DreamRoomDataBase.getDatabase(this) }
    val repository by lazy { DreamRepository(database.DreamDAO()) }


}