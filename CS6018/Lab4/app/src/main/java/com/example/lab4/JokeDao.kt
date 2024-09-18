package com.example.lab4
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface JokeDao {
    @Insert
    suspend fun insert(joke: Joke)

    @Query("SELECT * FROM jokes")
    suspend fun getAllJokes(): List<Joke>
}