package com.example.mobileappshw4

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//data class Dream (val title: String, val content: String, val reflection: String, val emotion: String)

// @Entity -> class represents a SQLite table
@Entity(tableName = "dream_table")
class Dream(@ColumnInfo(name = "title") val title: String,
            @ColumnInfo(name = "content") val content: String,
            @ColumnInfo(name = "reflection") val reflection: String,
            @ColumnInfo(name = "emotion") val emotion: String) {
    @PrimaryKey (autoGenerate = true) @ColumnInfo(name = "id") var id: Int = 0
} // define column information