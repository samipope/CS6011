package com.example.lab4

import retrofit2.Call
import retrofit2.http.GET

interface ChuckNorrisApiService {
    @GET("jokes/random")
    fun getRandomJoke(): Call<Joke>
}