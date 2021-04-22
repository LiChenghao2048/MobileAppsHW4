package com.example.mobileappshw4

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Dream::class), version = 1, exportSchema = false)
public abstract class DreamRoomDataBase :RoomDatabase() { // has to be abstract
    // connected with DAO
    abstract fun DreamDAO():DreamDAO // getter

    companion object{
        // there should only be one instance of the database for the whole app

        // singleton - software design pattern
        // when you need exactly 1 object to coordinate actions across the system
        // using singleton to prevent multiple instance of database opening at the same time
        @Volatile // singleton
        private var INSTANCE:DreamRoomDataBase? = null
        // write a method to get the database

        fun getDatabase(context: Context): DreamRoomDataBase{
            // if our instance is not null
            // we can return it

            // if it is null, we want to create the database
            return INSTANCE ?: synchronized(this){
                // creating the database
                // return the database
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DreamRoomDataBase::class.java,
                    "dream_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}