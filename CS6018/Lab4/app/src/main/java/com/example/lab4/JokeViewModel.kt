package com.example.lab4
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.app.Application
import androidx.lifecycle.AndroidViewModel


class JokeViewModel (application: Application) : AndroidViewModel(application){
    private val jokeDao = JokeDatabase.getDatabase(application).jokeDao()

    val currentJoke = MutableLiveData<Joke>()
    val jokeList = MutableLiveData<List<Joke>>(emptyList())

    init{
        viewModelScope.launch {
            jokeList.value = jokeDao.getAllJokes()
        }
    }

    fun fetchJoke() {
        RetrofitInstance.api.getRandomJoke().enqueue(object : Callback<Joke> {
            override fun onResponse(call: Call<Joke>, response: Response<Joke>) {
                response.body()?.let { joke ->
                    //add joke
                    currentJoke.value = joke
                    jokeList.value = jokeList.value?.plus(joke)
                }
            }

            override fun onFailure(call: Call<Joke>, t: Throwable) {
                //add error handling?
            }
        })
    }



}