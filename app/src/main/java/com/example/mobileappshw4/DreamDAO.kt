package com.example.mobileappshw4

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// DAO class should always be either interface or abstract class

@Dao
interface DreamDAO {

    // what sql queries do we need?
    // 1. get all the dreams and sort them by id
    // 2. insert a dream into the database

    @Query("SELECT * FROM dream_table ORDER BY id ASC")
    fun getSortedDreams() : Flow<List<Dream>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(dream: Dream)

    // delete a dream by dream id
    @Query("DELETE FROM dream_table WHERE id=:id")
    suspend fun delete(id: Int)

    // Update a dream with a given id and a set of fields
    @Query("UPDATE dream_table SET title=:title, content=:content, reflection=:reflection, emotion=:emotion WHERE id=:id")
    suspend fun update(title: String, content: String, reflection: String, emotion: String, id: Int)

    // Get a dream with a given id
    @Query ("SELECT * FROM dream_table WHERE id=:id")
    suspend fun getDream(id: Int) : Dream


}