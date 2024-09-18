package com.example.lab4
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "jokes")
data class Joke(
    @PrimaryKey val id: String,
    val value: String

)
