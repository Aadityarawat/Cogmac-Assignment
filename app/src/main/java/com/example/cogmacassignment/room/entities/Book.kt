package com.example.cogmacassignment.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "books_table")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val title : String?,
    val author : String,
    val publicationDate : String,
    val genre : String,
    val readStatus : Boolean
) : Serializable